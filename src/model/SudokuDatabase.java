package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SudokuDatabase {
    private static ArrayList<String> x9Sudokus;
    private static ArrayList<String> x16Sudokus;
    private static ArrayList<String> x25Sudokus;

    static {
	try {
	    x9Sudokus = loadSudokusFromFile("SudokuDatabase\\sudokus.9");
	    x16Sudokus = loadSudokusFromFile("SudokuDatabase\\sudokus.16");
	    x25Sudokus = loadSudokusFromFile("SudokuDatabase\\sudokus.25");
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    private static ArrayList<String> loadSudokusFromFile(String fileName) throws IOException {
	ArrayList<String> sudokus = new ArrayList<String>();
	BufferedReader br = new BufferedReader(new FileReader(fileName));
	String line = "";
	while ((line = br.readLine()) != null) {
	    sudokus.add(line);
	}
	br.close();
	return sudokus;
    }

    public static int[][] convertStringToSudokuArray(String sudokuString) {
	String[] numbers = sudokuString.split(" ");
	int size = (int) Math.sqrt(numbers.length);
	int[][] sudokuTable = new int[size][size];
	int index = 0;
	for (int r = 0; r < size; r++) {
	    for (int c = 0; c < size; c++) {
		sudokuTable[r][c] = Integer.parseInt(numbers[index++]);
	    }
	}
	return sudokuTable;
    }

    public static int[][] getRandomSudoku(int size) {
	int[][] puzzle = new int[size][size];
	int randomIndex = -1;
	switch (size) {
	case 9:
	    randomIndex = new Random().nextInt(x9Sudokus.size());
	    puzzle = convertStringToSudokuArray(x9Sudokus.get(randomIndex));
	    break;
	case 16:
	    randomIndex = new Random().nextInt(x16Sudokus.size());
	    puzzle = convertStringToSudokuArray(x16Sudokus.get(randomIndex));
	    break;
	case 25:
	    randomIndex = new Random().nextInt(x25Sudokus.size());
	    puzzle = convertStringToSudokuArray(x25Sudokus.get(randomIndex));
	    break;    
	default:
	    break;
	}
	return puzzle;
    }
    
    public static void main(String[] args) {
	int[][] puzzle = getRandomSudoku(9);
	for (int r = 0; r < 9; r++) {
	    for (int c = 0; c < 9; c++) {
		System.out.print(puzzle[r][c] + " ");
	    }
	    System.out.println();
	}
    }
}
