import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private List<LineSegment> collinearLineSegments = new ArrayList<LineSegment>();
    private int num = 0;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("the constructor parameter \"points\" is null");
        }

        // Duplicate points to be handled
        // x and y coordinates between 0 and 32767
        for (Point origin: points) {
            List<Point> subPoints = new ArrayList<Point>();
            List<Point> tmp = new ArrayList<Point>();

            Collections.addAll(subPoints, points);
            Collections.sort(subPoints, origin.slopeOrder());

            double prev = subPoints.get(0).slopeTo(origin);
            int count = 1;
            int subLength = points.length;

            for(int k = 0; k < subLength; k++) {

                Point point = subPoints.get(k);
                double current = point.slopeTo(origin);

                if (count > 1 && current == prev) {
                    // 如果是最后一个，存起来 LineSegment
                    if (k == subLength - 1) {
                        tmp.add(origin);
                        Collections.sort(tmp);
                        LineSegment newLineSegment = new LineSegment(tmp.get(0), tmp.get(count));
                        collinearLineSegments.add(newLineSegment);
                        num++;

                        // 如果超过3个，说明符合条件，加入lineSegments
                    } else {

                        // 存在 next 的情况，需要考虑 next == current 时不做任何操作，等遍历到下一个, 否则存起来当时的 LineSegment
                        Point nextPoint = subPoints.get(k + 1);
                        double next = nextPoint.slopeTo(origin);
                        if (next != current) {
                            tmp.add(origin);
                            Collections.sort(tmp);
                            LineSegment newLineSegment = new LineSegment(tmp.get(0), tmp.get(count));
                            collinearLineSegments.add(newLineSegment);
                            num++;
                        }
                    }
                }

                if (current == prev) {
                    tmp.add(point);
                    count++;
                } else {
                    count = 1;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
