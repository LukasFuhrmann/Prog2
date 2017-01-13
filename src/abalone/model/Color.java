package abalone.model;

/**
 * Implements the colors of the balls and empty slots.
 */
public enum Color {
    /**
     * Color of the second player.
     */
    WHITE('O'),
    /**
     * Color of the opening player
     */
    BLACK('X'),
    /**
     * Color of the slots.
     */
    NONE('.');

    private final char representation;

    Color(char c) {
        representation = c;
    }

    /**
     * @return String representation of this object.
     */
    @Override
    public String toString() {
        return representation + "";
    }

}
