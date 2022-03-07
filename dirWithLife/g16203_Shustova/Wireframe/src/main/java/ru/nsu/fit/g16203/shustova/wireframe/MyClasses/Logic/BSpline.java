package ru.nsu.fit.g16203.shustova.wireframe.MyClasses.Logic;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BSpline {
    private double length;
    public double zMax, zMin;
    private Point2D.Double[] points;
    private double[] controlPointsX, controlPointsY;
    private ArrayList<Integer> trimmedXs, trimmedYs;
    private Color color;

    public BSpline() {
        zMax = -1000;
        zMin = 1000;
        length = 0;
        color = Color.CYAN;
        trimmedXs = new ArrayList<>();
        trimmedYs = new ArrayList<>();
    }

    public double b(int i, double t) {
        switch (i) {
            case -1:
                return (((-t + 3) * t - 3) * t + 1) / 6;
            case 0:
                return (((3 * t - 6) * t) * t + 4) / 6;
            case 1:
                return (((-3 * t + 3) * t + 3) * t + 1) / 6;
            case 2:
                return (t * t * t) / 6;
        }
        return 0;
    }

    public Point2D.Double[] curvePoints(double[] xpoints, double[] ypoints, int numPts, int steps) {
        int pts = (numPts - 3) * steps;
        Point2D.Double[] curve = new Point2D.Double[pts];
        curve[0] = point(1, 0.0, xpoints, ypoints);
        for (int i = 1; i < numPts - 2; ++i) {
            for (int j = 0; j < steps; ++j) {
                curve[(i - 1) * steps + j] = point(i, j / (double) steps, xpoints, ypoints);
                if (curve[(i - 1) * steps + j].y > zMax)
                    zMax = curve[(i - 1) * steps + j].y;
                if (curve[(i - 1) * steps + j].y < zMin)
                    zMin = curve[(i - 1) * steps + j].y;
            }
        }
        countLength(curve);
        controlPointsX = xpoints;
        controlPointsY = ypoints;
        points = curve;
        return curve;
    }

    public Point2D.Double point(int i, double t, double[] xpoints, double[] ypoints) {
        double px = 0;
        double py = 0;
        for (int j = -1; j <= 2; ++j) {
            px += b(j, t) * xpoints[i + j];
            py += b(j, t) * ypoints[i + j];
        }
        return new Point2D.Double(px, py);
    }

    private void countLength(Point2D.Double[] points) {
        int size = points.length;
        length = 0;
        for (int i = 0; i < size - 1; ++i) {
            if (points[i] != null && points[i + 1] != null) {
                length += Point2D.distance(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y);
            }
        }
    }

    public Point2D.Double[] getPoints() {
        return points;
    }

    public double getLength() {
        return length;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double[] getControlPointsX() {
        return controlPointsX;
    }

    public void setControlPointsX(double[] controlPointsX) {
        this.controlPointsX = controlPointsX;
    }

    public void setControlPointsY(double[] controlPointsY) {
        this.controlPointsY = controlPointsY;
    }

    public double[] getControlPointsY() {
        return controlPointsY;
    }

    public void setTrimmedPoints(ArrayList<Integer> xs, ArrayList<Integer> ys) {
        trimmedXs = xs;
        trimmedYs = ys;
    }
}
