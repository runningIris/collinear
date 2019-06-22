import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {

    private List<LineSegment> collinearLineSegments = new ArrayList<LineSegment>();
    private int num = 0;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("the constructor parameter \"points\" is null");
        }

        List<Point> ps = new ArrayList<Point>();

        Point origin = new Point(0, 0);

        for(Point point: points) {
            point.draw();
            ps.add(point);
        }

        Collections.sort(ps, origin.slopeOrder());

        double prev = 0;
        int count = 0;

        List<Point> tmp = new ArrayList<Point>();

        for(Point point: ps) {
            double current = point.slopeTo(origin);

            StdOut.println(current);
            if (current == prev) {

                tmp.add(point);
                count++;

            } else {

                // 如果超过3个，说明符合条件，加入lineSegments
                if (count > 3/* to be checked */) {
                    LineSegment newLineSegment = new LineSegment(tmp.get(0), tmp.get(count - 1));
                    StdOut.println(newLineSegment);
                    collinearLineSegments.add(newLineSegment);
                    num++;
                } else {
                    count = 0;
                    prev = current;
                    tmp = new ArrayList<Point>();
                    tmp.add(point);
                }
            }
        }
    }
    public int numberOfSegments() {
        return num;
    }
    public LineSegment[] segments() {
        LineSegment[] s = new LineSegment[num];

        for(int i = 0; i < num; i++) {
            s[i] = collinearLineSegments.get(i);
        }
        return s;
    }
    public static void main(String[] args) {

        StdDraw.setPenRadius(0.01);
        StdDraw.setXscale(0, 40000);
        StdDraw.setYscale(0, 40000);
        StdDraw.setPenColor(StdDraw.BLUE);


        int len = StdIn.readInt();
        Point[] points = new Point[len];
        for(int i = 0; i < len; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
        }
        FastCollinearPoints fcp = new FastCollinearPoints(points);

        LineSegment[] segments = fcp.segments();

        for(LineSegment ls: segments) {
            ls.draw();
            StdOut.println(ls.toString());
        }
    }
}
