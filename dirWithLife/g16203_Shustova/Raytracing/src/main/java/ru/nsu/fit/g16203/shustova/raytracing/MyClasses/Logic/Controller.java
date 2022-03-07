package ru.nsu.fit.g16203.shustova.raytracing.MyClasses.Logic;

import ru.nsu.fit.g16203.shustova.raytracing.MyClasses.View.MyInitView;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
    private double a = 0.0, b = 1.0, c = 0.0, d = 2 * Math.PI;
    private int n = 10, m = 10, k = 5, STEPS = 12;
    public ArrayList<Figure> figures;
    private MyInitView initView;
    private Scene scene;
    private Camera camera;

    public Controller(MyInitView view) {
        initView = view;
        figures = new ArrayList<>();
        camera = new Camera();
        scene = new Scene(camera);
    }


    public void reset(){
        scene.rotationMatrix.setTypeAndFill(Matrix.Type.E,0,0,0,0);
        scene.recreate();
        initView.createScene();
    }

    public void writeConfigFile(File f) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readConfigFile(File f) {
    }

    private String deleteComments(String source) {
        if (!source.contains("//"))
            return source;
        int start = source.indexOf("//");
        return source.substring(0, start).trim();
    }

    private String rewind(Scanner scanner) {
        String s = scanner.nextLine();
        while (s.startsWith("//") || s.isEmpty())
            s = scanner.nextLine();
        return s;
    }


    public void setA(double a) {
        this.a = a;
        for (Figure f : figures) {
            if (!f.isAxis)
                f.setABCD(a, b, c, d);
        }
    }

    public void setB(double b) {
        this.b = b;
        for (Figure f : figures) {
            if (!f.isAxis)
                f.setABCD(a, b, c, d);
        }
    }

    public void setC(double c) {
        this.c = c;
        for (Figure f : figures) {
            if (!f.isAxis)
                f.setABCD(a, b, c, d);
        }
    }

    public void setD(double d) {
        this.d = d;
        for (Figure f : figures) {
            if (!f.isAxis)
                f.setABCD(a, b, c, d);
        }
    }

    public void setM(int m) {
        this.m = m;
        for (Figure f : figures) {
            if (!f.isAxis) {
                f.setM(m);
            }
        }
    }

    public void setK(int k) {
        this.k = k;
        for (Figure f : figures) {
            if (!f.isAxis) {
                f.setK(k);
            }
        }
    }

    public void setN(int n) {
        this.n = n;
        for (Figure f : figures) {
            if (!f.isAxis) {
                f.setN(n);
            }
        }
    }

    public void setSw(double sw) {
        camera.sw = sw;
        initView.recreate(camera.sw, camera.sh);
    }

    public void setSh(double sh) {
        camera.sh = sh;
        initView.recreate(camera.sw, camera.sh);
    }

    public void setZn(double zn) {
        camera.zn = zn;
    }

    public void setZf(double zf) {
        camera.zf = zf;
    }

    public double getSw() {
        return camera.sw;
    }

    public double getSh() {
        return camera.sh;
    }

    public double getZn() {
        return camera.zn;
    }

    public double getZf() {
        return camera.zf;
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

    public int getN() {
        return n;
    }

    public Scene getScene() {
        return scene;
    }

    public MyInitView getInitView() {
        return initView;
    }
}
