package abalone.View;

import abalone.Game;
import abalone.Utility;
import abalone.model.AbaloneGame;
import abalone.model.Board;
import abalone.model.Player;

import javax.swing.*;
import java.awt.*;


/**
 *  Implements
 */
class BoardPanel extends JPanel {

    private Board game = new AbaloneGame(2);

    BoardPanel(int size) {
        super();
        setLayout(new GridLayout(size + 2, (2 * size) + 3));
        for (int row = size + 1; row >= 0; row--) {
            for (int firstD = Utility.calcSpace(size + 2, row); firstD > 0;
                 firstD--) {
                add(new GroundPanel());
            }
            add(new BorderPanel());
            add(new GapPanel());
            for (int diag = Utility.firstDiag(size + 2, row) + 1;
                 diag <= Utility.lastDiag(size + 2, row) - 1; diag++) {
                if (row == 0 || row == size + 1) {
                    add(new BorderPanel());
                    add(new GapPanel());
                } else {
                    add(new SlotPanel(row - 1, diag - 1));
                    add(new GapPanel());
                }
            }
            add(new BorderPanel());
            for (int firstD = Utility.calcSpace(size + 2, row); firstD > 0;
                 firstD--) {
                add(new GroundPanel());
            }
        }
    }

    class GroundPanel extends JPanel {

        protected final Dimension PREF_SIZE = new Dimension(10, 10);
        protected final Color BACK_GROUND = new Color(205,133,63);

        GroundPanel() {
            super();
            setPreferredSize(PREF_SIZE);
            setBackground(BACK_GROUND);
        }

    }

    class GapPanel extends GroundPanel {

        GapPanel(){
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

        BorderPanel(){
            super();
        }

        @Override
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.setColor(Color.BLACK);
            graphics.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
            graphics.drawLine(0, 0, getWidth(), getHeight());
            graphics.drawLine(getWidth(), 0, 0, getHeight());
        }
    }

    class SlotPanel extends BorderPanel {

        private int diag;
        private int row;

        SlotPanel(int row, int diag) {
            super();
            this.row = row;
            this.diag = diag;
        }

        @Override
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.setColor(Color.BLACK);
            graphics.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
            graphics.drawLine(0, 0, getWidth(), getHeight());
            graphics.drawLine(getWidth(), 0, 0, getHeight());
            graphics.setColor(Utility.parseColor(game.getSlot(row, diag)));
            graphics.fillOval(0,0,getWidth(),getHeight());
        }

    }

}
