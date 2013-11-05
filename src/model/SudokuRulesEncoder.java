package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SudokuRulesEncoder {
    private int size;
    private int blockSize;
    private int[][] puzzle;
    private int[][][] variables;
    private int nVar;
    private ArrayList<String> allRules = new ArrayList<String>();
    private ArrayList<String> sudokuRules = new ArrayList<String>();
    private ArrayList<String> filledCellsRules = new ArrayList<String>();

    private int r, c, v;
    private StringBuilder currentRule = new StringBuilder();

    public SudokuRulesEncoder(int[][] puzzle) {
	this.puzzle = puzzle;
	this.size = puzzle.length;
	this.blockSize = (int) Math.sqrt(size);

	variables = new int[size + 1][size + 1][size + 1];
	nVar = 1;
	for (r = 1; r < size + 1; r++) {
	    for (c = 1; c < size + 1; c++) {
		for (v = 1; v < size + 1; v++) {
		    variables[r][c][v] = nVar++;
		}
	    }
	}
	nVar--;
    }

    private void addCurrentRuleAndResetBuffer(StringBuilder currentRule, ArrayList<String> rules) {
	rules.add(currentRule.toString());
	currentRule.setLength(0);
    }

    public void encodeExtendedRules() {
	encodeCellDefinednessRules();
	encodeCellUniquenessRules();
	encodeRowDefinednessRules();
	encodeRowUniquenessRules();
	encodeColumnDefinednessRules();
	encodeColumnUniquenessRules();
	encodeBlockDefinednessRules();
	encodeBlockUniquenessRules();
	encodePreFilledCells();

	allRules.addAll(sudokuRules);
	allRules.addAll(filledCellsRules);
    }

    public void writeToFile(String fileName) throws IOException {
	encodeExtendedRules();
	FileWriter fw = new FileWriter(fileName);
	fw.write("p cnf " + nVar + " " + allRules.size() + "\n");
	for (String rule : allRules) {
	    fw.write(rule.trim() + " 0\n");
	}
	fw.close();
    }

    /***
     * Tạo cnf clause thể hiện mỗi ô chứa ít nhất 1 giá trị từ 1 đến size của bảng
     * 111 112 113 114 115 116 117 118 119 0 (ô 1-1 có ít nhất 1 giá trị từ 0-9)
     */
    public void encodeCellDefinednessRules() {
	for (r = 1; r < size + 1; r++) {
	    for (c = 1; c < size + 1; c++) {
		for (v = 1; v < size + 1; v++) {
		    currentRule.append(variables[r][c][v] + " ");
		}
		addCurrentRuleAndResetBuffer(currentRule, sudokuRules);
	    }
	}
    }

    /***
     * Tạo cnf clause thể hiện mỗi ô có nhiều nhất 1 giá trị từ 1 đến size của bảng
     * -111 -112 0 (ô 1-1 không thể đồng thời bằng 1 và 2)
     */
    public void encodeCellUniquenessRules() {
	for (r = 1; r < size + 1; r++) {
	    for (c = 1; c < size + 1; c++) {
		for (int v = 1; v < size; v++) {
		    for (int i = v + 1; i < size + 1; i++) {
			currentRule.append("-" + variables[r][c][v] + " " + "-" + variables[r][c][i]);
			addCurrentRuleAndResetBuffer(currentRule, sudokuRules);
		    }
		}
	    }
	}
    }

    /***
     * Tạo cnf clause thể hiện trong mỗi hàng mỗi số xuất hiện ít nhất 1 lần:
     * 111 121 131 141 151 161 171 181 191 0 (ở hàng 1 giá trị 1 xuất hiện ít nhất 1 lần)
     */
    public void encodeRowDefinednessRules() {
	for (r = 1; r < size + 1; r++) {
	    for (v = 1; v < size + 1; v++) {
		for (int c = 1; c < size + 1; c++) {
		    currentRule.append(variables[r][c][v] + " ");
		}
		addCurrentRuleAndResetBuffer(currentRule, sudokuRules);
	    }
	}
    }

    /***
     * Tạo cnf clause thể hiện trong mỗi hàng mỗi số xuất hiện nhiều nhất 1 lần;
     * -111 -121 0 (ô 1 và ô 2 trong hàng 1 không thể cùng lúc bằng 1)
     */
    public void encodeRowUniquenessRules() {
	for (r = 1; r < size + 1; r++) {
	    for (v = 1; v < size + 1; v++) {
		for (c = 1; c < size; c++) {
		    for (int i = c + 1; i < size + 1; i++) {
			currentRule.append("-" + variables[r][c][v] + " " + "-" + variables[r][i][v]);
			addCurrentRuleAndResetBuffer(currentRule, sudokuRules);
		    }
		}
	    }
	}
    }

    /***
     * Tạo cnf clause thể hiện mỗi cột mỗi số xuất hiện ít nhất 1 lần
     * 111 211 311 411 5111 611 7111 8111 911 0 (ở cột 1 thì số 1 xuất hiện ít nhất 1 lần)
     */
    public void encodeColumnDefinednessRules() {
	for (c = 1; c < size + 1; c++) {
	    for (v = 1; v < size + 1; v++) {
		for (r = 1; r < size + 1; r++) {
		    currentRule.append(variables[r][c][v] + " ");
		}
		addCurrentRuleAndResetBuffer(currentRule, sudokuRules);
	    }
	}
    }

    /**
     * Tạo cnf clause thể hiện mỗi cột mỗi số xuất hiện nhiều nhất 1 lần
     * -111 -211 0 (ô 1 và ô 2 trong cột không thể cùng lúc bằng 1)
     */
    public void encodeColumnUniquenessRules() {
	for (c = 1; c < size + 1; c++) {
	    for (v = 1; v < size + 1; v++) {
		for (r = 1; r < size; r++) {
		    for (int i = r + 1; i < size + 1; i++) {
			currentRule.append("-" + variables[r][c][v] + " " + "-" + variables[i][c][v]);
			addCurrentRuleAndResetBuffer(currentRule, sudokuRules);
		    }
		}
	    }
	}
    }

    /***
     * Tạo cnf clause thể hiện trong mỗi block mỗi số xuất hiện ít nhất 1 lần
     * 111 121 131 211 221 231 311 321 331 0 (Trong block 1 thì số 1 xuất hiện ít nhất 1 lần)
     */
    public void encodeBlockDefinednessRules() {
	for (v = 1; v < size + 1; v++) {
	    for (int i = 0; i < blockSize; i++) {
		for (int j = 0; j < blockSize; j++) {
		    for (r = 1; r < blockSize + 1; r++) {
			for (c = 1; c < blockSize + 1; c++) {
			    currentRule.append(variables[blockSize * i + r][blockSize * j + c][v] + " ");
			}
		    }
		    addCurrentRuleAndResetBuffer(currentRule, sudokuRules);
		}
	    }
	}
    }

    /***
     * Tạo cnf clause thể hiện trong mỗi block mỗi số xuất hiện không quá 1 lần
     * -111 -211 0 (ô 1 và ô 1 trong cột không thể cùng lúc bằng 1)
     */
    public void encodeBlockUniquenessRules() {
	for (v = 1; v < size + 1; v++) {
	    for (int i = 0; i < blockSize; i++) {
		for (int j = 0; j < blockSize; j++) {
		    for (r = 1; r < blockSize + 1; r++) {
			for (c = 1; c < blockSize + 1; c++) {
			    for (int k = c + 1; k < blockSize + 1; k++) {
				currentRule.append("-" + variables[blockSize * i + r][blockSize * j + c][v] + " " + "-"
					+ variables[blockSize * i + r][blockSize * j + k][v]);
				addCurrentRuleAndResetBuffer(currentRule, sudokuRules);
			    }
			}
		    }
		}
	    }
	}

	for (v = 1; v < size + 1; v++) {
	    for (int i = 0; i < blockSize; i++) {
		for (int j = 0; j < blockSize; j++) {
		    for (r = 1; r < blockSize + 1; r++) {
			for (c = 1; c < blockSize + 1; c++) {
			    for (int k = r + 1; k < blockSize + 1; k++) {
				for (int l = 1; l < blockSize + 1; l++) {
				    currentRule.append("-" + variables[blockSize * i + r][blockSize * j + c][v] + " "
					    + "-" + variables[blockSize * i + k][blockSize * j + l][v]);
				    addCurrentRuleAndResetBuffer(currentRule, sudokuRules);
				}
			    }
			}
		    }
		}
	    }
	}
    }

    /***
     * Tạo cnf clause cho các ô đã được điền sẵn
     * 111 (ô 1 đã được điền 1)
     */
    public void encodePreFilledCells() {
	for (r = 0; r < size; r++) {
	    for (c = 0; c < size; c++) {
		if (puzzle[r][c] != 0) {
		    currentRule.append(variables[r + 1][c + 1][puzzle[r][c]]);
		    addCurrentRuleAndResetBuffer(currentRule, sudokuRules);
		}
	    }
	}
    }

}
