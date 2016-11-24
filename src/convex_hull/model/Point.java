package convex_hull.model;

/**
 *
 */
public class Point implements Comparable<Point> {

    private int x;
    private int y;

    public Point(int newX, int newY) {
        x = newX;
        y = newY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Point o) {
        int result = x - o.getX();
        if (result == 0) {
            result = y - o.getY();
        }
        return result;
    }

    /**
     * Checks if a point lies left, right or collinear to a vector created by
     * two other points in the Euclidean plane.
     *
     * @param p the point which position is checked.
     * @param q the point which creates the vector through q minus this point.
     * @return an positive/negative integer if p lies left/right of the
     * vector created by q minus this point, 0 if p is collinear to the vector.
     */
    public int leftOf(Point p, Point q) {
        return ((q.getX() - this.x) * (p.getY() - this.y))
                - ((p.getX() - this.x) * (q.getY() - this.y));
    }

    public double dist(Point p) {
        return Math.sqrt((this.x - p.getX()) ^ 2 + (this.y - p.getY()) ^ 2);
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof Point) {
            Point p = Point.class.cast(o);
            if ((this.x == p.getX()) && (this.y == (p.getY()))) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        return x * 23 + y * 53;
    }
}
