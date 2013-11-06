package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Observable;

/**
 * This class represents a Sudoku game. It contains the solution, the user
 * input, the selected number and methods to check the validation of the user
 * input.
 * 
 * @author Eric Beijer
 */
public class SudokuPuzzle extends Observable {
    private int puzzleSize;
    private int[][] puzzle; // Đề bài
    private int[][] solvingPuzzle; // Đề bài + người dùng input
    private int[][] solution; // Solution
    public boolean[][] check;
    private SudokuRulesEncoder ruleEncoder;
    private String miniSatResult = "";

    public SudokuPuzzle(int size) {
	this.puzzleSize = size;
	solution = null;
	puzzle = SudokuDatabase.getRandomSudoku(size);
	solvingPuzzle = new int[puzzleSize][puzzleSize];
	ruleEncoder = new SudokuRulesEncoder(puzzle);
    }

    /**
     * Tạo puzzle mới với kích cỡ nhập vào (9-16-25)
     * 
     * @param size
     *            Kích cỡ mới của puzzle
     */
    public void newGame(int size) {
	this.puzzleSize = size;
	solution = null;
	puzzle = SudokuDatabase.getRandomSudoku(size);
	solvingPuzzle = new int[puzzleSize][puzzleSize];
	ruleEncoder = new SudokuRulesEncoder(puzzle);
	setChanged();
	notifyObservers(UpdateAction.NEW_GAME);
    }

    /**
     * Tạo puzzle mới từ file
     * 
     * @param fileName
     *            file chứa puzzle mới
     * @throws IOException
     */
    public void newGame(String fileName) throws IOException {
	BufferedReader br = new BufferedReader(new FileReader(fileName));
	String line = br.readLine();
	int row = 0;
	if (line != null)
	    puzzleSize = Integer.parseInt(line);
	solution = null;
	puzzle = new int[puzzleSize][puzzleSize];
	while (((line = br.readLine()) != null) && (row < puzzleSize)) {
	    line = line.replaceAll("[^0-9]+", " ");
	    String[] numbers = line.trim().split(" ");
	    for (int c = 0; c < numbers.length; c++) {
		puzzle[row][c] = Integer.parseInt(numbers[c]);
	    }
	    row++;
	}
	ruleEncoder = new SudokuRulesEncoder(puzzle);
	br.close();
	setChanged();
	notifyObservers(UpdateAction.NEW_GAME);
    }

    /**
     * Giải puzzle, dùng với nút check
     * 
     * @return
     * @throws IOException
     */
    private String solve() throws IOException {
	if (solution != null)
	    return "Solved";
	String inputFile = "sudoku.cnf", outputFile = "sudoku.solution";
	ruleEncoder.writeToFile(inputFile);
	Process process = new ProcessBuilder("MiniSat_v1.14.exe", inputFile, outputFile).start();
	InputStream is = process.getInputStream();
	InputStreamReader isr = new InputStreamReader(is);
	BufferedReader br = new BufferedReader(isr);
	String result = "", line;

	while ((line = br.readLine()) != null) {
	    result += line + "\n";
	}

	if (readResultFromFile(outputFile) == null)
	    return "This puzzle can't be solved";
	else {
	    extractInformationFromMiniSatResult(result);
	    solution = readResultFromFile(outputFile);
	    setChanged();
	}
	return result;
    }

    /**
     * Giải và thông báo đã giải xong đến các thành phần khác
     * 
     * @throws IOException
     */
    public void solveAndNotify() throws IOException {
	solve();
	if (solution == null) {
	    miniSatResult = "Can't solve this puzzle";
	}
	setChanged();
	notifyObservers(UpdateAction.GAME_SOLVED);
    }

    /**
     * Đọc kết quả từ file trả về của MiniSat
     * 
     * @param fileName
     *            File kết quả của MiniSat
     * @return Sudoku kết quả, bằng null nếu không giải được
     * @throws IOException
     */
    public int[][] readResultFromFile(String fileName) throws IOException {
	BufferedReader br = new BufferedReader(new FileReader(fileName));
	String line;
	line = br.readLine();
	if (line.equals("UNSAT")) {
	    br.close();
	    return null;
	}
	int[][] solution = new int[puzzleSize][puzzleSize];
	line = br.readLine();
	String[] numbers = line.trim().split(" ");
	for (String number : numbers) {
	    if (number.startsWith("-") || number.equals("0"))
		continue;
	    int realNumber = Integer.parseInt(number) - 1;
	    int nCells = puzzleSize * puzzleSize;
	    int row = realNumber / nCells;
	    int col = (realNumber % nCells) / puzzleSize;
	    int value = (realNumber % nCells) % puzzleSize + 1;
	    solution[row][col] = value;
	}
	br.close();
	return solution;
    }

    /**
     * Đối chiếu các ô người dùng nhập vào và lời giải để kiểm tra
     * 
     * @throws IOException
     */
    public void check() throws IOException {
	check = new boolean[puzzleSize][puzzleSize];
	if (solution == null)
	    solve();
	for (int r = 0; r < puzzleSize; r++) {
	    for (int c = 0; c < puzzleSize; c++) {
		check[r][c] = solvingPuzzle[r][c] == 0 ? true : (solvingPuzzle[r][c] == solution[r][c]);
	    }
	}
	setChanged();
	notifyObservers(UpdateAction.CHECK);
	return;
    }

    public int getSize() {
	return puzzleSize;
    }

    public int getNumber(int c, int r) {
	return puzzle[c][r];
    }

    /**
     * Lọc từ kết quả của MiniSat để lấy bộ nhớ và CPU sử dụng
     * 
     * @param result
     */
    private void extractInformationFromMiniSatResult(String result) {
	String[] lines = result.split("\n");
	miniSatResult = "";
	for (String s : lines) {
	    if (s.contains("Memory")) {
		miniSatResult += s + "\n";
	    }
	    if (s.contains("CPU")) {
		miniSatResult += s;
	    }
	}
    }

    public String getMiniSatResult() {
	return miniSatResult;
    }

    public int[][] getSolution() {
	return solution;
    }

    public int[][] getPuzzle() {
	return puzzle;
    }

    /**
     * Điền số người dùng nhập vào bảng solvingPuzzle
     * 
     * @param r
     *            Hàng của ô
     * @param c
     *            Cột của ô
     * @param number
     *            Giá trị ô
     */
    public void setNumber(int r, int c, int number) {
	solvingPuzzle[r][c] = number;
    }

    /**
     * In puzzle ra cmd để test
     */
    public void print() {
	for (int r = 0; r < puzzleSize; r++) {
	    for (int c = 0; c < puzzleSize; c++) {
		System.out.print(puzzle[r][c] + " ");
	    }
	    System.out.println();
	}
	System.out.println();
    }
}