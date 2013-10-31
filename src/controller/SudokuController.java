package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import model.Puzzle;
import model.UpdateAction;
import view.Cell;
import view.SudokuPanel;

/**
 * This class controls all user actions from SudokuPanel.
 * 
 * @author Eric Beijer
 */
public class SudokuController implements MouseListener {
    private SudokuPanel sudokuPanel; // Panel to control.
    private Puzzle game; // Current Sudoku game.

    /**
     * Constructor, sets game.
     * 
     * @param game
     *            Game to be set.
     */
    public SudokuController(SudokuPanel sudokuPanel, Puzzle game) {
	this.sudokuPanel = sudokuPanel;
	this.game = game;
	sudokuPanel.setGame(game);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }
}