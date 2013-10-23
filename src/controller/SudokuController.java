package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import model.Game;
import model.UpdateAction;
import view.Cell;
import view.SudokuPanel;

/**
 * This class controls all user actions from SudokuPanel.
 *
 * @author Eric Beijer
 */
public class SudokuController implements MouseListener {
    private SudokuPanel sudokuPanel;    // Panel to control.
    private Game game;                  // Current Sudoku game.

    /**
     * Constructor, sets game.
     *
     * @param game  Game to be set.
     */
    public SudokuController(SudokuPanel sudokuPanel, Game game) {
        this.sudokuPanel = sudokuPanel;
        this.game = game;
    }

    /**
     * Recovers if user clicked field in game. If so it sets the selected number
     * at clicked position in game and updates clicked field. If user clicked a
     * field and used left mouse button, number at clicked position will be
     * cleared in game and clicked field will be updated.
     *
     * @param e MouseEvent.
     */
    public void mousePressed(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
}