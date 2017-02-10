package abalone.View;

import abalone.model.Board;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Implements the UI frame.
 */
public class GameFrame extends JFrame {

    private BoardPanel boardPanel = new BoardPanel();
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
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopMachineMove();
                e.getWindow().dispose();
            }
        });
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                boardPanel.updateBoard();
                revalidate();
            }
        });
    }

    void stopMachineMove() {
        boardPanel.stopThread();
    }

    void changeLevel(Integer level) {
        boardPanel.changeLevel(level);
    }
}
