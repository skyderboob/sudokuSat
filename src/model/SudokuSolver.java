package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SudokuSolver {
    public static int[][] game = new int[9][9];
    private static int[][][] variables = new int[10][10][10];
    private static ArrayList<String> rules = new ArrayList<String>();
    
    private static StringBuilder currentRule = new StringBuilder();
    private static int r, c, v;

    static {
	// Ô ở hàng r, cột c, giá trị v sẽ có biến tương ứng là variables[r][c][v] = r * 100 + y * 10 + v
	for (r = 1; r < 10; r++) {
	    for (c = 1; c < 10; c++) {
		for (v = 1; v < 10; v++) {
		    variables[r][c][v] = r * 100 + c * 10 + v;
		}
	    }
	}

	// Khởi tạo bảng sudo rỗng, các ô đều có giá trị bằng 0
	for (r = 0; r < 9; r++) {
	    for (c = 0; c < 9; c++)
		game[r][c] = 0;
	}
    }

    private static void addCurrentRuleAndResetBuffer() {
	rules.add(currentRule.toString());
	currentRule.setLength(0);
    }

    public static void main(String[] args) throws IOException {
	FileWriter fw = new FileWriter("deptrai.cnf");
	encodeAllRules();
	for (String s : rules)
	    fw.write(s + "\n");
	fw.close();
    }

    public static void encodeAllRules() {
	encodeRulesForCells();
	encodeRulesForRows();
	encodeRulesForColumns();
	encodeRulesForBlocks();
	encodePreFilledCells();
    }

    /***
     * Tạo cnf clause tương ứng với luật cho mỗi ô:
     * - Chứa tối thiểu 1 số: 111 112 113 114 115 116 117 118 119 0 (ví dụ với ô 1-1)
     * - Chứa tối đa 1 số: -111 -112 0 (chỉ chứa 1 hoặc 2, lặp lại cho từng cặp giá trị từ 1-9)
     */
    public static void encodeRulesForCells() {
	for (r = 1; r < 10; r++) {
	    for (c = 1; c < 10; c++) {
		for (int v = 1; v < 10; v++) {
		    currentRule.append(variables[r][c][v] + " ");
		}
		currentRule.append("0");
		addCurrentRuleAndResetBuffer();
	    }
	}

	for (r = 1; r < 10; r++) {
	    for (c = 1; c < 10; c++) {
		for (int v = 1; v < 9; v++) {
		    for (int i = v + 1; i < 10; i++) {
			currentRule.append("-" + variables[r][c][v] + " " + "-" + variables[r][c][i] + " 0");
			addCurrentRuleAndResetBuffer();
		    }
		}
	    }
	}
    }

    /***
     * Tạo cnf clause tương ứng với luật cho mỗi hàng:
     * - Mỗi số phải xuất hiện ít nhất 1 lần trong hàng: 111 121 131 141 151 161 171 181 191 0 (ví dụ với 1)
     * - Không có số nào xuất hiện 2 lần: -111 -121 0 (ô đầu và ô thứ 2 trong hàng không thể cùng lúc bằng 1)
     */
    public static void encodeRulesForRows() {
	for (r = 1; r < 10; r++) {
	    for (c = 1; c < 10; c++) {
		for (int v = 1; v < 10; v++) {
		    currentRule.append(variables[r][v][c] + " ");
		}
		currentRule.append("0");
		addCurrentRuleAndResetBuffer();
	    }
	}

	for (r = 1; r < 10; r++) {
	    for (v = 1; v < 10; v++) {
		for (c = 1; c < 9; c++) {
		    for (int i = c + 1; i < 10; i++) {
			currentRule.append("-" + variables[r][v][v] + " " + "-" + variables[r][i][v] + " 0");
			addCurrentRuleAndResetBuffer();
		    }
		}
	    }
	}
    }

    /***
     * Tạo cnf clause tương ứng với luật cho mỗi cột:
     * - Mỗi số phải xuất hiện ít nhất 1 lần trong cột: 111 211 311 411 5111 611 7111 8111 911 0 (ví dụ với 1)
     * - Không có số nào xuất hiện 2 lần: -111 -211 0 (ô đầu và ô thứ 2 trong cột không thể cùng lúc bằng 1)
     */
    public static void encodeRulesForColumns() {
	for (c = 1; c < 10; c++) {
	    for (v = 1; v < 10; v++) {
		for (r = 1; r < 10; r++) {
		    currentRule.append(variables[r][c][v] + " ");
		}
		currentRule.append("0");
		addCurrentRuleAndResetBuffer();
	    }
	}

	for (c = 1; c < 10; c++) {
	    for (v = 1; v < 10; v++) {
		for (r = 1; r < 9; r++) {
		    for (int i = r + 1; i < 10; i++) {
			currentRule.append("-" + variables[r][c][v] + " " + "-" + variables[i][c][v] + " 0");
			addCurrentRuleAndResetBuffer();
		    }
		}
	    }
	}
    }

    /***
     * Tạo cnf clause tương ứng với luật cho mỗi block 3x3:
     * - Mỗi số phải xuất hiện ít nhất 1 lần trong block: 111 121 131 211 221 231 311 321 331 0 (ví dụ với 1)
     * - Không có số nào xuất hiện 2 lần: -111 -211 0 (ô đầu và ô thứ 2 trong cột không thể cùng lúc bằng 1)
     */
    public static void encodeRulesForBlocks() {
	for (v = 1; v < 10; v++) {
	    for (int i = 0; i < 3; i++) {
		for (int j = 0; j < 3; j++) {
		    for (r = 1; r < 4; r++) {
			for (c = 1; c < 4; c++) {
			    currentRule.append(variables[3 * i + r][3 * j + c][v] + " ");
			}
		    }
		    currentRule.append("0");
		    addCurrentRuleAndResetBuffer();
		}
	    }
	}

	for (v = 1; v < 10; v++) {
	    for (int i = 0; i < 3; i++) {
		for (int j = 0; j < 3; j++) {
		    for (r = 1; r < 4; r++) {
			for (c = 1; c < 4; c++) {
			    for (int k = c + 1; k < 4; k++) {
				currentRule.append("-" + variables[3 * i + r][3 * j + c][v] + " " + "-"
					+ variables[3 * i + r][3 * j + k][c] + " 0");
				addCurrentRuleAndResetBuffer();
			    }
			}
		    }
		}
	    }
	}

	for (v = 1; v < 10; v++) {
	    for (int i = 0; i < 3; i++) {
		for (int j = 0; j < 3; j++) {
		    for (r = 1; r < 4; r++) {
			for (c = 1; c < 4; c++) {
			    for (int k = r + 1; k < 4; k++) {
				for (int l = 1; l < 4; l++) {
				    currentRule.append("-" + variables[3 * i + r][3 * j + c][v] + " " + "-"
					    + variables[3 * i + k][3 * j + l][v] + " 0");
				    addCurrentRuleAndResetBuffer();
				}
			    }
			}
		    }
		}
	    }
	}
    }

    public static void encodePreFilledCells() {
	for (r = 0; r < 9; r++) {
	    for (c = 0; c < 9; c++) {
		if (game[r][c] != 0) {
		    currentRule.append(variables[r + 1][c + 1][game[r][c]] + " 0");
		    addCurrentRuleAndResetBuffer();
		}
	    }
	}
    }
}
