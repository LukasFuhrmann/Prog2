package abalone.model;


import abalone.Utility;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Implementation of a Abalone inspired game. This class implements the game
 * logic.
 */
public class AbaloneGame implements Board, Cloneable {

    private int boardSize = 9;
    private int halfBoard = Math.floorDiv(boardSize, 2);
    private Player openingPLayer = Player.HUMAN;
    private Player currentP = Player.HUMAN;
    private Ball[][] balls;
    private LinkedList<Ball> blackBalls = new LinkedList<>();
    private LinkedList<Ball> whiteBalls = new LinkedList<>();
    private int whiteBallsLost = 0;
    private int blackBallsLost = 0;
    private int level;
    private static final double WIN_VALUE = 5000000.0;
    private static final double HUMAN_VALUE = 1.5;

    /**
     * Constructor that starts the standard game.
     *
     * @param level difficulty of the AI
     */
    public AbaloneGame(int level) {
        this.level = level;
        balls = new Ball[boardSize][boardSize];
        fillBalls();
    }

    /**
     * Constructor that starts a new game, changing the size of the board.
     *
     * @param size             new size of the board.
     *                         Must be above {@code MIN_SIZE}.
     * @param oldOpeningPLayer the opening player of the new game.
     * @param level            difficulty of the AI
     */
    public AbaloneGame(int size, Player oldOpeningPLayer, int level) {
        if (size < MIN_SIZE || size % 2 == 0) {
            throw new IllegalArgumentException("Board size has to be odd "
                    + "and at least " + MIN_SIZE + ".");
        } else {
            openingPLayer = oldOpeningPLayer;
            currentP = openingPLayer;
            boardSize = size;
            halfBoard = Math.floorDiv(boardSize, 2);
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
     * @param oldSize          old size of the board.
     * @param level            difficulty of the AI
     */
    public AbaloneGame(Player oldOpeningPlayer, int oldSize, int level) {
        openingPLayer = (oldOpeningPlayer == Player.HUMAN) ? Player.MACHINE
                                                           : Player.HUMAN;
        currentP = openingPLayer;
        boardSize = oldSize;
        halfBoard = Math.floorDiv(boardSize, 2);
        this.level = level;
        balls = new Ball[boardSize][boardSize];
        fillBalls();
    }


    private Player getCurrentP() {
        return currentP;
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
        return currentP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidPosition(int row, int diag) {
        return 0 <= row && row < boardSize
                && Math.max(0, row - halfBoard) <= diag
                && diag <= Math.min(row + halfBoard, boardSize - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidTarget(int row, int diag) {
        return -1 <= row && row <= boardSize
                && Math.max(0, row - halfBoard) <= diag + 1
                && diag - 1 <= Math.min(row + halfBoard, boardSize - 1);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Board move(int rowFrom, int diagFrom, int rowTo, int diagTo) {
        updateCurrentPlayer();
        if (!(isValidPosition(rowFrom, diagFrom)
                || isValidTarget(rowTo, diagTo))) {
            throw new IllegalArgumentException("Illegal coordinates.");
        } else if (currentP == Player.MACHINE || isGameOver()) {
            throw new IllegalStateException();
        } else {
            return playerMove(rowFrom, diagFrom, rowTo, diagTo);
        }
    }

    private Board playerMove(int rowFrom, int diagFrom, int rowTo, int diagTo) {
        if (balls[rowFrom][diagFrom] == null
                || balls[rowFrom][diagFrom].getPlayer() != currentP) {
            return null;
        } else if (Utility.isNextTo(rowFrom, diagFrom, rowTo, diagTo)) {
            int rowD = rowTo - rowFrom; //row direction
            int diagD = diagTo - diagFrom; //diag direction
            int ownBalls = 1;
            int oppBalls = 0;
            int ballCount = 1;
            while (isValidPosition(rowTo, diagTo)
                    && balls[rowTo][diagTo] != null) {
                if (oppBalls > 0
                        && balls[rowTo][diagTo].getPlayer() == currentP) {
                    return null;
                } else if (balls[rowTo][diagTo].getPlayer() == currentP) {
                    ++ownBalls;
                } else if (oppBalls + 1 >= ownBalls) {
                    return null;
                } else {
                    ++oppBalls;
                }
                rowTo += rowD;
                diagTo += diagD;
                ++ballCount;
            }
            AbaloneGame clone = null;
            try {
                clone = clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            if (!isValidPosition(rowTo, diagTo)
                    && isValidTarget(rowTo, diagTo)) {
                removeElim(clone, rowTo - rowD, diagTo - diagD);
                --ballCount;
            }
            while (ballCount > 0) {
                changeBallCoor(clone, rowFrom, rowD, diagFrom, diagD,
                        ballCount - 1);
                --ballCount;
            }
            clone.balls[rowFrom][diagFrom] = null;
            clone.currentP = Player.values()[Math.abs(currentP.ordinal() - 1)];
            return clone;
        } else {
            return null;
        }
    }

    private void changeBallCoor(AbaloneGame clone, int rowFrom, int rowD,
                                int diagFrom, int diagD, int pos) {
        int origR = rowFrom + rowD * pos;
        int origD = diagFrom + diagD * pos;
        clone.setBall(origR + rowD, origD + diagD,
                clone.getBall(origR, origD));
        clone.getBall(origR + rowD, origD + diagD).setRow(origR + rowD);
        clone.getBall(origR + rowD, origD + diagD).setDiag(origD + diagD);
    }

    private Ball getBall(int row, int diag) {
        return balls[row][diag];
    }

    private void setBall(int row, int diag, Ball ball) {
        balls[row][diag] = ball;
    }

    private void removeElim(AbaloneGame clone, int origR, int origD) {
        Ball lost = clone.getBall(origR, origD);
        if (lost.getColor() == Color.BLACK) {
            clone.getBlackBalls().remove(lost);
            ++clone.blackBallsLost;
        } else {
            clone.getWhiteBalls().remove(lost);
            ++clone.whiteBallsLost;
        }
        clone.balls[origR][origD] = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board machineMove() {
        updateCurrentPlayer();
        if (currentP == Player.HUMAN || isGameOver()) {
            throw new IllegalStateException();
        } else {
            Node root = new Node(this);
            buildGameTree(0, root);
            return evaluateTree(root, 1).getGame();
        }
    }

    private void buildGameTree(int depth, Node root) {
        if (depth < level && !root.getGame().isGameOver()) {
            AbaloneGame game = (AbaloneGame) root.getGame();
            game.updateCurrentPlayer();
            if (game.getCurrentP() == openingPLayer) {
                game.fillPossible(game.getBlackBalls(), root, depth);
            } else {
                game.fillPossible(game.getWhiteBalls(), root, depth);
            }
        }
    }

    private void fillPossible(LinkedList<Ball> playerBallList, Node root,
                              int depth) {
        int[][] direction =
                {{0, 1}, {1, 1}, {1, 0}, {0, -1}, {-1, -1}, {-1, 0}};
        for (Ball ball : playerBallList) {
            for (int i = 0; i < 6; i++) {
                AbaloneGame move = (AbaloneGame) playerMove(ball.getRow(),
                        ball.getDiag(), ball.getRow() + direction[i][0],
                        ball.getDiag() + direction[i][1]);
                if (move != null) {
                    Node child = new Node(move, move.valueBoard(depth));
                    root.addChild(child);
                    move.buildGameTree(depth + 1, child);
                }
            }
        }
    }

    private double valueBoard(int depth) {
        double valueHu = 0;
        double valueMa = 0;
        if (isGameOver() && getWinner() == Player.HUMAN) {
            valueHu = WIN_VALUE / depth;
        } else if (isGameOver() && getWinner() == Player.HUMAN) {
            valueMa = WIN_VALUE / depth;
        }
        double posV = 0;
        for (int row = 0; row <= halfBoard; row++) {
            double distRow = Math.min(row, boardSize - (row + 1));
            for (Ball ball : balls[row]) {
                if (ball != null) {
                    double distDia1 = Math.min(ball.getDiag(),
                            boardSize - (ball.getDiag() + 1));
                    double distDia2 = Math.min(ball.getDiag2(boardSize),
                            boardSize - (ball.getDiag2(boardSize) + 1));
                    double dist = Math.min(
                            distRow, Math.min(distDia1, distDia2));
                    posV += (ball.getPlayer() == Player.MACHINE)
                            ? dist : (-HUMAN_VALUE * dist);
                }
            }
        }
        double ballVal = getNumberOfBalls(machineColor())
                - HUMAN_VALUE * getNumberOfBalls(getHumanColor());
        return boardSize * ballVal + (valueMa - 1.5 * valueHu) + posV;
    }

    private Node evaluateTree(Node root, int depth) {
        Node bestOption = root;
        ArrayList<Node> bucket = new ArrayList<>();
        for (Node node : root.getChildren()) {
            bucket.add(evaluateTree(node, depth + 1));
        }
        if (bucket.size() > 0) {
            bestOption = bucket.get(0);
        }
        for (Node node : bucket) {
            if (node.getGame().getNextPlayer() == Player.HUMAN) {
                bestOption = (node.compareTo(bestOption) <= 0) ? bestOption
                                                               : node;
            } else {
                bestOption = (node.compareTo(bestOption) >= 0) ? bestOption
                                                               : node;
            }
        }
        if (bestOption != root) {
            root.setScore(bestOption.getScore());
        }
        if (depth == 1) {
            return bestOption;
        } else {
            return root;
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setLevel(int level) {
        this.level = level;
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
            return Player.values()[Math.abs(openingPLayer.ordinal() - 1)];
        } else {
            return getOpeningPlayer();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfBalls(Color color) {
        return (color == Color.BLACK) ? blackBalls.size()
                                      : whiteBalls.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getSlot(int row, int diag) {
        return (balls[row][diag] == null) ? Color.NONE
                                          : balls[row][diag].getColor();
    }

    private LinkedList<Ball> getBlackBalls() {
        return blackBalls;
    }

    private LinkedList<Ball> getWhiteBalls() {
        return whiteBalls;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return boardSize;
    }

    /**
     * Return a string representation of this board similar to the original
     * look of Abalone.
     *
     * @return string representation of the
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int row = boardSize - 1; row >= 0; row--) {
            for (int firstD = Math.abs(row - halfBoard);
                 firstD > 0; firstD--) {
                result.append(" ");
            }
            for (int diag = Math.max(row - halfBoard, 0);
                 diag <= Math.min(row + halfBoard, boardSize - 1); diag++) {
                Ball ball = balls[row][diag];
                if (ball == null) {
                    result.append(".");
                } else {
                    result.append(ball);
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

    /**
     * Creates deep copy of this game.
     *
     * @return deep copy of this object.
     * @throws CloneNotSupportedException if {@code super} does not implement
     *                                    clone().
     */
    @Override
    public AbaloneGame clone() throws CloneNotSupportedException {
        AbaloneGame clone = (AbaloneGame) super.clone();
        clone.balls = new Ball[clone.boardSize][clone.boardSize];
        clone.blackBalls = new LinkedList<>();
        for (Ball ball : blackBalls) {
            Ball ballCl = ball.clone();
            clone.blackBalls.add(ballCl);
            clone.balls[ball.getRow()][ball.getDiag()] = ballCl;
        }
        clone.whiteBalls = new LinkedList<>();
        for (Ball ball : whiteBalls) {
            Ball ballCl = ball.clone();
            clone.whiteBalls.add(ballCl);
            clone.balls[ball.getRow()][ball.getDiag()] = ballCl;
        }
        return clone;
    }

    private void fillBalls() {
        LinkedList<Ball> topBallsList = new LinkedList<>();
        LinkedList<Ball> botBallsList = new LinkedList<>();
        for (int row = 0; row < 2; row++) {
            for (int diag = 0; diag <= halfBoard + row; diag++) {
                botBallsList.add(new Ball(
                        Player.HUMAN, getHumanColor(), row, diag));
                topBallsList.add(new Ball(Player.MACHINE, machineColor(),
                        boardSize - (row + 1), boardSize - (diag + 1)));
            }
        }
        int thirdRow = 2;
        for (int diag = thirdRow; diag <= halfBoard; diag++) {
            botBallsList.add(new Ball(
                    Player.HUMAN, getHumanColor(), thirdRow, diag));
            topBallsList.add(new Ball(Player.MACHINE, machineColor(),
                    boardSize - (thirdRow + 1), boardSize - (diag + 1)));
        }
        if (openingPLayer == Player.HUMAN) {
            blackBalls.addAll(botBallsList);
            whiteBalls.addAll(topBallsList);
        } else {
            blackBalls.addAll(topBallsList);
            whiteBalls.addAll(botBallsList);
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

    private Color machineColor() {
        return (getHumanColor() == Color.BLACK) ? Color.WHITE : Color.BLACK;
    }

    private void updateCurrentPlayer() {
        currentP = getNextPlayer();
    }
}
