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

    public void setDiag(int neWdiag) {
        this.diag = neWdiag;
    }

    public int getDiag2(int size) {
        return row - diag + Math.floorDiv(size, 2);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int neWrow) {
        this.row = neWrow;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ball ball = (Ball) o;

        if (diag != ball.diag) return false;
        if (row != ball.row) return false;
        if (player != ball.player) return false;
        return color == ball.color;
    }

    @Override
    public int hashCode() {
        int result = player.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + diag;
        result = 31 * result + row;
        return result;
    }
}
