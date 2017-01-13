package abalone.model;

/**
 *
 */
public class Ball implements Cloneable {

    private Player player;
    private Color color;
    private int diag;
    private int row;


    Ball(Player player, Color color, int row, int diag) {
        this.player = player;
        this.color = color;
        this.diag = diag;
        this.row = row;
    }

    Player getPlayer() {
        return player;
    }

    Color getColor() {
        return color;
    }

    int getDiag() {
        return diag;
    }

    void setDiag(int newDiag) {
        this.diag = newDiag;
    }

    int getDiag2(int size) {
        return row - diag + Math.floorDiv(size, 2);
    }

    int getRow() {
        return row;
    }

    void setRow(int newRow) {
        this.row = newRow;
    }

    /**
     * Creates deep copy of this ball.
     *
     * @return deep copy of this object.
     * @throws CloneNotSupportedException if {@code super} does not implement
     *                                    clone().
     */
    @Override
    public Ball clone() throws CloneNotSupportedException {
        return (Ball) super.clone();
    }

    /**
     * @return string representation of this ball
     */
    @Override
    public String toString() {
        return color.toString();
    }

}
