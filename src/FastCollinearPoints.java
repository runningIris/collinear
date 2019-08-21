import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class FastCollinearPoints {

    private int num;
    private List<LineSegment> segments;
    private List<String> stringSegments;

    public FastCollinearPoints(Point[] points) {

        // 参数不合法处理

        if (points == null) {
            throw new IllegalArgumentException("The constructor argument points should not be null.");
        }

        if (points.length < 1) {
            throw new IllegalArgumentException("The constructor argument points should not be an empty array.");
        }

        List<String> stringPoints = new ArrayList<String>();

        for (Point p: points) {
            if (p == null) {
                throw new IllegalArgumentException("Any entry in the array points should not be null.");
            }

            if (stringPoints.contains(p.toString())) {
                throw new IllegalArgumentException("The constructor argument points have duplicated value.");
            } else {
                stringPoints.add(p.toString());
            }
        }


        // 初始化数据

        num = 0;
        segments = new ArrayList<LineSegment>();
        stringSegments = new ArrayList<String>();

        // 遍历每个点，以其为原点，计算其他点的斜率
        for (int j = 0; j < points.length; j++) {

            // 定义原点和剩余点
            Point origin = points[j];
            Point[] restPoints = new Point[points.length - 1];

            for (int k = 0; k < points.length; k++) {
                if (j != k) {
                    int index = k < j ? k : k - 1;
                    restPoints[index] = points[k];
                }
            }

            // 根据斜率来排序
            Arrays.sort(restPoints, origin.slopeOrder());

            // 计算剩余点到原点的斜率
            double[] slopes = new double[restPoints.length];
            for (int i = 0; i < restPoints.length; i++) {
                slopes[i] = origin.slopeTo(restPoints[i]);
            }


            double currentSlope = slopes[0];

            List<Point> tmp = new ArrayList<Point>();

            for (int i = 0; i < restPoints.length; i++) {

                if (currentSlope == slopes[i]) {
                    tmp.add(restPoints[i]);

                    // 最后一个的情况
                    if (i == restPoints.length - 1 && tmp.size() > 2) {
                        tmp.add(origin);
                        addLineSegment(tmp);
                    }

                } else {

                    if (tmp.size() > 2) {
                        tmp.add(origin);
                        addLineSegment(tmp);
                    }

                    tmp = new ArrayList<Point>();
                    tmp.add(restPoints[i]);
                }

                currentSlope = slopes[i];
            }
        }

    }

    private void addLineSegment(List<Point> points) {
        Collections.sort(points);
        LineSegment ls = new LineSegment(points.get(0), points.get(points.size() - 1));

        if (!stringSegments.contains(ls.toString())) {
            segments.add(ls);
            stringSegments.add(ls.toString());
            num++;
        }
    }

    public LineSegment[] segments() {
        LineSegment[] all = new LineSegment[num];
        for (int i = 0; i < num; i++) {
            all[i] = segments.get(i);
        }
        return all;
    }

    public int numberOfSegments() {
        return num;
    }

    public static void main(String[] args) {
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