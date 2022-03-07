package ru.nsu.fit.g16203.shustova.isolines.MyClasses.Logic;

import java.awt.*;

public class Settings {
    private double a, b, c, d;
    private int m, k;
    private Color isoColor;

    public Settings(){
        a = -Math.PI*2;
        b = Math.PI*2;
        c = -Math.PI*2;
        d = Math.PI*2;
        k = 20;
        m = 20;
        isoColor = Color.BLACK;
    }

    public Settings(double a, double b, double c, double d, int m, int k, Color isoColor) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.m = m;
        this.k = k;
        this.isoColor = isoColor;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setC(double c) {
        this.c = c;
    }

    public void setD(double d) {
        this.d = d;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setK(int k) {
        this.k = k;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    public int getK() {
        return k;
    }

    public int getM() {
        return m;
    }

    public Color getIsoColor() {
        return isoColor;
    }

    public void setIsoColor(Color isoColor) {
        this.isoColor = isoColor;
    }
}
