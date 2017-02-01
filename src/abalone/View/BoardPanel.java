package abalone.View;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
class BoardPanel extends JPanel {

    BoardPanel(int boardsize) {
        super();
        setLayout(new GridLayout(boardsize + 2, 2 * boardsize + 3));
        for (int row = boardsize + 2; row > 0; row--) {
            for (int column = 0; column < 2 * boardsize + 3; column++) {

            }
        }
    }
}
