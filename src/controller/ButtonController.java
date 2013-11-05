package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.SudokuPuzzle;

/**
 * This class controls all user actions from ButtonPanel.
 * 
 * @author Eric Beijer
 */
public class ButtonController implements ActionListener {
    private SudokuPuzzle puzzle;
    private int gameSize = 9;

    /**
     * Constructor, sets game.
     * 
     * @param puzzle
     *            Game to be set.
     */
    public ButtonController(SudokuPuzzle puzzle) {
	this.puzzle = puzzle;
    }

    /**
     * Performs action after user pressed button.
     * 
     * @param e
     *            ActionEvent.
     */
    public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("New"))
	    puzzle.newGame(gameSize);
	else if (e.getActionCommand().equals("Solve"))
	    try {
		puzzle.solve();
	    } catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	else if (e.getActionCommand().equals("Check"))
	    System.out.println("Check game clicked");
	else if (e.getActionCommand().equals("Exit"))
	    System.exit(0);
	else if (e.getActionCommand().equals("Help on"))
	    System.out.println("Help clicked");
	else if (e.getActionCommand().equals("3x3")) {
	    gameSize = 9;
	} else if (e.getActionCommand().equals("4x4")) {
	    gameSize = 16;
	} else if (e.getActionCommand().equals("5x5")) {
	    gameSize = 25;
	}

    }
}