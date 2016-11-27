package convex_hull.model;

import java.util.Comparator;

/**
 * This class implements a point in the Euclidean plane.
 */
public class AngleDistComparator implements Comparator<Point> {


    private Point referenceP;

    public AngleDistComparator(Point reference) {
        referenceP = reference;
    }

    /**
     * Checks if a point lies left, right or collinear to a vector created by
     * two other points in the Euclidean plane.
     *
     * @param p the point which position is checked.
     * @param q the point which creates the vector through q minus the
     *          reference point.
     * @return an positive(negative) integer if p lies left(right) of the vector
     * created by q minus the reference point or collinear and p(q) is further
     * away from the reference point than q(p). 0 if p and q are equal.
     */
    @Override
    public int compare(Point p, Point q) {
        int result = referenceP.leftOf(p, q);
        if (result == 0) {
            return (int) Math.signum(referenceP.dist(p) - referenceP.dist(q));
        } else {
            return result;
        }
    }
}
