package convex_hull.model;

import java.util.Comparator;

/**
 *
 */
public class AngleDistComparator implements Comparator<Point> {


    private Point referenceP;

    public Point getReferenceP() {
        return referenceP;
    }

    public void setReferenceP(Point referenceP) {
        this.referenceP = referenceP;
    }

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
