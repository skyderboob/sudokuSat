package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.SudokuPuzzle;
import model.UpdateAction;
import controller.SudokuController;

/**
 * This class draws the sudoku panel and reacts to updates from the model.
 * 
 * @author Eric Beijer
 */
public class SudokuPanel extends JPanel implements Observer {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int puzzleSize;
    private Cell[][] cells; // Array of cells.
    private JPanel[][] table; // Panels holding the cells.

    /**
     * Constructs the panel, adds sub panels and adds fields to these sub panels.
     */
    public SudokuPanel(int size) {

	super(new GridLayout((int) Math.sqrt(size), (int) Math.sqrt(size)));
	int blockSize = (int) Math.sqrt(size);
	puzzleSize = size;

	table = new JPanel[blockSize][blockSize];
	for (int r = 0; r < blockSize; r++) {
	    for (int c = 0; c < blockSize; c++) {
		table[r][c] = new JPanel(new GridLayout(blockSize, blockSize));
		table[r][c].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		add(table[r][c]);
	    }

	}

	cells = new Cell[puzzleSize][puzzleSize];

	for (int r = 0; r < puzzleSize; r++) {
	    for (int c = 0; c < puzzleSize; c++) {
		cells[r][c] = new Cell(r, c, puzzleSize);
		table[r / blockSize][c / blockSize].add(cells[r][c]);
	    }
	}
    }
    
    /**
     * Method called when model sends update notification.
     * 
     * @param o
     *            The model.
     * @param arg
     *            The UpdateAction.
     */
    public void update(Observable o, Object arg) {
	switch ((UpdateAction) arg) {
	case NEW_GAME:
	    setOriginalPuzle((SudokuPuzzle) o);
	    break;
	case CHECK:
	    break;
	case GAME_SOLVED:
	    setSolution((SudokuPuzzle) o);
	case HELP:
	    break;
	default:
	    break;
	}
    }

    /**
     * Sets the fields corresponding to given game.
     * 
     * @param puzzle
     *            Game to be set.
     */
    public void setOriginalPuzle(SudokuPuzzle puzzle) {
	int gameSize = puzzle.getSize();
	for (int r = 0; r < gameSize; r++) {
	    for (int c = 0; c < gameSize; c++) {
		int number = puzzle.getNumber(r, c);
		cells[r][c].setBackground(Color.WHITE);
		cells[r][c].setNumber(number, number == 0? true : false);
	    }
	}
    }

    public void setSolution(SudokuPuzzle puzzle) {
	int gameSize = puzzle.getSize();
	int[][] puzzleTable = puzzle.getPuzzle();
	int[][] puzzleSolution = puzzle.getSolution();
	for (int r = 0; r < gameSize; r++) {
	    for (int c = 0; c < gameSize; c++) {
		cells[r][c].setBackground(Color.WHITE);
		if (puzzleTable[r][c] == puzzleSolution[r][c]) {
		    cells[r][c].setNumber(puzzleSolution[r][c], false);
		} else {
		    cells[r][c].setNumber(puzzleSolution[r][c], true);
		}
	    }
	}
    }

    /**
     * Adds controller to all sub panels.
     * 
     * @param sudokuController
     *            Controller which controls all user actions.
     */
    public void setController(SudokuController sudokuController) {
	for (int r = 0; r < table.length; r++) {
	    for (int c = 0; c < table[0].length; c++)
		table[r][c].addMouseListener(sudokuController);
	}
    }
}