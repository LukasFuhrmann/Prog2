package abalone.model;


import java.util.LinkedList;

/**
 *
 */
public class AbaloneGame implements Board, Cloneable {

    private int boardSize = 9;
    private int halfBSize = Math.floorDiv(boardSize, 2);
    private Player openingPLayer = Player.HUMAN;
    private Player currentPlayer = Player.HUMAN;
    private Ball[][] balls;
    private LinkedList<Ball> blackBalls = new LinkedList<>();
    private LinkedList<Ball> whiteBalls = new LinkedList<>();
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
            halfBSize = Math.floorDiv(boardSize, 2);
            this.level = level;
            balls = new Ball[boardSize][boardSize];
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
        halfBSize = Math.floorDiv(boardSize, 2);
        this.level = level;
        balls = new Ball[boardSize][boardSize];
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
        return isValidPosOnSize(row, diag, boardSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidTarget(int row, int diag) {
        return isValidPosOnSize(row, diag, boardSize + 1);
    }

    private boolean isValidPosOnSize(int row, int diag, int boardSize) {
        if (0 <= row && row < boardSize
                && Math.max(0, row - halfBSize) <= diag
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
    public Board move(int rowFrom, int diagFrom, int rowTo, int diagTo) {
        if (isNextTo(rowFrom, diagFrom, rowTo, diagTo)) {
            int row = rowTo - rowFrom;
            int diag = diagTo - diagFrom;

        } else {
            return null;
        }
    }

    private boolean isNextTo(int rowFrom, int diagFrom, int rowTo, int diagTo) {
        int row = rowTo - rowFrom;
        int diag = diagTo - diagFrom;
        if (row > 1 || diag > 1) {
            return false;
        } else {
            return !((row == 0 && diag == 0) || (diag * row == -1));
        }
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
        return !(blackBallsLost < ELIM && whiteBallsLost < ELIM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getWinner() {
        if (!isGameOver()) {
            throw new IllegalStateException("Game not over yet.");
        } else if (blackBallsLost >= ELIM) {
            return Player.values()[Math.abs(getOpeningPlayer().ordinal() - 1)];
        } else if (whiteBallsLost >= ELIM) {
            return getOpeningPlayer();
        } else {
            throw new IllegalStateException("Both players lost. This should "
                    + "not be possible.");
        }
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
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int row = boardSize - 1; row >= 0; row--) {
            for (int firstD = Math.abs(row - halfBSize);
                 firstD > 0; firstD--) {
                result.append(" ");
            }
            for (int diag = Math.max(row - halfBSize, 0);
                 diag <= Math.min(row + halfBSize, boardSize - 1); diag++) {
                Ball ball = balls[row][diag];
                if (ball == null) {
                    result.append(".");
                } else {
                    result.append(ball.toString());
                }
                result.append(" ");
            }
            result.deleteCharAt(result.length() - 1);
            if (row != 0) {
                result.append(System.lineSeparator());
            }
        }
        return result.toString();
    }

    @Override
    public AbaloneGame clone() throws CloneNotSupportedException {
        AbaloneGame clone = (AbaloneGame) super.clone();
        LinkedList<Ball> clBlackB = new LinkedList<>();
        for (Ball ball : blackBalls) {
            clBlackB.add(ball.clone());
            clone.balls[ball.getRow()][ball.getDiag()] =
                    clone.balls[ball.getRow()][ball.getDiag()].clone();
        }
        LinkedList<Ball> clWhiteB = new LinkedList<>();
        for (Ball ball : whiteBalls) {
            clWhiteB.add(ball.clone());
            clone.balls[ball.getRow()][ball.getDiag()] =
                    clone.balls[ball.getRow()][ball.getDiag()].clone();
        }
        clone.blackBalls = clBlackB;
        clone.whiteBalls = clWhiteB;
        return clone;
    }

    private void fillBalls() {
        Color machineColor = (getHumanColor() == Color.BLACK) ? Color.WHITE
                                                              : Color.BLACK;
        LinkedList<Ball> topBallsList = new LinkedList<>();
        LinkedList<Ball> botBallsList = new LinkedList<>();
        for (int row = 0; row < 2; row++) {
            for (int diag = 0; diag <= halfBSize + row; diag++) {
                botBallsList.add(new Ball(
                        Player.HUMAN, getHumanColor(), row, diag));
                topBallsList.add(new Ball(Player.MACHINE, machineColor,
                        boardSize - (row + 1),
                        boardSize - (diag + 1)));
            }
        }
        for (int diag = 2; diag <= halfBSize; diag++) {
            botBallsList.add(new Ball(
                    Player.HUMAN, getHumanColor(), 2, diag));
            topBallsList.add(
                    new Ball(Player.MACHINE, machineColor,
                            boardSize - 3, boardSize - (diag + 1)));
        }
        if (openingPLayer == Player.HUMAN) {
            blackBalls = botBallsList;
            whiteBalls = topBallsList;
        } else {
            blackBalls = topBallsList;
            whiteBalls = botBallsList;
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
