package abalone.model;


import java.util.LinkedList;

/**
 *
 */
public class AbaloneGame implements Board, Cloneable {

    private int boardSize = 9;
    private Player openingPLayer = Player.HUMAN;
    private Player currentPlayer = Player.HUMAN;
    private Ball[][] balls;
    private LinkedList<Ball> blackBalls;
    private LinkedList<Ball> whiteBalls;
    private int whiteBallsLost = 0;
    private int blackBallsLost = 0;
    private int level;

    public AbaloneGame(int level) {
        this.level = level;
        balls = new Ball[boardSize][boardSize];
        fillBalls();
    }

    public AbaloneGame(int size, Player oldOpeningPLayer, int level) {
        if (size < MIN_SIZE || size % 2 == 0) {
            throw new IllegalArgumentException("Board size has to be odd "
                    + "and at least " + MIN_SIZE + ".");
        } else {
            openingPLayer = oldOpeningPLayer;
            currentPlayer = openingPLayer;
            boardSize = size;
            this.level = level;
            fillBalls();
        }
    }

    /**
     * Constructor that starts a new game, changing only the opening player
     * from before.
     *
     * @param oldOpeningPlayer opening player of game before(standard is HUMAN).
     */
    public AbaloneGame(Player oldOpeningPlayer, int oldSize, int level) {
        Player newOpener = (oldOpeningPlayer == Player.HUMAN) ? Player.MACHINE
                                                              : Player.HUMAN;
        boardSize = oldSize;
        this.level = level;
        fillBalls();
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
        return (getOpeningPlayer() == Player.HUMAN) ? Color.BLACK : Color.WHITE;
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
        if (0 <= row && row < boardSize
                && Math.max(0, row - Math.floorDiv(boardSize, 2)) <= diag
                && diag <= Math.min(
                            row + Math.floorDiv(boardSize, 2), boardSize - 1)) {
            return true;
        } else {
            return false;
        }
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
        if (level < 1) {
            throw new IllegalArgumentException("Level needs to be above 0.");
        } else {
            this.level = level;
        }
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
        if (color == Color.NONE) {
            throw new IllegalArgumentException("There are no balls with color"
                    + " NONE.");
        } else {
            return (color == Color.BLACK) ? blackBalls.size()
                                          : whiteBalls.size();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getSlot(int row, int diag) {
        return (balls[row][diag] == null) ? Color.NONE
                                          : balls[row][diag].getColor();
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
        AbaloneGame clone = new AbaloneGame(level);
        clone.boardSize = boardSize;
        clone.openingPLayer = openingPLayer;
        clone.currentPlayer = currentPlayer;
        clone.whiteBallsLost = whiteBallsLost;
        clone.blackBallsLost = blackBallsLost;
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
        clone.balls = ballsClone;
        clone.blackBalls = clBlackB;
        clone.whiteBalls = clWhiteB;
        return clone;
    }

    private void fillBalls() {
        Color machineColor = (getHumanColor() == Color.BLACK) ? Color.WHITE
                                                              : Color.BLACK;
        int flooredHalf = Math.floorDiv(boardSize, 2);
        for (int row = 0; row < 2; row++) {
            for (int diag = 0; diag < flooredHalf; diag++) {
                blackBalls.addFirst(new Ball(
                        Player.HUMAN, getHumanColor(), row, diag));
                whiteBalls.addFirst(
                        new Ball(Player.MACHINE, machineColor,
                                boardSize - (row + 1),
                                boardSize - diag));
            }
        }
        for (int diag = 2; diag < flooredHalf - 2; diag++) {
            blackBalls.addFirst(new Ball(
                    Player.HUMAN, getHumanColor(), 2, diag));
            whiteBalls.addFirst(
                    new Ball(Player.MACHINE, machineColor,
                            boardSize - 3, boardSize - diag));
        }
        fillBallsArray();
    }

    private void fillBallsArray() {
        for (Ball ball : blackBalls) {
            balls[ball.getRow()][ball.getDiag()] = ball;
        }
        for (Ball ball : whiteBalls) {
            balls[ball.getRow()][ball.getDiag()] = ball;
        }
    }
}
