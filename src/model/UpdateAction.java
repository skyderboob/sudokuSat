package model;

/**
 * Enumeration used to inform observers what to update.
 *
 * @author Eric Beijer
 */
public enum UpdateAction {
    NEW_GAME,
    NEW_SIZE,
    CHECK,
    SELECTED_NUMBER,
    GAME_SOLVED,
    HELP
}