package abalone;

import java.awt.*;

/**
 * Class implementing several useful methods.
 */
public class Utility {

    private Utility() {
    }

    /**
     * Calculates and returns the first viable diagonal coordinate in a row
     * on a game board.
     *
     * @param boardSize size of the board.
     * @param row       current row of the board.
     * @return the first diagonal coordinate.
     */
    public static int firstDiag(int boardSize, int row) {
        return Math.max(0, row - Math.floorDiv(boardSize, 2));
    }

    /**
     * Calculates and returns the number of spaces before and after the board
     * in a row.
     *
     * @param boardSize size of the board.
     * @param row       current row of the board.
     * @return number of spaces.
     */
    public static int calcSpace(int boardSize, int row) {
        return Math.abs(row - Math.floorDiv(boardSize, 2));
    }

    /**
     * Calculates and returns the last viable diagonal coordinate in a row
     * on a game board.
     *
     * @param boardSize size of the board.
     * @param row       current row of the board.
     * @return the last diagonal coordinate.
     */
    public static int lastDiag(int boardSize, int row) {
        return Math.min(row + Math.floorDiv(boardSize, 2), boardSize - 1);
    }

    /**
     * Converts colors of the type {@code abalone.model.Color} into {@code
     * java.awt.Color}.
     *
     * @param color color of the {@code abalone.model.Color} type.
     * @return respective {@code java.awt.Color} for the input.
     */
    public static Color parseColor(abalone.model.Color color) {
        if (color != abalone.model.Color.WHITE
                && color != abalone.model.Color.BLACK) {
            return new Color(102, 51, 0);
        } else {
            return (color == abalone.model.Color.WHITE) ? Color.WHITE
                                                        : Color.BLACK;
        }
    }

    /**
     * Checks if the coordinates of a move are in reach for a valid move.
     *
     * @param rowFrom  current row of the ball.
     * @param diagFrom current diagonal coordinate of the ball.
     * @param rowTo    target row.
     * @param diagTo   target diagonal.
     * @return {@code true} if coordinates are next to another, {@code false}
     * otherwise.
     */
    public static boolean isNextTo(int rowFrom, int diagFrom,
                                   int rowTo, int diagTo) {
        int row = rowTo - rowFrom;
        int diag = diagTo - diagFrom;
        if (Math.abs(row) > 1 || Math.abs(diag) > 1) {
            return false;
        } else {
            return !((row == 0 && diag == 0) || (diag * row == -1));
        }
    }
}
