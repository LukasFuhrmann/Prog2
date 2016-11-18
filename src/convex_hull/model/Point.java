package convex_hull.model;

import java.util.Objects;

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
        int result = this.getX() - o.getX();
        if (result == 0) {
            result = this.getY() - o.getY();
        }
        return result;
    }

    /**
     * @param p
     * @param q
     * @return
     */
    public int leftOf(Point p, Point q) {
        int result = 0;
        return result = ((q.getX() - this.getX()) * (p.getY() - this.getY()))
                - ((p.getX() - this.getX()) * (q.getY() - this.getY()));
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
        return x + y + 2000;
    }
}
