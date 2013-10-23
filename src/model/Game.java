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

    /**
     * Constructor
     */
    public Game(int size) {
	this.blockSize = size;
	this.gameSize = blockSize * blockSize;
	game = new int[gameSize][gameSize];
    }

    public void newGame(int size) {
        setChanged();
        notifyObservers(UpdateAction.NEW_GAME);
    }
    
    public int getNumber(int c, int r) {
        return game[c][r];
    }
    
    
    public static void main(String[] args) {
	Game test = new Game(2);
    }

}