package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;

import sun.awt.RepaintArea;
import controller.ButtonController;
import controller.SudokuController;
import model.Game;

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
    Game game;
    static Sudoku sudoku;
    
    public static void setPanelSize(int size) {
	sudoku.sudokuPanel = new SudokuPanel(size);
	sudoku.getContentPane().remove(1);
	sudoku.getContentPane().add(sudoku.sudokuPanel, 1);
	sudoku.pack();
    }
    
    public Sudoku(int size) {
        super("Sudoku");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        game = new Game(size);

        buttonController = new ButtonController(game);
        buttonPanel = new ButtonPanel();
        buttonPanel.setController(buttonController);
        add(buttonPanel, BorderLayout.EAST);

        sudokuPanel = new SudokuPanel(size);
        sudokuController = new SudokuController(sudokuPanel, game);
        sudokuPanel.setController(sudokuController);
        add(sudokuPanel, BorderLayout.CENTER);

        game.addObserver(buttonPanel);
        game.addObserver(sudokuPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Main entry point of program.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // Use System Look and Feel
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ex) { ex.printStackTrace(); }
        sudoku = new Sudoku(3);
    }
}