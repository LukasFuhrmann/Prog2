package abalone.model;

/**
 * Implements the colors of the balls and empty slots.
 */
public enum Color {
    WHITE('O'),
    BLACK('X'),
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
