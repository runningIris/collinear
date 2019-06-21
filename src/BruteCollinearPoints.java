import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {

    public BruteCollinearPoints(Point[] points) {
        int pl = points.length;
        Point origin = new Point(0, 0);
        for (int i = 0; i < pl - 3; i++) {
            Point p = points[i];
            for (int j = i + 1; j < pl - 2; j++) {
                Point q = points[j];
                double pq = p.slopeTo(q);
                for (int k = j + 1; k < pl - 1; k++) {
                    Point r = points[k];
                    double pr = p.slopeTo(r);
                    if (pq != pr) {
                        break;
                    }
                    for (int l = k + 1; l < pl; l++) {
                        // System.out.println("(" + i + ", " + j + ", " + k + ", " + l + ')');
                        Point s = points[l];
                        double ps = p.slopeTo(s);
                        if (pq != ps) {
                            break;
                        }
                        Point[] arr = new Point[]{p, q, r, s};
                        Arrays.sort(arr, origin.slopeOrder());
                        StdOut.println(arr[0].toString() + " -> " + arr[3].toString());
                        StdDraw.setPenColor(StdDraw.RED);
                        StdDraw.setPenRadius(0.01);
                        StdDraw.setXscale(0, 50000);
                        StdDraw.setYscale(0, 50000);
                        new LineSegment(arr[0], arr[3]).draw();
                    }
                }
            }
        }
    }
    public int numberOfSegments() {
        return 1;
    }
    public LineSegment[] segments() {
        return null;
    }
    public static void main(String[] args) {
        int len = StdIn.readInt();
        Point[] points = new Point[len];

        int i = -1;
        int x = 0;
        int y = 0;

        while(!StdIn.isEmpty()) {
            i++;
            if (i % 2 == 0) {
                y = StdIn.readInt();
                points[i / 2] = new Point(x, y);
            } else {
                x = StdIn.readInt();
            }
        }

        new BruteCollinearPoints(points);
    }
}
