package abalone.View;

import abalone.model.Board;

import javax.swing.JFrame;
import java.awt.BorderLayout;

/**
 * Implements the UI frame.
 */
public class GameFrame extends JFrame {

    private BoardPanel boardPanel = new BoardPanel(9);
    private OptionPanel optionPanel = new OptionPanel();

    /**
     * Creates new GameFrame with a game of the level 2 and size 9.
     */
    public GameFrame() {
        super("Abalone");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().add(boardPanel, BorderLayout.CENTER);
        getContentPane().add(optionPanel, BorderLayout.SOUTH);
        setSize(1000, 700);
        setVisible(true);
    }

    void updatePanelScores(Board game) {
        optionPanel.updateScores(game);
    }

    void newGame(int size, int level, boolean switched) {
        stopMachineMove();
        getContentPane().remove(boardPanel);
        boardPanel.removeAll();
        boardPanel = new BoardPanel(size, level, switched, boardPanel);
        getContentPane().add(boardPanel, BorderLayout.CENTER);
        boardPanel.updateBoard();
        revalidate();
    }

    @SuppressWarnings("deprecation")
    private void stopMachineMove() {
        boardPanel.stopThread();
    }

    void changeLevel(Integer itemAt) {
        boardPanel.changeLevel(itemAt);
    }
}
