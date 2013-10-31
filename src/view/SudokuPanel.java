package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Puzzle;
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

    private int panelSize;
    private Cell[][] cells; // Array of cells.
    private JPanel[][] table; // Panels holding the cells.

    /**
     * Constructs the panel, adds sub panels and adds fields to these sub panels.
     */
    public SudokuPanel(int size) {
	super(new GridLayout(size, size));
	panelSize = size;

	table = new JPanel[size][size];
	for (int r = 0; r < size; r++) {
	    for (int c = 0; c < size; c++) {
		table[r][c] = new JPanel(new GridLayout(size, size));
		table[r][c].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		add(table[r][c]);
	    }

	}

	int gameSize = size * size;
	cells = new Cell[gameSize][gameSize];

	for (int r = 0; r < gameSize; r++) {
	    for (int c = 0; c < gameSize; c++) {
		cells[r][c] = new Cell(r, c, size);
		table[r / size][c / size].add(cells[r][c]);
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
	    setGame((Puzzle) o);
	    break;
	case CHECK:
	    break;
	case GAME_SOLVED:
	    setGame((Puzzle) o);
	case HELP:
	    break;
	default:
	    break;
	}
    }

    /**
     * Sets the fields corresponding to given game.
     * 
     * @param game
     *            Game to be set.
     */
    public void setGame(Puzzle game) {
	int gameSize = panelSize * panelSize;
	for (int r = 0; r < gameSize; r++) {
	    for (int c = 0; c < gameSize; c++) {
		int number = game.getNumber(r, c);
		cells[r][c].setBackground(Color.WHITE);
		cells[r][c].setNumber(number, number == 0 ? true : false);
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
	for (int r = 0; r < panelSize; r++) {
	    for (int c = 0; c < panelSize; c++)
		table[r][c].addMouseListener(sudokuController);
	}
    }
}