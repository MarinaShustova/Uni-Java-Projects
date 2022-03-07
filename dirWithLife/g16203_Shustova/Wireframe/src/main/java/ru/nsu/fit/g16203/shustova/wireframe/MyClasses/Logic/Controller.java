package ru.nsu.fit.g16203.shustova.wireframe.MyClasses.Logic;

import ru.nsu.fit.g16203.shustova.wireframe.MyClasses.View.MyInitView;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Controller {
    private double a = 0.0, b = 1.0, c = 0.0, d = 2 * Math.PI;
    private int n = 10, m = 10, k = 5, STEPS = 12;
    public ArrayList<BSpline> splines;
    public ArrayList<Figure> figures;
    public HashMap<BSpline, Figure> bodies;
    private MyInitView initView;
    private Scene scene;
    private Camera camera;

    public Controller(MyInitView view) {
        initView = view;
        splines = new ArrayList<>();
        BSpline s = DefaultSpline();
        figures = new ArrayList<>();
        bodies = new HashMap<>();
        camera = new Camera();
        scene = new Scene(camera);
        addSpline(s);
    }

    public BSpline DefaultSpline(){
        BSpline spline = new BSpline();
        double[] xp = {165.0, 167.0, 163.0, 172.0, 166.0};
        double[] yp = {-124.0, -76.0, -4.0, 46.0, 122.0};
        spline.setControlPointsX(xp);
        spline.setControlPointsY(yp);
        spline.curvePoints(xp, yp, xp.length, STEPS);
        spline.setColor(Color.RED);
        return spline;
    }

    public void addSpline(BSpline spline) {
        splines.add(spline);
        Figure fig = new Figure(spline);
        figures.add(fig);
        bodies.put(spline, fig);
        for (Figure f : figures) {
            if (!f.isAxis)
                f.setABCD(a, b, c, d);
        }
        scene.setFigures(figures);
        initView.setBox(scene);
        initView.createScene();
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
            bw.write(n + " " + m + " " + k + " " + a + " " + b + " " + c + " " + d);
            bw.newLine();
            bw.write(camera.zn + " " + camera.zf + " " + camera.sw + " " + camera.sh);
            bw.newLine();
            Matrix m = scene.rotationMatrix;
            for (int i = 0; i < 3; ++i) {
                bw.write(m.body[i][0] + " " + m.body[i][1] + " " + m.body[i][2]);
                bw.newLine();
            }
            bw.write(initView.background.getRed() + " " + initView.background.getGreen() + " " + initView.background.getBlue());
            bw.newLine();
            bw.write(String.valueOf(figures.size()));
            bw.newLine();
            for (Figure figure : figures) {
                bw.write(figure.color.getRed()+" "+figure.color.getGreen()+" "+figure.color.getBlue());
                bw.newLine();
                bw.write(figure.Cx+" "+figure.Cy+" "+figure.Cz);
                bw.newLine();
                Matrix rot = figure.positionMatrix;
                for (int i = 0; i < 3; ++i) {
                    bw.write(rot.body[i][0] + " " + rot.body[i][1] + " " + rot.body[i][2]);
                    bw.newLine();
                }
                bw.write(String.valueOf(figure.getSpline().getControlPointsX().length));
                bw.newLine();
                int size = figure.getSpline().getControlPointsX().length;
                for (int i = 0; i < size; ++i){
                    bw.write(figure.getSpline().getControlPointsY()[i] + " "+figure.getSpline().getControlPointsX()[i]);
                    bw.newLine();
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readConfigFile(File f) {
        try {
            splines = new ArrayList<>();
            figures = new ArrayList<>();
            Scanner scanner = new Scanner(f);
            String[] retval;
            String s;
            Matrix rotateMatrix = new Matrix(4, 4);
            Color color;
            s = rewind(scanner);
            s = deleteComments(s);
            retval = s.split(" ");
            n = Integer.parseInt(retval[0]);
            m = Integer.parseInt(retval[1]);
            k = Integer.parseInt(retval[2]);
            a = Double.parseDouble(retval[3]);
            b = Double.parseDouble(retval[4]);
            c = Double.parseDouble(retval[5]);
            d = Double.parseDouble(retval[6]);
            s = rewind(scanner);
            s = deleteComments(s);
            retval = s.split(" ");
            scene.camera.zn = Double.parseDouble(retval[0]);
            scene.camera.zf = Double.parseDouble(retval[1]);
            scene.camera.sw = Double.parseDouble(retval[2]);
            scene.camera.sh = Double.parseDouble(retval[3]);
            for (int i = 0; i < 3; ++i) {
                s = rewind(scanner);
                s = deleteComments(s);
                retval = s.split(" ");
                for (int j = 0; j < 3; ++j) {
                    rotateMatrix.body[i][j] = Double.parseDouble(retval[j]);
                }
            }
            rotateMatrix.body[3][3] = 1;
            scene.rotationMatrix = rotateMatrix;
            s = rewind(scanner);
            s = deleteComments(s);
            retval = s.split(" ");
            color = new Color(Integer.parseInt(retval[0]), Integer.parseInt(retval[1]),
                    Integer.parseInt(retval[2]));
            initView.Background(color);
            s = rewind(scanner);
            s = deleteComments(s);
            retval = s.split(" ");
            int amount = Integer.parseInt(retval[0]);
            for (int i = 0; i < amount; ++i) {
                s = rewind(scanner);
                s = deleteComments(s);
                retval = s.split(" ");
                color = new Color(Integer.parseInt(retval[0]), Integer.parseInt(retval[1]),
                        Integer.parseInt(retval[2]));
                s = rewind(scanner);
                s = deleteComments(s);
                retval = s.split(" ");
                double cx, cy, cz;
                cx = Double.parseDouble(retval[0]);
                cy = Double.parseDouble(retval[1]);
                cz = Double.parseDouble(retval[2]);
                Matrix rot = new Matrix(4, 4);
                for (int k = 0; k < 3; ++k) {
                    s = rewind(scanner);
                    s = deleteComments(s);
                    retval = s.split(" ");
                    for (int j = 0; j < 3; ++j) {
                        rot.body[k][j] = Double.parseDouble(retval[j]);
                    }
                }
                rot.body[3][3] = 1;
                s = rewind(scanner);
                s = deleteComments(s);
                retval = s.split(" ");
                int amount2 = Integer.parseInt(retval[0]);
                BSpline spline = new BSpline();
                spline.setColor(color);
                double[] xp = new double[amount2];
                double[] yp = new double[amount2];
                for (int k = 0; k < amount2; ++k) {
                    s = rewind(scanner);
                    s = deleteComments(s);
                    retval = s.split(" ");
                    yp[k] = Double.parseDouble(retval[0]);
                    xp[k] = Double.parseDouble(retval[1]);
                }
                spline.setControlPointsX(xp);
                spline.setControlPointsY(yp);
                spline.curvePoints(xp, yp, xp.length, STEPS);
                splines.add(spline);
                Figure figure = new Figure(spline);
                figure.Cx = cx;
                figure.Cy = cy;
                figure.Cz = cz;
                figure.positionMatrix = rot;
                figures.add(figure);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException ee) {
            ee.printStackTrace();
        }
        scene.setFigures(figures);
        for (Figure figure : figures) {
            if (!figure.isAxis) {
                figure.setABCD(a, b, c, d);
                figure.setK(k);
                figure.setM(m);
                figure.setN(n);
            }
        }
        initView.setBox(scene);
        initView.recreate(camera.sw, camera.sh);
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
