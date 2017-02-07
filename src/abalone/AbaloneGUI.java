package abalone;

import abalone.View.GameFrame;

/**
 * Starts the program for an interpretation of Abalone with a GUI.
 */
public final class AbaloneGUI {

    private AbaloneGUI(){
    }

    /**
     * Starts the program.
     *
     * @param args not used.
     */
    public static void main(String[] args) {
        Thread eventDispatcher = new Thread(){

            /**
             * {@inheritDoc}
             */
            @Override
            public void run() {
                GameFrame frame = new GameFrame();
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        eventDispatcher.start();
    }
}
