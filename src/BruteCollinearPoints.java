import java.util.*;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
    private List<LineSegment> collinearLineSegments = new ArrayList<LineSegment>();

    private int lineNum = 0;

    private boolean isEqual(double v1, double v2) {
        return v1 == v2;
    }

    public BruteCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException("The constructor argument \"points\" should not be null. ");
        }

        // Duplicate points to be handled
        List<Point> all = new ArrayList<Point>();

        for (Point point: points) {
            if (all.contains(point)) {
                throw new java.lang.IllegalArgumentException("Duplicate points argument in constructor. ");
            }

            all.add(point);
        }

        int pl = points.length;

        for (int i = 0; i < pl - 3; i++) {
            Point p = points[i];

            for (int j = i + 1; j < pl - 2; j++) {
                Point q = points[j];
                double pq = p.slopeTo(q);

                for (int k = j + 1; k < pl - 1; k++) {
                    Point r = points[k];
                    double pr = p.slopeTo(r);

                    if (!isEqual(pq, pr)) {
                        continue;
                    }

                    for (int l = k + 1; l < pl; l++) {
                        Point s = points[l];
                        double ps = p.slopeTo(s);
                        if (!isEqual(pq, ps)) {
                            continue;
                        }

                        List<Point> collinearPoints = new ArrayList<Point>();
                        collinearPoints.add(p);
                        collinearPoints.add(q);
                        collinearPoints.add(r);
                        collinearPoints.add(s);
                        Collections.sort(collinearPoints);
                        Point min = collinearPoints.get(0);
                        Point max = collinearPoints.get(3);

                        collinearLineSegments.add(new LineSegment(max, min));

                        lineNum++;
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return lineNum;
    }

    public LineSegment[] segments() {
        LineSegment[] s = new LineSegment[lineNum];
        for(int i = 0; i < lineNum; i++) {
            s[i] = collinearLineSegments.get(i);
        }
        return s;
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.001);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
