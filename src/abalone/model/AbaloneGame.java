package abalone.model;


import java.util.LinkedList;

/**
 *
 */
public class AbaloneGame implements Board, Cloneable {

    private int boardSize = 9;
    private Player openingPLayer = Player.HUMAN;
    private Player currentPlayer = Player.HUMAN;
    private Color humanColor = Color.BLACK;
    private Ball[][] balls;
    private LinkedList<Ball> blackBalls;
    private LinkedList<Ball> whiteBalls;
    private int whiteBallsLost = 0;
    private int blackBallsLost = 0;
    private int level = 2;

    public AbaloneGame() {
        balls = new Ball[boardSize][boardSize];
    }

    public AbaloneGame(int size) {

    }

    /**
     * Constructor that starts a new game only changing the opening player
     * from before.
     *
     * @param oldOpeningPlayer
     */
    public AbaloneGame(Player oldOpeningPlayer) {
        if (oldOpeningPlayer == Player.HUMAN) {
            openingPLayer = Player.HUMAN;
            humanColor = Color.BLACK;
        } else {
            openingPLayer = Player.MACHINE;
            humanColor = Color.WHITE;
        }
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setOpeningPLayer(Player openingPLayer) {
        this.openingPLayer = openingPLayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setHumanColor(Color humanColor) {
        this.humanColor = humanColor;
    }

    public void setBalls(Ball[][] balls) {
        this.balls = balls;
    }

    public void setBlackBalls(LinkedList<Ball> blackBalls) {
        this.blackBalls = blackBalls;
    }

    public void setWhiteBalls(LinkedList<Ball> whiteBalls) {
        this.whiteBalls = whiteBalls;
    }

    public void setWhiteBallsLost(int whiteBallsLost) {
        this.whiteBallsLost = whiteBallsLost;
    }

    public void setBlackBallsLost(int blackBallsLost) {
        this.blackBallsLost = blackBallsLost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getOpeningPlayer() {
        return openingPLayer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getHumanColor() {
        return humanColor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getNextPlayer() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidPosition(int row, int diag) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidTarget(int row, int diag) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board move(int rowFrom, int diagFrom, int rowTo, int diagTo) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board machineMove() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLevel(int level) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getWinner() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfBalls(Color color) {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getSlot(int row, int diag) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return boardSize;
    }

    @Override
    public AbaloneGame clone() {
        AbaloneGame clone = new AbaloneGame();
        clone.setBoardSize(getSize());
        clone.setOpeningPLayer(openingPLayer);
        clone.setCurrentPlayer(currentPlayer);
        clone.setLevel(level);
        clone.setWhiteBallsLost(whiteBallsLost);
        clone.setBlackBallsLost(blackBallsLost);
        Ball[][] ballsClone = new Ball[boardSize][boardSize];
        LinkedList<Ball> clBlackB = new LinkedList<>();
        for (Ball ball : blackBalls) {
            clBlackB.addFirst(ball.clone());
            ballsClone[ball.getRow()][ball.getDiag()] =
                    ballsClone[ball.getRow()][ball.getDiag()].clone();
        }
        LinkedList<Ball> clWhiteB = new LinkedList<>();
        for (Ball ball : whiteBalls) {
            clWhiteB.addFirst(ball.clone());
            ballsClone[ball.getRow()][ball.getDiag()] =
                    ballsClone[ball.getRow()][ball.getDiag()].clone();
        }
        clone.setBalls(ballsClone);
        clone.setBlackBalls(clBlackB);
        clone.setWhiteBalls(clWhiteB);
        return clone;
    }
}
