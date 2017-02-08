package abalone;

import abalone.View.GameFrame;

import javax.swing.SwingUtilities;

/**
 * Starts the program for an interpretation of Abalone with a GUI.
 */
public final class AbaloneGUI {

    private AbaloneGUI() {
    }

    /**
     * Starts the program.
     *
     * @param args not used.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Thread() {
            @Override
            public void run() {
                GameFrame frame = new GameFrame();
            }
        });
    }
}
