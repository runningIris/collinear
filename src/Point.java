import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point>{

    private final int x;
    private final int y;

    public Point(int x, int y) {

        if (x > 32767 || x < 0 || y > 32767 || y < 0) {
            throw new java.lang.IllegalArgumentException("Point coordinate should be between 0 and 32767.");
        }

        this.x = x;
        this.y = y;
    }
    public void draw() {
        StdDraw.point(x, y);
    }
    public void drawTo(Point that) {
        StdDraw.line(x, y, that.x, that.y);
    }
    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* code here */

        if (that == null) {
            throw new NullPointerException();
        }

        if (x == that.x && y == that.y) {
            return Double.NEGATIVE_INFINITY;
        }

        if (x == that.x) {
            return Double.POSITIVE_INFINITY;
        }

        if (y == that.y) {
            return +0.0;
        }

        return (double)(that.y - y) / (that.x - x);
    }
    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* Code here */
        if (x == that.x && y == that.y) {
            return 0;
        }

        if (y < that.y || (y == that.y && x < that.x)) {
            return -1;
        }

        return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* code here */
        return new Comparator<Point>() {
            public int compare(Point o1, Point o2) {
                double slope1 = slopeTo(o1);
                double slope2 = slopeTo(o2);

                if (slope1 == slope2) {
                    return 0;
                }

                if (slope1 < slope2) {
                    return -1;
                }

                return 1;
            }
        };
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    public static void main(String args[]) {
        Point a = new Point(11, 51);
        Point b = new Point(21, 1);
        Point c = new Point(3, 8);
        Point o = new Point(0, 0);

        Point[] arr = new Point[3];
        arr[0] = a;
        arr[1] = b;
        arr[2] = c;

        Arrays.sort(arr, o.slopeOrder());

        for (int i = 0; i < arr.length; i++) {
            StdOut.println(arr[i].toString());
        }
    }
}
