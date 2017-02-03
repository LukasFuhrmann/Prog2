package abalone.View;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class GameFrame extends JFrame {

    private JPanel boardPanel = new BoardPanel(9);
    private OptionPanel optionPanel = new OptionPanel();

    public GameFrame() {
        super("Abalone");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().add(boardPanel, BorderLayout.CENTER);
        getContentPane().add(optionPanel, BorderLayout.SOUTH);
        setSize(1000,700);
        setVisible(true);
    }

    public static void main(String[] args) {
        GameFrame g1 = new GameFrame();
    }
}
