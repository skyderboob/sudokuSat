package view;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import model.SudokuPuzzle;
import model.UpdateAction;
import controller.ButtonController;
import controller.SudokuController;

/**
 * Main class of program.
 * 
 * @author Eric Beijer
 */
public class Sudoku extends JFrame implements Observer {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    SudokuPanel sudokuPanel;
    ButtonController buttonController;
    ButtonPanel buttonPanel;
    SudokuController sudokuController;
    JTextArea minisatResult;
    SudokuPuzzle puzzle;
    static Sudoku sudoku;

    public void setPanelSize(int size) {
	remove(sudokuPanel);
	sudokuPanel = new SudokuPanel(size);
	sudokuController.setSudokuPanel(sudokuPanel);
	add(sudokuPanel);
	pack();
    }

    public Sudoku(int size) throws IOException {
	super("Sudoku");
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	getContentPane().setLayout(new BorderLayout());

	puzzle = new SudokuPuzzle(size);

	minisatResult = new JTextArea("MiniSat result");
	buttonController = new ButtonController(puzzle);
	buttonPanel = new ButtonPanel();
	buttonPanel.add(minisatResult, BorderLayout.SOUTH);
	buttonPanel.setController(buttonController);
	add(buttonPanel, BorderLayout.EAST);

	sudokuPanel = new SudokuPanel(size);
	sudokuController = new SudokuController(sudokuPanel, puzzle);
	add(sudokuPanel, BorderLayout.CENTER);

	puzzle.addObserver(sudokuController);
	puzzle.addObserver(this);

	pack();
	setLocationRelativeTo(null);
	setVisible(true);
    }

    public static void main(String[] args) throws IOException {
	// Use System Look and Feel
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    sudoku = new Sudoku(9);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

    }

    @Override
    public void update(Observable o, Object arg) {
	// TODO Auto-generated method stub
	switch ((UpdateAction) arg) {
	case NEW_GAME:
	    SudokuPuzzle sp = (SudokuPuzzle) o;
	    setPanelSize(sp.getSize());
	    break;
	case GAME_SOLVED:
	    minisatResult.setText(puzzle.getMiniSatResult());
	default:
	    break;
	}

    }
}