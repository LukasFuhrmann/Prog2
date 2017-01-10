package abalone.model;

/**
 *
 */
public class Ball implements Cloneable {

    private Player player;
    private Color color;
    private int diag;
    private int row;

    public Ball() {
    }

    public Ball(Player player, Color color, int row, int diag) {
        this.player = player;
        this.color = color;
        this.diag = diag;
        this.row = row;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getDiag() {
        return diag;
    }

    public void setDiag(int diag) {
        this.diag = diag;
    }

    public int getDiag2(int size) {
        return row - diag + Math.floorDiv(size, 2);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public Ball clone() throws CloneNotSupportedException {
        try {
            return  (Ball) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return color.toString();
    }

}
