package ru.nsu.fit.g16203.shustova.wireframe.MyClasses.View;

import javafx.util.Pair;
import ru.nsu.fit.g16203.shustova.wireframe.MyClasses.Logic.BSpline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BSplinePanel extends JPanel {
    private BufferedImage panel;
    public BSpline spline;
    private ArrayList<Double> xpoints, ypoints;
    private int STEPS = 12, count = 0, width = 600, height = 500;
    private double xMax = 300, xMin = -300, yMax = 250, yMin = -250;
    private double a = 0.0, b = 1.0;
    Pair<Integer, Double> aPoint = null;
    Pair<Integer, Double> bPoint = null;

    public BSplinePanel() {
        super();
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                int p = isControl(e.getX(), e.getY());
                if (p != -1) {
                    drawControlPoint(e.getX(), e.getY());
                    Point2D point = fromPtoD(e.getX(), e.getY());
                    xpoints.set(p, point.getX());
                    ypoints.set(p, point.getY());
                    redraw();
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (spline == null)
                        spline = new BSpline();
                    addControlPoint(e.getX(), e.getY());
//                    System.out.println(e.getX()+" "+e.getY());
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    int p = isControl(e.getX(), e.getY());
                    if (p != -1) {
                        xpoints.remove(p);
                        ypoints.remove(p);
                        --count;
                        redraw();
                    }
                    if (spline.getPoints().length == 0)
                        spline = null;
                }
            }
        });
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    xMax /= 1.5;
                    yMax /= 1.5;
                    xMin /= 1.5;
                    yMin /= 1.5;
//                    System.out.println(xMax+" "+xMin+" "+yMax+" "+yMin);
                    redraw();

                } else {
                    xMax *= 1.5;
                    yMax *= 1.5;
                    xMin *= 1.5;
                    yMin *= 1.5;
//                    System.out.println(xMax+" "+xMin+" "+yMax+" "+yMin);
                    redraw();
                }
            }
        });
        xpoints = new ArrayList<>();
        ypoints = new ArrayList<>();
        panel = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        drawAxis();
    }

    public void setSpline(BSpline newSpline) {
        spline = newSpline;
        spline.setColor(newSpline.getColor());
        xpoints = toDoubleArraylist(spline.getControlPointsX());
        ypoints = toDoubleArraylist(spline.getControlPointsY());
        count = xpoints.size();
        redraw();
    }

//    private void scale() {
//        for (Double d : xpoints) {
//            if (d > xMax)
//                xMax = (int) Math.round(d) + 1;
//            if (d < xMin)
//                xMin = (int) Math.round(d) - 1;
//        }
//        for (Double d : ypoints) {
//            if (d > yMax)
//                yMax = (int) Math.round(d) + 1;
//            if (d < yMin)
//                yMin = (int) Math.round(d) - 1;
//        }
//
//    }

    public void clear() {
        spline = null;
        count = 0;
        xpoints = new ArrayList<>();
        ypoints = new ArrayList<>();
        panel = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        drawAxis();
        repaint();
    }

    private void addControlPoint(int x, int y) {
        Point2D p = fromPtoD(x, y);
//        System.out.println(p.getX()+" "+p.getY());
        xpoints.add(p.getX());
        ypoints.add(p.getY());
        ++count;
        drawControlPoint((int) Math.round(p.getX()), (int) Math.round(p.getY()));
        repaint();
    }

    private void drawControlPoint(int x, int y) {
        Graphics g = panel.getGraphics();
        g.setColor(Color.MAGENTA);
        Point p = fromDtoP(x, y);
//        System.out.println(p.getX()+" "+p.getY());
        g.drawOval(p.x - 4, p.y - 4, 8, 8);
    }

    private void redraw() {
        panel = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        drawAxis();
        int size = xpoints.size();
        for (int i = 0; i < size; ++i) {
            drawControlPoint((int) Math.round(xpoints.get(i)), (int) Math.round(ypoints.get(i)));
        }
        repaint();
    }

    private int isControl(int x, int y) {
        int size = xpoints.size(), currPoint = -1;
        Point2D p = fromPtoD(x, y);
        double minDistance = 4, distance;
        for (int i = 0; i < size; ++i) {

            distance = p.distance(xpoints.get(i), ypoints.get(i));
            if (distance <= minDistance) {
                minDistance = distance;
                currPoint = i;
            }
        }
        return currPoint;
    }

    private void drawAxis() {
        Graphics g = panel.getGraphics();
        g.setColor(Color.CYAN);
        g.drawLine(panel.getWidth() / 2, 0, panel.getWidth() / 2, panel.getHeight());
        g.drawLine(0, panel.getHeight() / 2, panel.getWidth(), panel.getHeight() / 2);
        g.setFont(new Font("default", Font.BOLD, 16));
        g.drawString("Z", panel.getWidth() / 2 + 5, 16);
        g.drawString("X", panel.getWidth() - 12, panel.getHeight() / 2 - 2);
        double x = 0.0, y = 0.0;
        int x1, y1;
        while (x < xMax) {
            x1 = fromDtoP(x, 0).x;
            g.fillOval(x1 - 2, height / 2 - 2, 4, 4);
            x1 = fromDtoP(-x, 0).x;
            g.fillOval(x1 - 2, height / 2 - 2, 4, 4);
            ++x;
        }
        while (y < yMax) {
            y1 = fromDtoP(0, y).y;
            g.fillOval(width / 2 - 2, y1, 4, 4);
            y1 = fromDtoP(0, -y).y;
            g.fillOval(width / 2 - 2, y1, 4, 4);
            ++y;
        }
        repaint();
    }

    private Point fromDtoP(double x, double y) {
        int u, v;
        u = (int) Math.round(width * (x - xMin) / (xMax - xMin));
        v = (int) Math.round(height * (y - yMin) / (yMax - yMin));
        return new Point(u, v);
    }

    private Point2D fromPtoD(int u, int v) { //sizex, sizey - pixel area size
        double x, y;
        x = (xMax - xMin) * (double) u / (width) + xMin;
        y = (yMax - yMin) * (double) v / (height) + yMin;
        return new Point2D.Double(x, y);
    }

    private void calculateSpline() {
        double[] xp = toDoubleArray(xpoints);
        double[] yp = toDoubleArray(ypoints);
        spline.curvePoints(xp, yp, xp.length, STEPS);
    }

    private void drawMulticoloredSpline(Graphics2D g) {
        ArrayList<Double> xs = new ArrayList<>(), ys = new ArrayList<>();
        int border = fillArrays(xs, ys, aPoint, 0);
        g.setColor(Color.GRAY);
        for (int i = 0; i < xs.size(); ++i) {
            xs.set(i, fromDtoP(xs.get(i), 0).getX());
            ys.set(i, fromDtoP(0, ys.get(i)).getY());
        }
//        for (int i = 0; i < xs.size() - 1; ++i) {
//            g.drawLine(xs.get(i), ys.get(i), xs.get(i + 1), ys.get(i + 1));
//        }
        g.drawPolyline(toIntArray(xs), toIntArray(ys), xs.size());
        xs = new ArrayList<>();
        ys = new ArrayList<>();
        border = fillArrays(xs, ys, bPoint, border);
        g.setColor(spline.getColor());
        for (int i = 0; i < xs.size(); ++i) {
            xs.set(i, fromDtoP(xs.get(i), 0).getX());
            ys.set(i, fromDtoP(0, ys.get(i)).getY());
        }
//        for (int i = 0; i < xs.size() - 1; ++i) {
//            g.drawLine(xs.get(i), ys.get(i), xs.get(i + 1), ys.get(i + 1));
//        }
        g.drawPolyline(toIntArray(xs), toIntArray(ys), xs.size());
//        spline.setTrimmedPoints(xs, ys);
        xs = new ArrayList<>();
        ys = new ArrayList<>();
        fillArrays(xs, ys, new Pair<>(xpoints.size() - 4, 1.0), border);
        for (int i = 0; i < xs.size(); ++i) {
            xs.set(i, fromDtoP(xs.get(i), 0).getX());
            ys.set(i, fromDtoP(0, ys.get(i)).getY());
        }
        g.setColor(Color.GRAY);
//        for (int i = 0; i < xs.size() - 1; ++i) {
//            g.drawLine(xs.get(i), ys.get(i), xs.get(i + 1), ys.get(i + 1));
//        }
        g.drawPolyline(toIntArray(xs), toIntArray(ys), xs.size());
    }

    private int fillArrays(ArrayList<Double> xs, ArrayList<Double> ys, Pair<Integer, Double> point, int start) {
        Point2D.Double[] curr = spline.getPoints();
        double x, y;
        int i = start, k = 0;
        while (i < (point.getKey() + point.getValue()) * STEPS) {
            if (curr[i] != null) {
                x = curr[i].x;
                y = curr[i].y;
                xs.add(x);
                ys.add(y);
                ++k;
            }
            ++i;
        }
        if (i < (xpoints.size() - 3) * STEPS - 1)
            return i - 1;
        else
            return i;
    }

    private double[] toDoubleArray(ArrayList<Double> ary) {
        double[] res = new double[ary.size()];
        int size = ary.size();
        for (int i = 0; i < size; i++) {
            res[i] = ary.get(i);
        }
        return res;
    }

    private int[] toIntArray(ArrayList<Double> ary) {
        int[] res = new int[ary.size()];
        int size = ary.size();
        for (int i = 0; i < size; i++) {
            res[i] = (int)Math.round(ary.get(i));
        }
        return res;
    }

    private ArrayList<Double> toDoubleArraylist(double[] ary) {
        ArrayList<Double> res = new ArrayList<>();
        for (int i = 0; i < ary.length; ++i)
            res.add(ary[i]);
        return res;
    }

    public BSpline getSpline() {
        return spline;
    }

    public void setAB(double na, double nb) {
        a = na;
        b = nb;
        moveBorders();
        repaint();
    }

    public void moveBorders() {
        aPoint = f(a);
        bPoint = f(b);
    }

    private Pair<Integer, Double> f(double u) {
        Point2D.Double[] p = spline.getPoints();
        int size = p.length;
        double length = 0;
        int i, k = 0;
        for (i = 0; i < size - 1; ++i) {
            if (p[i] != null && p[i + 1] != null) {
                length += Point2D.distance(p[i].x, p[i].y, p[i + 1].x, p[i + 1].y);
                k = (i + 2) / STEPS;
            }
            if (length >= u * spline.getLength()) {
                break;
            }
        }
        return (Pair<Integer, Double>) new Pair(k, (double) (i + 1 - (k) * STEPS) / STEPS);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (count >= 4) {
            calculateSpline();
            moveBorders();
            drawMulticoloredSpline((Graphics2D) g);
        }
        g.drawImage(panel, 0, 0, this);
    }
}
