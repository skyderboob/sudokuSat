package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Observable;
import java.util.Observer;

import model.SudokuPuzzle;
import model.UpdateAction;
import view.Cell;
import view.SudokuPanel;

/**
 * This class controls all user actions from SudokuPanel.
 * 
 * @author Eric Beijer
 */
public class SudokuController implements Observer, FocusListener {
    private SudokuPanel sudokuPanel; // Panel to control.
    private SudokuPuzzle puzzle; // Current Sudoku game.

    /**
     * Constructor, sets game.
     * 
     * @param game
     *            Game to be set.
     */
    public SudokuController(SudokuPanel sudokuPanel, SudokuPuzzle game) {
	this.sudokuPanel = sudokuPanel;
	this.puzzle = game;
	this.sudokuPanel.setOriginalPuzle(game);
	this.sudokuPanel.setController(this);
    }

    public void setSudokuPanel(SudokuPanel sudokuPanel) {
	this.sudokuPanel = sudokuPanel;
	this.sudokuPanel.setController(this);
    }
    
    @Override
    public void update(Observable o, Object arg) {
	// TODO Auto-generated method stub
	switch ((UpdateAction) arg) {
	case NEW_GAME:
	    sudokuPanel.setOriginalPuzle(puzzle);
	    break;
	case CHECK:
	    sudokuPanel.check(puzzle);
	    break;
	case GAME_SOLVED:
	    sudokuPanel.setSolution(puzzle);
	    break;
	case HELP:
	    break;
	default:
	    break;
	}
    }

    @Override
    public void focusGained(FocusEvent e) {
	// TODO Auto-generated method stub
	Cell cell = (Cell) e.getSource();
	Color currentColor = cell.getForeground();
	cell.setForeground(currentColor.equals(Color.BLACK) ? Color.BLACK : Color.BLUE);
    }

    @Override
    public void focusLost(FocusEvent e) {
	// TODO Auto-generated method stub
	Cell cell = (Cell) e.getSource();
	String cellText = cell.getText();
	int value = cellText.equals("") ? 0 : Integer.parseInt(cellText);
	puzzle.setNumber(cell.getRow(), cell.getColumn(), value);
    }
}