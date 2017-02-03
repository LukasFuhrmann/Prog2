package abalone;

import java.awt.*;

/**
 *
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

    public static Color parseColor(abalone.model.Color color) {
        if (color != abalone.model.Color.WHITE
                && color != abalone.model.Color.BLACK) {
            return new Color(102, 51, 0);
        } else {
            return (color == abalone.model.Color.WHITE) ? Color.WHITE
                                                        : Color.BLACK;
        }
    }
}
