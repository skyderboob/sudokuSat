package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.UpdateAction;
import controller.SudokuController;

/**
 * This class draws the sudoku panel and reacts to updates from the model.
 *
 * @author Eric Beijer
 */
public class SudokuPanel extends JPanel implements Observer {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int panelSize;
    private Cell[][] cells;       // Array of cells.
    private JPanel[][] table;      // Panels holding the cells.
    
    /**
     * Constructs the panel, adds sub panels and adds fields to these sub panels.
     */
    public SudokuPanel(int size) {
        super(new GridLayout(size, size));
        panelSize = size;

        table = new JPanel[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                table[y][x] = new JPanel(new GridLayout(size, size));
                table[y][x].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                add(table[y][x]);
            }
        }

        int gameSize = size * size;
        cells = new Cell[gameSize][gameSize];
        for (int y = 0; y < gameSize; y++) {
            for (int x = 0; x < gameSize; x++) {
                cells[y][x] = new Cell(x, y, size);
                table[y / size][x / size].add(cells[y][x]);
            }
        }
    }

    /**
     * Method called when model sends update notification.
     *
     * @param o     The model.
     * @param arg   The UpdateAction.
     */
    public void update(Observable o, Object arg) {
        switch ((UpdateAction)arg) {
            case NEW_GAME:
                //setGame((Game)o);
                break;
            case CHECK:
                break;
            case SELECTED_NUMBER:
            case CANDIDATES:
            case HELP:
                break;
        }
    }

    /**
     * Sets the fields corresponding to given game.
     *
     * @param game  Game to be set.
     */
//    public void setGame(Game game) {
//        for (int y = 0; y < 9; y++) {
//            for (int x = 0; x < 9; x++) {
//                cells[y][x].setBackground(Color.WHITE);
//                cells[y][x].setNumber(game.getNumber(x, y), false);
//            }
//        }
//    }


    /**
     * Adds controller to all sub panels.
     *
     * @param sudokuController  Controller which controls all user actions.
     */
    public void setController(SudokuController sudokuController) {
        for (int y = 0; y < panelSize; y++) {
            for (int x = 0; x < panelSize; x++)
                table[y][x].addMouseListener(sudokuController);
        }
    }
}