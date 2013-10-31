package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.Puzzle;
import view.Sudoku;

/**
 * This class controls all user actions from ButtonPanel.
 * 
 * @author Eric Beijer
 */
public class ButtonController implements ActionListener {
    private Puzzle puzzle;
    private int gameSize = 3;

    /**
     * Constructor, sets game.
     * 
     * @param puzzle
     *            Game to be set.
     */
    public ButtonController(Puzzle puzzle) {
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
	    System.out.println(puzzle.getNumber(1, 1));
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
	    puzzle.newGame(3);
	    Sudoku.setPanelSize(3);

	} else if (e.getActionCommand().equals("4x4")) {
	    puzzle.newGame(4);
	    Sudoku.setPanelSize(4);

	} else if (e.getActionCommand().equals("5x5")) {
	    puzzle.newGame(5);
	    Sudoku.setPanelSize(5);

	}

    }

    public void newGameButtonHandler() {
	Sudoku.setPanelSize(gameSize);
    }
}