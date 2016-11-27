package convex_hull.model;

/**
 * This class implements a point in the Euclidean plane as the tuple (x, y).
 */
public class Point implements Comparable<Point> {

    private int x;
    private int y;

    /**
     * Creates new Point object.
     *
     * @param newX x value for the new point.
     * @param newY y value fore the new point.
     */
    public Point(int newX, int newY) {
        x = newX;
        y = newY;
    }

    /**
     * @return x value of the point.
     */
    public int getX() {
        return x;
    }

    /**
     * @return y value of the point.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the point as a String in the Form of: "(x, y)".
     *
     * @return String representation of the point.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Compares this point object with another point. Returns a negative
     * integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object. The order is
     * given by the comparison of the x values first, then the comparison of
     * the y values if both points have the same x value.
     *
     * @param o point which is compared to this point.
     * @return a negative integer, zero, or a positive integer as this point
     * is less than, equal to, or greater than the other point.
     */
    @Override
    public int compareTo(Point o) {
        int result = x - o.getX();
        if (result == 0) {
            result = y - o.getY();
        }
        return result;
    }

    /**
     * Checks if a point lies left, right or collinear to a vector in
     * the Euclidean plane. The vector is created by another point as the
     * head  and this point as the tail of the vector.
     *
     * @param p    the point which position is checked.
     * @param head head of the vector.
     * @return an positive/negative integer if p lies left/right of the
     * vector created by head minus this point, 0 if p is collinear to the vector.
     */
    public int leftOf(Point p, Point head) {
        return ((head.getX() - this.x) * (p.getY() - this.y))
                - ((p.getX() - this.x) * (head.getY() - this.y));
    }

    /**
     * Calculates the distance between two points with the Euclidean norm.
     *
     * @param p the second point.
     * @return the distance between p and this point.
     */
    public double dist(Point p) {
        return Math.sqrt(((this.x - p.getX()) ^ 2) + ((this.y - p.getY()) ^ 2));
    }

    /**
     * Indicates if a object is equal to this point. This is the case if the
     * object is a point and has the same coordinates as this point.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument;
     * {@code false} otherwise.
     * @see #hashCode()
     */
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof Point) {
            Point p = Point.class.cast(obj);
            if ((this.x == p.getX()) && (this.y == p.getY())) {
                result = true;
            }
        }
        return result;
    }

    /**
     *
     * @return hash code value for this point.
     * @see Java.convex_hull.model.Point#equals(Point)
     */
    @Override
    public int hashCode() {
        return x * 23 + y * 53;
    }
}
