package abalone.model;

/**
 *
 */
public enum Color {
    WHITE('O'),
    BLACK('X'),
    NONE('.');

    private final char representation;

    Color(char c) {
        representation = c;
    }

    @Override
    public String toString() {
        return representation + "";
    }
}
