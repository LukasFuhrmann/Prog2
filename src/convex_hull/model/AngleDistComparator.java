package convex_hull.model;

import java.util.Comparator;

/**
 * This class implements a comparator which will be used for ordering the
 * points for the graham scan. The points are ordered by the angle they
 * have to a reference point from smallest angle to biggest angle.
 */
public class AngleDistComparator implements Comparator<Point> {


    private Point referenceP;

    /**
     * Creates a AngleDistComparator with initial reference point.
     *
     * @param reference reference point.
     */
    public AngleDistComparator(Point reference) {
        referenceP = reference;
    }

    /**
     * Checks if a point lies left, right or collinear to a vector created by
     * the reference point as tail and a second point as head of the vector in
     * the Euclidean plane.
     *
     * @param p    the point which position is checked.
     * @param head head of the vector.
     * @return an positive(negative) integer if p lies left(right) of the vector
     * or collinear and p(head) is further away from the reference point than
     * head(p). 0 if p and head are equal.
     */
    @Override
    public int compare(Point p, Point head) {
        int result = referenceP.leftOf(p, head);
        if (result == 0) {
            return (int) Math.signum(referenceP.dist(p)
                    - referenceP.dist(head));
        } else {
            return result;
        }
    }
}
