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
public class Puzzle extends Observable {
    private int puzzleSize;
    private int blockSize;
    private int[][] puzzle; // Generated game with user input.
    private SudokuRulesEncoder ruleEncoder;
    private int selectedNumber; // Selected number by user.

    /**
     * Constructor
     */
    public Puzzle(int size) {
	this.blockSize = size;
	this.puzzleSize = blockSize * blockSize;
	puzzle = new int[puzzleSize][puzzleSize];
    }

    public void newGame(int size) {
	blockSize = size;
	puzzleSize = blockSize * blockSize;
	puzzle = new int[puzzleSize][puzzleSize];
	setChanged();
	notifyObservers(UpdateAction.NEW_GAME);
    }

    public void newGame(String fileName) throws IOException {
	BufferedReader br = new BufferedReader(new FileReader(fileName));
	String line = br.readLine();
	int row = 0;
	if (line != null)
	    puzzleSize = Integer.parseInt(line);
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
	readResultFromFile(outputFile);
	return result;
    }

    public boolean readResultFromFile(String fileName) throws IOException {
	BufferedReader br = new BufferedReader(new FileReader(fileName));
	String line;
	line = br.readLine();
	if (line.equals("UNSAT")) {
	    br.close();
	    return false;
	}
	puzzle = new int[puzzleSize][puzzleSize];
	line = br.readLine();
	String[] numbers = line.trim().split(" ");
	for (String number : numbers)
	{
	    if (number.startsWith("-") || number.equals("0")) continue;
	    int realNumber = Integer.parseInt(number) - 1;
	    int nCells = puzzleSize * puzzleSize;
	    int row = realNumber / nCells;
	    int col = (realNumber % nCells)/puzzleSize;
	    int value = (realNumber % nCells) % puzzleSize + 1;
	    puzzle[row][col] = value;	    
	}
	br.close();
	setChanged();
	notifyObservers(UpdateAction.GAME_SOLVED);
	return true;
    }

    public int getNumber(int c, int r) {
	return puzzle[c][r];
    }

    /**
     * Returns number selected user.
     * 
     * @return Number selected by user.
     */
    public int getSelectedNumber() {
	return selectedNumber;
    }

    /**
     * Sets given number on given position in the game.
     * 
     * @param x
     *            The x position in the game.
     * @param y
     *            The y position in the game.
     * @param number
     *            The number to be set.
     */
    public void setNumber(int c, int r, int number) {
	puzzle[c][r] = number;
	hasChanged();
    }
}