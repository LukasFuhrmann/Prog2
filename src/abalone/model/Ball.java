package abalone.model;

/**
 *
 */
public class Ball {

    private Player currentPlayer;
    private Color color;
    private int diag1;
    private int diag2;
    private int row;

    public Ball(Player currentP, Color color, int diag1, int row, int size) {
        this.currentPlayer = currentP;
        this.color = color;
        this.diag1 = diag1;
        this.diag2 =  row - diag1 + Math.floorDiv(size, 2);
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

    public int getDiag1() {
        return diag1;
    }

    public void setDiag1(int diag1) {
        this.diag1 = diag1;
    }

    public int getDiag2() {
        return diag2;
    }

    public void setDiag2(int diag2) {
        this.diag2 = diag2;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }


}
