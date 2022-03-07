package ru.nsu.fit.g16203.shustova.wireframe.MyClasses.Logic;

import javafx.util.Pair;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Figure {
    private BSpline spline;
    public Color color;
    private int n = 10, m = 10, STEPS = 12, k = 5;
    private double a = 0.0, b = 1.0, c = 0.0, d = 2 * Math.PI;
    private ArrayList<Pair<Double, Double>> netPoints;
    public ArrayList<Pair<Point3, Point3>> baseEdges, newEdges;
    public int rx, ry, rz;
    public double Cx, Cy, Cz;
    public boolean isAxis;
    public Matrix positionMatrix;


    public Figure(BSpline spline) {
        this.spline = spline;
        netPoints = new ArrayList<>();
        baseEdges = new ArrayList<>();
        newEdges = new ArrayList<>();
        Cx = 0;
        Cy = 0;
        Cz = -(int)(spline.zMax+spline.zMin)/2;
        color = spline.getColor();
        isAxis = false;
        positionMatrix = new Matrix(4,4);
        positionMatrix.setTypeAndFill(Matrix.Type.E, 0,0,0,0);
    }

    public Figure(){
        netPoints = new ArrayList<>();
        baseEdges = new ArrayList<>();
        newEdges = new ArrayList<>();
        Cx = 0;
        Cy = 0;
        Cz = 0;
        isAxis = true;
        positionMatrix = new Matrix(4,4);
        positionMatrix.setTypeAndFill(Matrix.Type.E, 0,0,0,0);
    }

    public void setSpline(BSpline s){
        spline = s;
        setABCD(a,b,c,d);
        color = spline.getColor();
    }

    public void setABCD(double na, double nb, double nc, double nd) {
        netPoints = new ArrayList<>();
        Point2D.Double[] curr = spline.getPoints();
        a = na;
        b = nb;
        c = nc;
        d = nd;
        Pair<Integer, Double> p;
        Pair<Double, Double> t;
        double x, y;
        for (int i = 0; i < n*k; ++i) {
            p = f(a * (1 - (double) i / (n*k)) + b * (double) i / (n*k));
            x = curr[(int) ((p.getKey() + p.getValue()) * STEPS)].x;
            y = curr[(int) ((p.getKey() + p.getValue()) * STEPS)].y;
            t = new Pair<>(x, y);
            netPoints.add(t);
        }
        calculateEdges();
    }

    private void calculateEdges() {
        baseEdges = new ArrayList<>();
        newEdges = new ArrayList<>();
        for (int i = 1; i < n*k; ++i) {
            for (int j = 0; j <= m; ++j) {
                baseEdges.add(new Pair<>(rotate(netPoints.get(i - 1), c + j * (d-c) / m), rotate(netPoints.get(i), c + j * (d-c) / m)));
                newEdges.add(new Pair<>(rotate(netPoints.get(i - 1), c + j * (d-c) / m), rotate(netPoints.get(i), c + j * (d-c) / m)));
            }
        }
        for (int i = 0; i <= n; ++i) {
            for (int j = 1; j <= m*k; ++j) {
                if (i == n) {
                    baseEdges.add(new Pair<>(rotate(netPoints.get(i * k - 1), c + (j - 1) * (d-c) / (m * k)),
                            rotate(netPoints.get(i * k - 1), c + j * (d-c) / (m * k))));
                    newEdges.add(new Pair<>(rotate(netPoints.get(i * k - 1), c + (j - 1) * (d-c) / (m * k)),
                            rotate(netPoints.get(i * k - 1), c + j * (d-c) / (m * k))));
                }
                else {
                    baseEdges.add(new Pair<>(rotate(netPoints.get(i * k), c + (j - 1) * (d-c) / (m * k)),
                            rotate(netPoints.get(i * k), c + j * (d-c) / (m * k))));
                    newEdges.add(new Pair<>(rotate(netPoints.get(i * k), c + (j - 1) * (d-c) / (m * k)),
                            rotate(netPoints.get(i * k), c + j * (d-c) / (m * k))));
                }
            }
        }
        transform(positionMatrix);
    }

    private Point3 rotate(Pair<Double, Double> point, double phi) {
        Point3 res = new Point3();
        res.x = point.getKey() * Math.cos(phi);
        res.y = point.getKey() * Math.sin(phi);
        res.z = point.getValue();
        return res;
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

    public void clip(double zn, double zf){
        Point3 first, second;
        ArrayList<Pair<Point3, Point3>> toDelete = new ArrayList<>();
        for (Pair<Point3, Point3> pair : newEdges) {
            first = pair.getKey();
            second = pair.getValue();
//            System.out.println(first.z+" "+second.z);
            if (first.z <= zn && second.z <= zn){
            }
            else if (first.z > zn && second.z > zn){
                toDelete.add(pair);
            }
            else if (first.z <= zn && second.z > zn){
                toDelete.add(pair);
            }
            else if (first.z > zn && second.z <= zn){
                toDelete.add(pair);
            }
            if (first.z >= zf && second.z >= zf){
            }
            else if (first.z < zf && second.z < zf){
                toDelete.add(pair);
            }
            else if (first.z >= zf && second.z < zf){
                toDelete.add(pair);
            }
            else if (first.z < zf && second.z >= zf){
                toDelete.add(pair);
            }
        }
        for (Pair<Point3, Point3> p : toDelete){
            newEdges.remove(p);
        }
    }

    public void transform(Matrix matrix) {
        Matrix pointVector = new Matrix(4, 1);
        Matrix res;
        Point3 first, second;
        for (Pair<Point3, Point3> pair : newEdges) {
            first = pair.getKey();
            second = pair.getValue();
            pointVector.setTypeAndFill(Matrix.Type.VECTOR, first.x, first.y, first.z,0.0);
            res = matrix.mult(pointVector);
            first.x = res.body[0][0]/res.body[3][0];
            first.y = res.body[1][0]/res.body[3][0];
            first.z = res.body[2][0]/res.body[3][0];
            pointVector.setTypeAndFill(Matrix.Type.VECTOR, second.x, second.y, second.z,0.0);
            res = matrix.mult(pointVector);
            second.x = res.body[0][0]/res.body[3][0];
            second.y = res.body[1][0]/res.body[3][0];
            second.z = res.body[2][0]/res.body[3][0];
        }
        int a = 0;
    }

    public void reset() {
        newEdges = new ArrayList<>();
        Point3 first, second;
        for (Pair<Point3, Point3> pair : baseEdges) {
            first = pair.getKey();
            second = pair.getValue();
            Point3 temp1 = new Point3(), temp2 = new Point3();
            temp1.x = first.x;
            temp1.y = first.y;
            temp1.z = first.z;
            temp2.x = second.x;
            temp2.y = second.y;
            temp2.z = second.z;
            newEdges.add(new Pair<>(temp1, temp2));
        }
        transform(positionMatrix);
    }

    public Figure axeX(){
        Figure x;
        x = new Figure();
        x.baseEdges.add(new Pair<>(new Point3(Cx, Cy, Cz), new Point3(Cx+60, Cy, Cz)));
        x.newEdges.add(new Pair<>(new Point3(Cx, Cy, Cz), new Point3(Cx+60, Cy, Cz)));
        x.color = Color.RED;
        return x;
    }

    public Figure axeY(){
        Figure y;
        y = new Figure();
        y.baseEdges.add(new Pair<>(new Point3(Cx, Cy, Cz), new Point3(Cx, Cy+60, Cz)));
        y.newEdges.add(new Pair<>(new Point3(Cx, Cy, Cz), new Point3(Cx, Cy+60, Cz)));
        y.color = Color.GREEN;
        return y;
    }

    public Figure axeZ(){
        Figure z;
        z = new Figure();
        z.baseEdges.add(new Pair<>(new Point3(Cx, Cy, Cz), new Point3(Cx, Cy, Cz+60)));
        z.newEdges.add(new Pair<>(new Point3(Cx, Cy, Cz), new Point3(Cx, Cy, Cz+60)));
        z.color = Color.BLUE;
        return z;
    }

    public void setK(int k) {
        this.k = k;
        setABCD(a,b,c,d);
    }

    public void setM(int m) {
        this.m = m;
        setABCD(a,b,c,d);
    }

    public void setN(int n) {
        this.n = n;
        setABCD(a,b,c,d);
    }

    public BSpline getSpline() {
        return spline;
    }
}
