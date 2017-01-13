package abalone.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements a tree structure for {@code machineMove()} to calculate the
 * move for the machine.
 */
public class Node implements Comparable {

    private List<Node> children = new ArrayList<>();
    private Board game;
    private double score;

    Node(Board game, double score) {
        this.game = game;
        this.score = score;
    }

    Node(Board game) {
        this.game = game;
    }

    Board getGame() {
        return game;
    }


    void addChild(Node child) {
        children.add(child);
    }

    double getScore() {
        return score;
    }

    void setScore(double score) {
        this.score = score;
    }

    List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Object o) {
        if (o == null) {
            throw new NullPointerException();
        } else if (o.getClass() != getClass()) {
            throw new ClassCastException();
        }
        Node node = (Node) o;
        return (int) (score - node.getScore());
    }
}
