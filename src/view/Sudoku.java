package view;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
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
    JMenuBar menuBar;
    JTextArea minisatResult;
    SudokuPuzzle puzzle;
    static Sudoku sudoku;

    public static void setPanelSize(int size) {
	sudoku.sudokuPanel = new SudokuPanel(size);
	sudoku.sudokuPanel.setOriginalPuzle(sudoku.puzzle);

	sudoku.pack();
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


	//add(minisatResult, BorderLayout.SOUTH);
	
	menuBar = new JMenuBar();
	JMenu menu = new JMenu("A Menu");
	menuBar.add(menu);
	add(menuBar, BorderLayout.NORTH);

	sudokuPanel = new SudokuPanel(size);
	sudokuController = new SudokuController(sudokuPanel, puzzle);
	sudokuPanel.setController(sudokuController);
	add(sudokuPanel, BorderLayout.CENTER);

	puzzle.addObserver(buttonPanel);
	puzzle.addObserver(sudokuPanel);
	puzzle.addObserver(this);
	
	pack();
	setLocationRelativeTo(null);
	setVisible(true);
    }

    /**
     * Main entry point of program.
     * 
     * @param args
     *            Command line arguments.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
	// Use System Look and Feel
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	sudoku = new Sudoku(9);
    }

    @Override
    public void update(Observable o, Object arg) {
	// TODO Auto-generated method stub
	switch ((UpdateAction) arg) {
	case NEW_GAME:
//	    getContentPane().remove(2);
//	    add(sudokuPanel, 2);
//	    pack();
	    break;
	case GAME_SOLVED:
	    minisatResult.setText(puzzle.getMiniSatResult());
	default:
	    break;
	}

    }
}