package abalone.model;

import java.util.ArrayList;

/**
 *
 */
public class AbaloneGame implements Board, Cloneable {

    private int boardSize = 9;
    private Player openingPLayer = Player.HUMAN;
    private Player currentPlayer = Player.HUMAN;
    private Color humanColor = Color.BLACK;
    private Ball[][] balls;
    private ArrayList<Ball> blackBalls;
    private ArrayList<Ball> whiteBalls;
    private int whiteBallsLost = 0;
    private int blackBallsLost = 0;

    public AbaloneGame() {

    }

    public AbaloneGame(int size) {
    }

    /**
     * Gets the player who should open or already has opened the game. As an
     * invariant, this player has the black balls.
     *
     * @return The player who makes the initial move.
     */
    @Override
    public Player getOpeningPlayer() {
        return null;
    }

    /**
     * Gets the color of the human player.
     *
     * @return The tile color of the human.
     */
    @Override
    public Color getHumanColor() {
        return null;
    }

    /**
     * Gets the player who is allowed to execute the next move.
     *
     * @return The player who shall make the next move.
     * @throws IllegalStateException If the game is already over.
     */
    @Override
    public Player getNextPlayer() {
        return null;
    }

    /**
     * Checks if the provided coordinates are valid slots within the game.
     *
     * @param row  The row.
     * @param diag The diagonal.
     * @return {@code true} iff valid coordinates.
     */
    @Override
    public boolean isValidPosition(int row, int diag) {
        return false;
    }

    /**
     * Checks if the provided coordinates are valid slots within the game or 1
     * slot outside.
     *
     * @param row  The row.
     * @param diag The diagonal.
     * @return {@code true} iff valid coordinates.
     */
    @Override
    public boolean isValidTarget(int row, int diag) {
        return false;
    }

    /**
     * Executes a human move. Eliminating own balls is allowed. This method does
     * not change the state of this instance, which is treated here as
     * immutable. Instead, a new board/game is returned, which is a copy of
     * {@code this} with the move executed.
     *
     * @param rowFrom  The slot's row number from which the ball of the human
     *                 player should be moved.
     * @param diagFrom The slot's diagonal number from which the ball of the
     *                 human player should be moved.
     * @param rowTo    The slot's row number to which the ball of the human player
     *                 should be moved.
     * @param diagTo   The slot's diagonal number to which the ball of the human
     *                 player should be moved.
     * @return A new board with the move executed. If the move is not valid,
     * then {@code null} will be returned.
     * @throws IllegalStateException    If the game is already over, or it is not
     *                                  the human's turn.
     * @throws IllegalArgumentException If the provided parameters are invalid,
     *                                  e.g., the from slot lies outside the grid or the to slot outside
     *                                  the grid plus an one-element border.
     */
    @Override
    public Board move(int rowFrom, int diagFrom, int rowTo, int diagTo) {
        return null;
    }

    /**
     * Executes a machine move. This method does not change the state of this
     * instance, which is treated here as immutable. Instead, a new board/game
     * is returned, which is a copy of {@code this} with the move executed.
     *
     * @return A new board with the move executed.
     * @throws IllegalStateException If the game is already over, or it is not
     *                               the machine's turn.
     */
    @Override
    public Board machineMove() {
        return null;
    }

    /**
     * Sets the skill level of the machine.
     *
     * @param level The skill as a number, must be at least 1.
     */
    @Override
    public void setLevel(int level) {

    }

    /**
     * Checks if the game is over, i.e., one player has won.
     *
     * @return {@code true} if and only if the game is over.
     */
    @Override
    public boolean isGameOver() {
        return false;
    }

    /**
     * Checks if the game state is won. Only valid if the game is already over.
     *
     * @return The winner.
     * @throws IllegalStateException If the game is not over yet, then there is
     *                               no winner.
     */
    @Override
    public Player getWinner() {
        return null;
    }

    /**
     * Gets the number of balls currently placed on the grid for the provided
     * color. Only valid for black or white.
     *
     * @param color The color for which to count the balls.
     * @return The number of balls.
     */
    @Override
    public int getNumberOfBalls(Color color) {
        return;
    }

    /**
     * Gets the color (black or white) of a ball in the slot at the specified
     * coordinates. If the slot is empty, then the result is no color (e.g.
     * NONE).
     *
     * @param row  The row of the slot in the game grid.
     * @param diag The diagonal of the slot in the game grid.
     * @return The slot color.
     */
    @Override
    public Color getSlot(int row, int diag) {
        return null;
    }

    /**
     * Gets the number of rows = the number of diagonals of this game.
     *
     * @return The size of the game.
     */
    @Override
    public int getSize() {
        return boardSize;
    }
}
