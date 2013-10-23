package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import view.Sudoku;
import view.SudokuPanel;
import model.Game;

/**
 * This class controls all user actions from ButtonPanel.
 * 
 * @author Eric Beijer
 */
public class ButtonController implements ActionListener {
    private Game game;
    private int gameSize = 3;

    /**
     * Constructor, sets game.
     * 
     * @param game
     *            Game to be set.
     */
    public ButtonController(Game game) {
	this.game = game;
    }

    /**
     * Performs action after user pressed button.
     * 
     * @param e
     *            ActionEvent.
     */
    public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("New"))
	    newGameButtonHandler();
	else if (e.getActionCommand().equals("Solve"))
	    System.out.println("Solve clicked");
	else if (e.getActionCommand().equals("Check"))
	    System.out.println("Check game clicked");
	else if (e.getActionCommand().equals("Exit"))
	    System.exit(0);
	else if (e.getActionCommand().equals("Help on"))
	    System.out.println("Help clicked");
	else if (e.getActionCommand().equals("3x3"))
	    Sudoku.setPanelSize(3);
	else if (e.getActionCommand().equals("4x4"))
	    Sudoku.setPanelSize(4);
	else if (e.getActionCommand().equals("5x5"))
	    Sudoku.setPanelSize(5);

    }

    public void newGameButtonHandler() {
	Sudoku.setPanelSize(gameSize);
    }
}