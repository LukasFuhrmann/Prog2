package abalone.View;

import abalone.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  Implements a panel at the the bottom of the frame which shows the game
 *  score and gives several options for starting new games, changing the
 *  level and closing the program.
 */
class OptionPanel extends JPanel {

    private JButton newB = new JButton("New");
    private JButton switchB = new JButton("Switch");
    private JButton quitB = new JButton("Quit");
    private static final Integer[] sizes = new Integer[]{7, 9, 11, 13, 15};
    private JComboBox<Integer> size = new JComboBox<>(sizes);
    private static final Integer[] levels = new Integer[]{1, 2, 3, 4};
    private JComboBox<Integer> level = new JComboBox<>(levels);
    private JLabel whiteScore = new JLabel(" 14 ");
    private JLabel blackScore = new JLabel(" 14 ");
    private JLabel sizeName = new JLabel(" Size: ");
    private JLabel levelName = new JLabel(" Level: ");


    OptionPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        size.setPreferredSize(new Dimension(70, 30));
        size.setMaximumSize(size.getPreferredSize());
        level.setPreferredSize(size.getPreferredSize());
        level.setMaximumSize(level.getPreferredSize());
        add("Black score", blackScore);
        add("Level tag", levelName);
        add("Level box", level);
        add("Size tag", sizeName);
        add("Size box", size);
        add(new JPanel());
        add("New", newB);
        add("Switch", switchB);
        add("Quit", quitB);
        add(new JPanel());
        add("Black score", blackScore);
        add("White score", whiteScore);
        whiteScore.setForeground(Color.WHITE);
        blackScore.setFont(new Font("Arial", 0, 50));
        whiteScore.setFont(new Font("Arial", 0, 50));
        sizeName.setFont(new Font("Arial", 0, 25));
        levelName.setFont(new Font("Arial", 0, 25));
        sizeName.setVisible(true);
        levelName.setVisible(true);
        level.setSelectedIndex(1);
        size.setSelectedIndex(1);
        addListeners();
    }

    private void addListeners(){
        newB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((GameFrame) getTopLevelAncestor()).newGame(
                        size.getItemAt(size.getSelectedIndex()),
                        level.getItemAt(level.getSelectedIndex()), false);
            }
        });
        switchB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((GameFrame) getTopLevelAncestor()).newGame(
                        size.getItemAt(size.getSelectedIndex()),
                        level.getItemAt(level.getSelectedIndex()), true);

            }
        });
        quitB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((GameFrame)getTopLevelAncestor()).dispose();
            }
        });
        level.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((GameFrame)getTopLevelAncestor()).changeLevel(level
                        .getItemAt(level.getSelectedIndex()));
            }
        });
    }


    void updateScores(Board game) {
        whiteScore.setText(" " +
                game.getNumberOfBalls(abalone.model.Color.WHITE) + " ");
        blackScore.setText(" " +
                game.getNumberOfBalls(abalone.model.Color.BLACK) + " ");
    }
}
