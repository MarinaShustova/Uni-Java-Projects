package ru.nsu.fit.g16203.shustova.isolines.MyClasses.View;

import javafx.util.Pair;
import ru.nsu.fit.g16203.shustova.isolines.MyClasses.Logic.Function;
import ru.nsu.fit.g16203.shustova.isolines.MyClasses.Logic.MyPoint;
import ru.nsu.fit.g16203.shustova.isolines.MyClasses.Logic.Settings;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public class Map extends JPanel {
    private BufferedImage map, net, lines, dots;
    private double a, b, c, d;
    private int nZones, k, m;
    private Function function;
    private ArrayList<Pair<Double, Color>> palette;
    private Color[] colors;
    public boolean showNet = true, interpollation = false, showLines = true, drawLines = true, showDots = false;
    private int sizeX, sizeY;
    private ArrayList<Double> isolines, levels;
    private Color isoColor;

    public Map(int n, Color[] colors, Function f, JLabel statusbar) {
        sizeX = 600;
        sizeY = 600;
        isolines = new ArrayList<>();
        levels = new ArrayList<>();
        this.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        this.colors = colors;
        map = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
        net = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
        lines = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
        dots = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
        function = f;
        nZones = n;
        setParams(new Settings());
        fillPalette();
        redraw(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (drawLines) {
//                    double f = function.getValue(fromPtoD(e.getX(), e.getY(), sizeX, sizeY).x,
//                            fromPtoD(e.getX(), e.getY(), sizeX, sizeY).y);
                    double f = getInterpVal(e.getX(), e.getY());
                    isolines.add(f);
                    drawIsoLine(f);
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (drawLines) {
//                    double f = function.getValue(fromPtoD(e.getX(), e.getY(), sizeX, sizeY).x,
//                            fromPtoD(e.getX(), e.getY(), sizeX, sizeY).y);
                    double f = getInterpVal(e.getX(), e.getY());
                    isolines.add(f);
                    drawIsoLine(f);
                    repaint();
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                MyPoint p = fromPtoD(e.getX(), e.getY(), sizeX, sizeY);
//                System.out.println("X:" + e.getX() + " Y" + e.getY());
                BigDecimal bigDecimalX = new BigDecimal(p.x);
                bigDecimalX = bigDecimalX.setScale(3, RoundingMode.HALF_UP);
                BigDecimal bigDecimalY = new BigDecimal(p.y);
                bigDecimalY = bigDecimalY.setScale(3, RoundingMode.HALF_UP);
                BigDecimal bigDecimalF = new BigDecimal(function.getValue(p.x, p.y));
                bigDecimalF = bigDecimalF.setScale(3, RoundingMode.HALF_UP);
                BigDecimal bigDecimalFI = new BigDecimal(getInterpVal(e.getX(), e.getY()));
                bigDecimalFI = bigDecimalFI.setScale(3, RoundingMode.HALF_UP);
                statusbar.setText("X:" + bigDecimalX.doubleValue() + " Y:" + bigDecimalY.doubleValue()
                        + " F:" + bigDecimalF.doubleValue() + " Fi:" + bigDecimalFI.doubleValue());
                statusbar.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (drawLines) {
                    super.mouseDragged(e);
                    recompute();
//                    double f = function.getValue(fromPtoD(e.getX(), e.getY(), sizeX, sizeY).x,
//                            fromPtoD(e.getX(), e.getY(), sizeX, sizeY).y);
                    double f = getInterpVal(e.getX(), e.getY());
                    drawIsoLine(f);
                    repaint();
                }
            }
        });
    }

    private void fillPalette() {
        palette = new ArrayList<>();
        double step = (function.getMax() - function.getMin()) / nZones;
        for (int i = 0; i < nZones; ++i) {
            palette.add(new Pair<>(function.getMin() + (i + 1) * step, colors[i]));
            isolines.add(function.getMin() + (i + 1) * step - 0.1);
            levels.add(function.getMin() + (i + 1) * step - 0.1);
        }
    }

    public void setParams(Settings s) {
        a = s.getA();
        c = s.getC();
        b = s.getB();
        d = s.getD();
        k = s.getK();
        m = s.getM();
        isoColor = s.getIsoColor();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        sizeX = width;
        sizeY = height;
        map = null;
        net = null;
        lines = null;
        dots = null;
    }

    private void recompute() {
        map = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
        net = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
        lines = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
        dots = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
        redraw(false);
    }

    public void redraw(boolean callrepaint) {
        if (interpollation)
            drawIntrpSurface();
        else
            drawSurface();
        drawNet();
        for (Double f : isolines)
            drawIsoLine(f);
        if (callrepaint)
            repaint();
    }

    public void dropLines() {
        isolines = new ArrayList<>(levels);
        lines = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
        dots = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
        redraw(true);
    }

    private void drawSurface() {
        int width = map.getWidth(), height = map.getHeight();
        double val;
        MyPoint curr;
        for (int i = 0; i < width; ++i)
            for (int j = 0; j < height; ++j) {
                curr = fromPtoD(i, j, width, height);
                val = function.getValue(curr.x, curr.y);
                for (Pair p : palette) {
                    if (val < (Double) p.getKey()) {
                        map.setRGB(i, j, ((Color) p.getValue()).getRGB());
                        break;
                    }
                }
            }
    }

    private void drawIntrpSurface() {
        int width = map.getWidth(), height = map.getHeight();
        double val;
        MyPoint curr;
        for (int i = 0; i < width; ++i)
            for (int j = 0; j < height; ++j) {
                curr = fromPtoD(i, j, width, height);
                val = function.getValue(curr.x, curr.y);
                map.setRGB(i, j, interpColor(val).getRGB());
            }
    }

    private Color interpColor(double u) {
        double step = (function.getMax() - function.getMin()) / colors.length;
        u = u - function.getMin();
        if (u < step / 2) {
            return colors[0];
        }
        if (u > (colors.length - 1) * step + step / 2) {
            return colors[colors.length - 1];
        }
        int c1 = (int) ((u - step / 2) / step) < 0 ? 0 : (int) ((u - step / 2) / step);
        int c2 = c1 + 1 > colors.length - 1 ? colors.length - 1 : c1 + 1;
        double v1 = c1 * step + step / 2;
        double v2 = c2 * step + step / 2;
        if (v1 == v2) {
            return colors[c1];
        }
        return interpolatedColor(colors[c1], colors[c2], u, v1, v2);
    }

    private Color interpolatedColor(Color c1, Color c2, double u, double v1, double v2) {
        int r = (int) (c1.getRed() * (v2 - u) / (v2 - v1) + c2.getRed() * (u - v1) / (v2 - v1));
        int g = (int) (c1.getGreen() * (v2 - u) / (v2 - v1) + c2.getGreen() * (u - v1) / (v2 - v1));
        int b = (int) (c1.getBlue() * (v2 - u) / (v2 - v1) + c2.getBlue() * (u - v1) / (v2 - v1));
        return trimColor(r, g, b);
    }

    private Color trimColor(int r, int g, int b) {
        if (r > 255) r = 255;
        if (r < 0) r = 0;
        if (g > 255) g = 255;
        if (g < 0) g = 0;
        if (b > 255) b = 255;
        if (b < 0) b = 0;
        return new Color(r, g, b);
    }

    private void drawNet() {
        int width = net.getWidth();
        int height = net.getHeight();
        double offsetX = sizeX / (double) k;
        double offsetY = sizeY / (double) m;
        Graphics g = net.getGraphics();
        g.setColor(Color.BLACK);
        for (int i = 0; i < k; ++i)
            g.drawLine((int) Math.round(i * offsetX), 0, (int) Math.round(i * offsetX), height - 1);
        for (int i = 0; i < m; ++i)
            g.drawLine(0, (int) Math.round(i * offsetY), width - 1, (int) Math.round(i * offsetY));
    }

    private MyPoint fromDtoP(double x, double y, int sizex, int sizey) {
        int u, v;
        u = (int) Math.round(sizex * (x - a) / (b - a) + 0.5);
        v = (int) Math.round(sizey * (y - c) / (d - c) + 0.5);
        return new MyPoint(u, v);
    }

    private MyPoint fromPtoD(int u, int v, int sizex, int sizey) { //sizex, sizey - pixel area size
        double x, y;
        x = (b - a) * (double) u / (sizex) + a;
        y = (d - c) * (double) v / (sizey) + c;
        return new MyPoint(x, y);
    }

    private void drawIsoLine(double level) {
        double offsetX = map.getWidth() / (double) k;
        double offsetY = map.getHeight() / (double) m;
        MyPoint lu, ru, ld, rd;
        for (int i = 0; i < k; ++i) {
            for (int j = 0; j < m; ++j) {
                lu = new MyPoint((int) Math.round(i * offsetX), (int) Math.round(j * offsetY));
                ru = new MyPoint((int) Math.round((i + 1) * offsetX), (int) Math.round(j * offsetY));
                ld = new MyPoint((int) Math.round(i * offsetX), (int) Math.round((j + 1) * offsetY));
                rd = new MyPoint((int) Math.round((i + 1) * offsetX), (int) Math.round((j + 1) * offsetY));
                drawLineinArea(lu, ru, ld, rd, level);
            }
        }
        repaint();
    }

    private void drawLineinArea(MyPoint lu, MyPoint ru, MyPoint ld, MyPoint rd, double val) { //pixels coordinates
        MyPoint luD, current = luD = fromPtoD(lu.u, lu.v, sizeX, sizeY), ruD, ldD, rdD;
        double f1 = function.getValue(current.x, current.y);
        current = ruD = fromPtoD(ru.u, ru.v, sizeX, sizeY);
        double f2 = function.getValue(current.x, current.y);
        current = ldD = fromPtoD(ld.u, ld.v, sizeX, sizeY);
        double f3 = function.getValue(current.x, current.y);
        current = rdD = fromPtoD(rd.u, rd.v, sizeX, sizeY);
        double f4 = function.getValue(current.x, current.y);
        boolean isCrossUp = (f1 - val) * (f2 - val) <= 0 ? true : false,
                isCrossDown = (f3 - val) * (f4 - val) <= 0 ? true : false,
                isCrossLeft = (f1 - val) * (f3 - val) <= 0 ? true : false,
                isCrossRight = (f2 - val) * (f4 - val) <= 0 ? true : false;
        int crossAmount = 0;
        crossAmount = (isCrossUp ? 1 : 0) + (isCrossRight ? 1 : 0) + (isCrossDown ? 1 : 0) + (isCrossLeft ? 1 : 0);
        if (crossAmount == 2) {
            cross2(isCrossUp, isCrossDown, isCrossLeft, isCrossRight, false, luD, ruD, ldD, rdD, val, f1, f2, f3, f4);
        }
        if (crossAmount == 3) {
            double eps = 0.001, newVal = val;
            while (crossAmount == 3) {
                newVal += eps;
                isCrossUp = (f1 - newVal) * (f2 - newVal) <= 0 ? true : false;
                isCrossDown = (f3 - newVal) * (f4 - newVal) <= 0 ? true : false;
                isCrossLeft = (f1 - newVal) * (f3 - newVal) <= 0 ? true : false;
                isCrossRight = (f2 - newVal) * (f4 - newVal) <= 0 ? true : false;
                crossAmount = (isCrossUp ? 1 : 0) + (isCrossRight ? 1 : 0) + (isCrossDown ? 1 : 0) + (isCrossLeft ? 1 : 0);
            }
            if (crossAmount == 2) {
                cross2(isCrossUp, isCrossDown, isCrossLeft, isCrossRight, false, luD, ruD, ldD, rdD, val, f1, f2, f3, f4);
            }
            if (crossAmount == 4) {
                cross4(luD, ruD, ldD, rdD, val, f1, f2, f3, f4);
            }
        }
        if (crossAmount == 4) {
            cross4(luD, ruD, ldD, rdD, val, f1, f2, f3, f4);
        }
    }

    private void cross4(MyPoint lu, MyPoint ru, MyPoint ld, MyPoint rd, double val, double f1, double f2, double f3, double f4) { //D
        double x5 = (lu.x + ru.x) / 2, y5 = (lu.y + ld.y) / 2, f5 = (f1 + f2 + f3 + f4) / 4;
        boolean isCrossNW = (f1 - val) * (f5 - val) <= 0 ? true : false,
                isCrossNE = (f2 - val) * (f5 - val) <= 0 ? true : false,
                isCrossSW = (f3 - val) * (f5 - val) <= 0 ? true : false,
                isCrossSE = (f4 - val) * (f5 - val) <= 0 ? true : false;
        int crossAmount = 0;
        crossAmount = (isCrossNW ? 1 : 0) + (isCrossNE ? 1 : 0) + (isCrossSW ? 1 : 0) + (isCrossSE ? 1 : 0);
        if (crossAmount == 3 || crossAmount == 4) {
            System.out.println("Well, it CAN be!");
        } else {
            if (isCrossNW)
                cross2(true, false, true, false, true, lu, ru, ld, rd, val, f1, f2, f3, f4);
            if (isCrossNE)
                cross2(true, false, false, true, true, lu, ru, ld, rd, val, f1, f2, f3, f4);
            if (isCrossSW)
                cross2(false, true, true, false, true, lu, ru, ld, rd, val, f1, f2, f3, f4);
            if (isCrossSE)
                cross2(false, true, false, true, true, lu, ru, ld, rd, val, f1, f2, f3, f4);
        }
    }

    private void cross2(boolean isCrossUp, boolean isCrossDown, boolean isCrossLeft, boolean isCrossRight, boolean withDiagonal,      //D coordinates
                        MyPoint lu, MyPoint ru, MyPoint ld, MyPoint rd, double val, double f1, double f2, double f3, double f4) {
        double xu, xd, yl, yr;
        if (isCrossUp && isCrossDown) {
            xu = findCrossCoord(lu.x, ru.x, f1, f2, val);
            xd = findCrossCoord(ld.x, rd.x, f3, f4, val);
            MyPoint p1 = fromDtoP(xu, lu.y, sizeX, sizeY),
                    p2 = fromDtoP(xd, ld.y, sizeX, sizeY);
            drawLine(p1.u, p1.v, p2.u, p2.v);
        }
        if (isCrossUp && isCrossLeft) {
            xu = findCrossCoord(lu.x, ru.x, f1, f2, val);
            yl = findCrossCoord(lu.y, ld.y, f1, f3, val);
            MyPoint p1 = fromDtoP(xu, lu.y, sizeX, sizeY),
                    p2 = fromDtoP(lu.x, yl, sizeX, sizeY);
            drawLine(p1.u, p1.v, p2.u, p2.v);
        }
        if (isCrossUp && isCrossRight) {
            xu = findCrossCoord(lu.x, ru.x, f1, f2, val);
            yr = findCrossCoord(ru.y, rd.y, f2, f4, val);
            MyPoint p1 = fromDtoP(xu, lu.y, sizeX, sizeY),
                    p2 = fromDtoP(ru.x, yr, sizeX, sizeY);

            drawLine(p1.u, p1.v, p2.u, p2.v);
        }
        if (isCrossDown && isCrossLeft) {
            xd = findCrossCoord(ld.x, rd.x, f3, f4, val);
            yl = findCrossCoord(lu.y, ld.y, f1, f3, val);
            MyPoint p1 = fromDtoP(xd, ld.y, sizeX, sizeY),
                    p2 = fromDtoP(lu.x, yl, sizeX, sizeY);

            drawLine(p1.u, p1.v, p2.u, p2.v);
        }
        if (isCrossDown && isCrossRight) {
            xd = findCrossCoord(ld.x, rd.x, f3, f4, val);
            yr = findCrossCoord(ru.y, rd.y, f2, f4, val);
            MyPoint p1 = fromDtoP(xd, ld.y, sizeX, sizeY),
                    p2 = fromDtoP(ru.x, yr, sizeX, sizeY);

            drawLine(p1.u, p1.v, p2.u, p2.v);
        }
        if (isCrossLeft && isCrossRight) {
            yl = findCrossCoord(lu.y, ld.y, f1, f3, val);
            yr = findCrossCoord(ru.y, rd.y, f2, f4, val);
            MyPoint p1 = fromDtoP(lu.x, yl, sizeX, sizeY),
                    p2 = fromDtoP(ru.x, yr, sizeX, sizeY);
            drawLine(p1.u, p1.v, p2.u, p2.v);
        }
    }

    private double getInterpVal(int u, int v) { //Pixel coords
        MyPoint pointD = fromPtoD(u, v, sizeX, sizeY);
        int x1, x2, y1, y2;
        double offset = sizeX / (double) k;
        x1 = (int) Math.round(offset * (int)(u / offset));
        x2 = (int) Math.round(x1 + offset);
        offset = sizeY / (double) m;
        y1 = (int) Math.round(offset * (int)(v / offset));
        y2 = (int) Math.round(y1 + offset);
        MyPoint temp = fromPtoD(x1, y1, sizeX, sizeY);
        MyPoint temp2 = fromPtoD(x2, y2, sizeX, sizeY);
        double f1 = function.getValue(temp.x, temp.y);
        double f2 = function.getValue(temp2.x, temp.y);
        double f3 = function.getValue(temp.x, temp2.y);
        double f4 = function.getValue(temp2.x, temp2.y);
        return valInterp(temp.x, temp2.x, temp2.y, temp.y, f1, f2, f3, f4, pointD.x, pointD.y);
    }

    private double valInterp(double x1, double x2, double y1, double y2, double f1, double f2, double f3, double f4,
                             double x, double y) {
        double s1 = (x2 - x) * (y2 - y) * f3 / ((x2 - x1) * (y2 - y1));
        double s2 = (x - x1) * (y2 - y) * f4 / ((x2 - x1) * (y2 - y1));
        double s3 = (x2 - x) * (y - y1) * f1 / ((x2 - x1) * (y2 - y1));
        double s4 = (x - x1) * (y - y1) * f2 / ((x2 - x1) * (y2 - y1));
        return s1 + s2 + s3 + s4;
    }

    private double findCrossCoord(double v1, double v2, double f1, double f2, double val) {
        double x, dx = Math.abs(v1 - v2);
        if (f1 < f2) {
            x = dx * (val - f1) / (f2 - f1);
            return v1 + x;
        } else {
            x = dx * (val - f2) / (f1 - f2);
            return v2 - x;
        }
    }

    private void drawLine(int x1, int y1, int x2, int y2) {
        Graphics g = lines.getGraphics();
        if (x1 >= sizeX) x1 = sizeX - 1;
        if (x2 >= sizeX) x2 = sizeX - 1;
        if (y1 >= sizeY) y1 = sizeY - 1;
        if (y2 >= sizeY) y2 = sizeY - 1;
        g.setColor(isoColor);
        g.drawLine(x1, y1, x2, y2);
        if (showDots) {
            Graphics g2 = dots.getGraphics();
            g2.setColor(Color.PINK);
            g2.fillOval((int) (x1 - 2.5), (int) (y1 - 2.5), 5, 5);
            g2.fillOval((int) (x2 - 2.5), (int) (y2 - 2.5), 5, 5);
        }
    }

    @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (map == null || net == null || lines == null) {
            recompute();
        }
        g.drawImage(map, 0, 0, this);
        if (showNet)
            g.drawImage(net, 0, 0, this);
        if (showLines)
            g.drawImage(lines, 0, 0, this);
        if (showDots)
            g.drawImage(dots, 0, 0, this);
    }
}
