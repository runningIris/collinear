import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    private List<LineSegment> collinearLineSegments = new ArrayList<LineSegment>();

    private int lineNum = 0;

    public BruteCollinearPoints(Point[] points) {
        int pl = points.length;

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
                        Point s = points[l];
                        double ps = p.slopeTo(s);
                        if (pq != ps) {
                            break;
                        }

                        List<Point> collinearPoints = new ArrayList<Point>(4);
                        collinearPoints.add(p);
                        collinearPoints.add(q);
                        collinearPoints.add(r);
                        collinearPoints.add(s);

                        // Collections.sort(collinearPoints);
                        Point max = Collections.max(collinearPoints);
                        Point min = Collections.min(collinearPoints);

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
        int len = StdIn.readInt();
        Point[] points = new Point[len];

        int i = -1;
        int x = 0;
        int y;

        while(!StdIn.isEmpty()) {
            if (i % 2 == 0) {
                y = StdIn.readInt();
                points[i / 2] = new Point(x, y);
            } else {
                x = StdIn.readInt();
            }
            i++;
        }


        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.001);
        StdDraw.setXscale(0, 40000);
        StdDraw.setYscale(0, 40000);
        StdDraw.line(100000, 0, -10000, 0);
        StdDraw.line(0, 100000, 0, -10000);
        StdDraw.setPenColor(StdDraw.BLUE);
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

        LineSegment[] ls = bcp.segments();
        int num = bcp.numberOfSegments();

        for (int j = 0; j < num; j++) {
            ls[j].draw();
        }

        StdDraw.show();
    }
}
