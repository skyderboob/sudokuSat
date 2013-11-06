package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;

import view.Sudoku;
import model.SudokuPuzzle;

/**
 * This class controls all user actions from ButtonPanel.
 * 
 * @author Eric Beijer
 */
public class ButtonController implements ActionListener {
    private SudokuPuzzle puzzle;
    private int gameSize = 9;
    final JFileChooser fc = new JFileChooser();

    /**
     * Constructor, sets game.
     * 
     * @param puzzle
     *            Game to be set.
     */
    public ButtonController(SudokuPuzzle puzzle) {
	this.puzzle = puzzle;
	fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    /**
     * Performs action after user pressed button.
     * 
     * @param e
     *            ActionEvent.
     */
    public void actionPerformed(ActionEvent e) {
	try {
	    if (e.getActionCommand().equals("New"))
		puzzle.newGame(gameSize);
	    else if (e.getActionCommand().equals("Solve")) {
		puzzle.solveAndNotify();
	    } else if (e.getActionCommand().equals("Check")) {
		puzzle.check();
	    } else if (e.getActionCommand().equals("Exit")) {
		System.exit(0);
	    } else if (e.getActionCommand().equals("Import")) {
		int returnVal = fc.showOpenDialog(Sudoku.getFrames()[0]);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fc.getSelectedFile();
		    puzzle.newGame(selectedFile.getAbsolutePath());
		    fc.setCurrentDirectory(selectedFile.getParentFile());
		}
	    } else if (e.getActionCommand().equals("3x3")) {
		gameSize = 9;
	    } else if (e.getActionCommand().equals("4x4")) {
		gameSize = 16;
	    } else if (e.getActionCommand().equals("5x5")) {
		gameSize = 25;
	    }
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

    }
}