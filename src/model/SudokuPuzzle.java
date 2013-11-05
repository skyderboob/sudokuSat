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
    private int[][] puzzle;
    private int[][] solution;
    private SudokuRulesEncoder ruleEncoder;
    private String miniSatResult = "";
    
    public SudokuPuzzle(int size) {
	this.puzzleSize = size;
	solution = null;
	puzzle = SudokuDatabase.getRandomSudoku(size);
	ruleEncoder = new SudokuRulesEncoder(puzzle);
    }

    public void newGame(int size) {
	this.puzzleSize = size;
	solution = null;
	puzzle = SudokuDatabase.getRandomSudoku(size);
	ruleEncoder = new SudokuRulesEncoder(puzzle);
	setChanged();
	notifyObservers(UpdateAction.NEW_GAME);
    }

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

    public String solve() throws IOException {
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
	    notifyObservers(UpdateAction.GAME_SOLVED);
	}
	return result;
    }

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

    public int getSize() {
	return puzzleSize;
    }

    public int getNumber(int c, int r) {
	return puzzle[c][r];
    }
    
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

    public void setNumber(int c, int r, int number) {
	puzzle[c][r] = number;
	hasChanged();
    }

    public void print() {
	for (int r = 0; r < puzzleSize; r++) {
	    for (int c = 0; c < puzzleSize; c++) {
		System.out.print(puzzle[r][c] + " ");
	    }
	    System.out.println();
	}
	System.out.println();
    }

    public static void main(String[] args) {
	SudokuPuzzle sp = new SudokuPuzzle(9);
	sp.print();
    }
}