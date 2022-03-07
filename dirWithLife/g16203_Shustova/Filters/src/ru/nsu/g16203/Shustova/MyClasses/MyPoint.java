package ru.nsu.g16203.Shustova.MyClasses;

public class MyPoint {
    public int x, r, g, b;
    public double val, x1, y1, z1, abs;

    public MyPoint(int x, double abs) {
        this.x = x;
        this.abs = abs;
    }

    public MyPoint(int x, int r, int g, int b) {
        this.x = x;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public MyPoint(double x, double y, double z, double q) {
        this.x1 = x;
        this.y1 = y;
        this.z1 = z;
        this.val = q;
    }

    public MyPoint(double x, double y, double z) {
        this.x1 = x;
        this.y1 = y;
        this.z1 = z;
    }
}
