package model;

import java.util.Observable;

/**
 * This class represents a Sudoku game. It contains the solution, the user
 * input, the selected number and methods to check the validation of the user
 * input.
 * 
 * @author Eric Beijer
 */
public class Game extends Observable {
    private int gameSize;
    private int blockSize;
    private int[][] game; // Generated game with user input.
    private int selectedNumber;     // Selected number by user.

    /**
     * Constructor
     */
    public Game(int size) {
	this.blockSize = size;
	this.gameSize = blockSize * blockSize;
	game = new int[gameSize][gameSize];
    }

    public void newGame(int size) {
	blockSize = size;
	gameSize = blockSize * blockSize;
	game = new int[gameSize][gameSize];
        setChanged();
        notifyObservers(UpdateAction.NEW_GAME);
    }
    
    public int getNumber(int c, int r) {
        return game[c][r];
    }
    
    
    /**
     * Returns number selected user.
     *
     * @return  Number selected by user.
     */
    public int getSelectedNumber() {
        return selectedNumber;
    }
    
    /**
     * Sets given number on given position in the game.
     *
     * @param x         The x position in the game.
     * @param y         The y position in the game.
     * @param number    The number to be set.
     */
    public void setNumber(int x, int y, int number) {
        game[y][x] = number;
        hasChanged();
    }
}