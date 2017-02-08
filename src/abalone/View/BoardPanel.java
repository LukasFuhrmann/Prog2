package abalone.View;

import abalone.Utility;
import abalone.model.AbaloneGame;
import abalone.model.Board;
import abalone.model.Player;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;


/**
 * Implements the panel for the representation of the game board.
 */
class BoardPanel extends JPanel {

    private Board game = new AbaloneGame(2);
    private LinkedList<SlotPanel> slotList = new LinkedList<>();
    private Thread machineMove;
    private MouseListener listener = new BoardListener();

    BoardPanel(int size) {
        super();
        builtBoard(size);
    }

    BoardPanel(int size, int level, boolean switched, BoardPanel oldPanel) {
        super();
        if (switched) {
            game = new AbaloneGame(oldPanel.game.getOpeningPlayer(), size,
                    level);
        } else {
            game = new AbaloneGame(size, oldPanel.game.getOpeningPlayer(),
                    level);
        }
        builtBoard(size);
        if (game.getOpeningPlayer() == Player.MACHINE) {
            machineMove = new Thread() {
                @Override
                public void run() {
                    game = game.machineMove();
                }
            };
            machineMove.start();
            SwingUtilities.invokeLater(machineMove);
        }
    }

    void changeLevel(Integer level) {
        game.setLevel(level);
    }

    @Deprecated
    void stopThread() {
        if ((machineMove != null) && machineMove.isAlive()) {
            machineMove.stop();
        }
    }

    private void builtBoard(int size) {
        setLayout(new GridLayout(size + 2, (2 * size) + 3));
        for (int row = size + 1; row >= 0; row--) {
            for (int spaces = Utility.calcSpace(size + 2, row); spaces > 0;
                 spaces--) {
                add(new GroundPanel());
            }
            BorderPanel border = new BorderPanel(row - 1,
                    Utility.firstDiag(size + 2, row) - 1);
            add(border);
            border.addMouseListener(listener);
            GapPanel gap = new GapPanel();
            add(gap);
            gap.addMouseListener(listener);
            for (int diag = Utility.firstDiag(size + 2, row) + 1;
                 diag <= Utility.lastDiag(size + 2, row) - 1; diag++) {
                if (row == 0 || row == size + 1) {
                    BorderPanel egde = new BorderPanel(row - 1, diag - 1);
                    add(egde);
                    egde.addMouseListener(listener);
                    GapPanel gapInside = new GapPanel();
                    add(gapInside);
                    gapInside.addMouseListener(listener);
                } else {
                    SlotPanel slot = new SlotPanel(row - 1, diag - 1);
                    add(slot);
                    slotList.add(slot);
                    slot.addMouseListener(listener);
                    GapPanel gapInside = new GapPanel();
                    add(gapInside);
                    gapInside.addMouseListener(listener);
                }
            }
            border = new BorderPanel(row - 1,
                    Utility.lastDiag(size + 2, row) - 1);
            add(border);
            border.addMouseListener(listener);
            for (int spaces = Utility.calcSpace(size + 2, row); spaces > 0;
                 spaces--) {
                add(new GroundPanel());
            }
        }
    }

    void updateBoard() {
        for (SlotPanel slot : slotList) {
            slot.repaint();
        }
        ((GameFrame) getTopLevelAncestor()).updatePanelScores(game);
    }

    private void gameOver() {
        String message = "Congratulations, you won! Play again? Press"
                + " new!";
        if (game.getWinner() == Player.MACHINE) {
            message = "Sorry, you lost! Play again? Press new!";
        }
        JOptionPane.showMessageDialog(getTopLevelAncestor(), message, "Game "
                + "over!", JOptionPane.INFORMATION_MESSAGE);
        for (Component comp : getComponents()) {
            comp.removeMouseListener(listener);
        }
    }

    private void updateClickedPanel() {
        for (SlotPanel slotPanel : slotList) {
            slotPanel.updateBackground();
        }
    }

    private void checkGame() {
        if (game.isGameOver()) {
            gameOver();
        } else {
            machineMove = new Thread() {
                @Override
                public void run() {
                    game = game.machineMove();
                    updateBoard();
                    if (game.isGameOver()) {
                        gameOver();
                    }
                }
            };
            SwingUtilities.invokeLater(machineMove);
        }
    }

    private class BoardListener implements MouseListener {

        private boolean ballSelected = false;
        private int selectedRow;
        private int selectedDiag;

        BoardListener() {
            super();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!(e.getSource() instanceof BorderPanel)
                    && e.getSource() instanceof GapPanel) {
                Toolkit.getDefaultToolkit().beep();
            } else if (ballSelected) {
                checkSecondBall(e);
            } else {
                if (e.getSource() instanceof SlotPanel) {
                    int clickedRow = ((BorderPanel) e.getSource()).row;
                    int clickedDiag = ((BorderPanel) e.getSource()).diag;
                    if (game.getSlot(clickedRow, clickedDiag)
                            == game.getHumanColor()) {
                        ballSelected = true;
                        selectedRow = clickedRow;
                        selectedDiag = clickedDiag;
                        ((SlotPanel) e.getSource()).setBackground(Color.BLUE);
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }

        private void checkSecondBall(MouseEvent e) {
            int clickedRow = ((BorderPanel) e.getSource()).row;
            int clickedDiag = ((BorderPanel) e.getSource()).diag;
            if (selectedDiag == clickedDiag && selectedRow == clickedRow) {
                ballSelected = false;
                updateClickedPanel();
            } else if (Utility.isNextTo(selectedRow, selectedDiag,
                    clickedRow, clickedDiag)) {
                Board saveGame = game;
                game = game.move(selectedRow, selectedDiag, clickedRow,
                        clickedDiag);
                if (game == null) {
                    game = saveGame;
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(getTopLevelAncestor(),
                            "Error! Illegal move!", "Careful!",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    ballSelected = false;
                    updateClickedPanel();
                    updateBoard();
                    checkGame();
                }
            } else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(getTopLevelAncestor(), "Error!"
                                + " Illegal move!", "Careful!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    class GroundPanel extends JPanel {

        protected final Dimension prefSize = new Dimension(20, 20);
        protected final Color background = new Color(205, 133, 63);

        GroundPanel() {
            super();
            setPreferredSize(prefSize);
            setBackground(background);
        }

    }

    class GapPanel extends GroundPanel {

        GapPanel() {
            super();
        }

        @Override
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.setColor(Color.BLACK);
            graphics.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        }
    }

    class BorderPanel extends GapPanel {

        protected int diag;
        protected int row;

        BorderPanel(int row, int diag) {
            super();
            this.row = row;
            this.diag = diag;
        }

        @Override
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.drawLine(0, 0, getWidth(), getHeight());
            graphics.drawLine(getWidth(), 0, 0, getHeight());
        }
    }

    class SlotPanel extends BorderPanel {

        SlotPanel(int row, int diag) {
            super(row, diag);
        }

        @Override
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.setColor(Utility.parseColor(game.getSlot(row, diag)));
            graphics.fillOval(0, 0, getWidth(), getHeight());
        }

        void updateBackground() {
            setBackground(background);
        }
    }
}
