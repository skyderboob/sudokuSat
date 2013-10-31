package view;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.UIManager;

import model.Puzzle;
import controller.ButtonController;
import controller.SudokuController;

/**
 * Main class of program.
 *
 * @author Eric Beijer
 */
public class Sudoku extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    SudokuPanel sudokuPanel;
    ButtonController buttonController;
    ButtonPanel buttonPanel;
    SudokuController sudokuController;
    JMenuBar menuBar;
    Puzzle puzzle;
    static Sudoku sudoku;
    
    public static void setPanelSize(int size) {
	sudoku.sudokuPanel = new SudokuPanel(size);
	sudoku.sudokuPanel.setGame(sudoku.puzzle);
	sudoku.getContentPane().remove(2);
	sudoku.add(sudoku.sudokuPanel, 2);
	sudoku.pack();
    }
    
    public Sudoku(int size) throws IOException {
        super("Sudoku");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        puzzle = new Puzzle(size);
        puzzle.newGame("yrd");

        buttonController = new ButtonController(puzzle);
        buttonPanel = new ButtonPanel();
        buttonPanel.setController(buttonController);
        add(buttonPanel, BorderLayout.EAST);

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

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Main entry point of program.
     * 
     * @param args Command line arguments.
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // Use System Look and Feel
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ex) { ex.printStackTrace(); }
        sudoku = new Sudoku(3);
    }
}