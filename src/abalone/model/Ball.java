package abalone.model;

/**
 *
 */
public class Ball implements Cloneable {

    private Player currentPlayer;
    private Color color;
    private int diag;
    private int row;

    public Ball() {
    }

    public Ball(Player currentP, Color color, int diag, int row) {
        this.currentPlayer = currentP;
        this.color = color;
        this.diag = diag;
        this.row = row;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
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

    public void setDiag(int diag1) {
        this.diag = diag1;
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
    public Ball clone() {
        return new Ball(currentPlayer, color, diag, row);
    }

}
