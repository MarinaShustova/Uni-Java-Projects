package ru.nsu.g16203.Shustova.MyClasses.Filters;

import ru.nsu.g16203.Shustova.MyClasses.MyPoint;

import java.awt.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Volume implements Filter {
    private int Nx = 15, Ny = 15, Nz = 15, nAbs, nEmis, nQ, width, height;
    private ArrayList<MyPoint> absGraph = new ArrayList<>();
    private ArrayList<MyPoint> emisGraph = new ArrayList<>();
    private ArrayList<MyPoint> Qs = new ArrayList<>();
    private Double maxImp = null, minImp = null;
    private double[] abs = new double[101];
    private Color[] emis = new Color[101];
    private boolean isAbs = true, isEmis = true;

    @Override
    public void setFilter(BufferedImage img) {
        width = img.getWidth();
        height = img.getHeight();
        int r, g, b;
        int[] newPixels = new int[width * height];
        Color currentColor;
        findBounds();
        for (int i = 0; i < width; ++i)
            for (int j = 0; j < height; ++j) {
                currentColor = new Color(img.getRGB(i, j));
                r = currentColor.getRed();
                g = currentColor.getGreen();
                b = currentColor.getBlue();
                newPixels[i * width + j] = (getNewColor(r, g, b, i, j)).getRGB();
            }
        for (int x = 0; x < width; ++x)
            for (int y = 0; y < height; ++y) {
                img.setRGB(x, y, newPixels[x * width + y]);
            }
    }

    private void prepare() {
        calcAbs();
        calcEmis();
    }

    public void setParams(int x, int y, int z, boolean a, boolean e) {
        Nx = x;
        Ny = y;
        Nz = z;
        isAbs = a;
        isEmis = e;
    }

    public void readConfigFile(File f) {
        try {
            Scanner scanner = new Scanner(f);
            String[] retval;
            String s;
            s = rewind(scanner);
            s = deleteComments(s);
            nAbs = Integer.parseInt(s);
            for (int i = 0; i < nAbs; ++i) {
                s = rewind(scanner);
                s = deleteComments(s);
                retval = s.split(" ");
                absGraph.add(new MyPoint(Integer.parseInt(retval[0]), Double.parseDouble(retval[1])));
            }
            s = rewind(scanner);
            s = deleteComments(s);
            nEmis = Integer.parseInt(s);
            for (int i = 0; i < nEmis; ++i) {
                s = rewind(scanner);
                s = deleteComments(s);
                retval = s.split(" ");
                emisGraph.add(new MyPoint(Integer.parseInt(retval[0]), Integer.parseInt(retval[1]),
                        Integer.parseInt(retval[2]), Integer.parseInt(retval[3])));
            }
            s = rewind(scanner);
            s = deleteComments(s);
            nQ = Integer.parseInt(s);
            for (int i = 0; i < nQ; ++i) {
                s = rewind(scanner);
                s = deleteComments(s);
                retval = s.split(" ");
                Qs.add(new MyPoint(Double.parseDouble(retval[0]), Double.parseDouble(retval[1]),
                        Double.parseDouble(retval[2]), Double.parseDouble(retval[3])));
            }
            prepare();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    private double calcImpact(double x, double y, double z) {
        double imp = 0.0;
        for (MyPoint q : Qs) {
            double dist = Math.sqrt(Math.pow((x - q.x1), 2) + Math.pow((y - q.y1), 2) + Math.pow((z - q.z1), 2));
            if (dist < 0.1)
                dist = 0.1;
            imp += q.val / dist;
        }
        if (maxImp == null || imp > maxImp)
            maxImp = imp;
        if (minImp == null || imp < minImp)
            minImp = imp;
        return imp;
    }

    private void findBounds() {
        double xoffset = 1.0 / Nx, yoffset = 1.0 / Ny, zoffset = 1.0 / Nz;
        double x05 = xoffset / 2, y05 = yoffset / 2, z05 = zoffset / 2;
        for (int i = 0; i < Nx; ++i)
            for (int j = 0; j < Ny; ++j)
                for (int k = 0; k < Nz; ++k) {
                    calcImpact(x05 + i * xoffset, y05 + j * yoffset, z05 + k * zoffset);
                }
    }

    private void calcAbs() {
        double start, end;
        int vertices = absGraph.size();
        Arrays.fill(abs, 0);
        for (int i = 0; i < vertices - 1; ++i) {
            start = absGraph.get(i).abs;
            end = absGraph.get(i + 1).abs;
            int x1 = absGraph.get(i).x, x2 = absGraph.get(i + 1).x;
            int len = x2 - x1;
            if (len == 0) {
                abs[x1] = absGraph.get(i + 1).abs;
            }
            for (int j = 0; j < len; ++j) {
                abs[x1 + j] = start + j * (end - start) / len;
            }
        }
    }

    private double getAbs(double imp) {
        int newCoord = (int) Math.round((imp - minImp) * 100 / (maxImp - minImp));
        if (newCoord >= 100) {
            newCoord = 99;
        }
        if (newCoord < 0) {
            newCoord = 0;
        }
        return abs[newCoord];
    }

    private void calcEmis() {
        int red, green, blue;
        int rStart, rEnd, gStart, gEnd, bStart, bEnd;
        int vertices = emisGraph.size();
        for (int i = 0; i < vertices - 1; ++i) {
            MyPoint curr = emisGraph.get(i), next = emisGraph.get(i + 1);
            rStart = curr.r;
            rEnd = next.r;
            gStart = curr.g;
            gEnd = next.g;
            bStart = curr.b;
            bEnd = next.b;
            int x1 = curr.x, x2 = next.x;
            int len = x2 - x1;
            if (len == 0) {
                emis[x1] = new Color(next.r, next.g, next.b);
            }
            for (int j = 0; j < len; ++j) {
                red = rStart + j * (rEnd - rStart) / len;
                green = gStart + j * (gEnd - gStart) / len;
                blue = bStart + j * (bEnd - bStart) / len;
                emis[x1 + j] = new Color(red, green, blue);
            }
        }
    }

    private Color getEmis(double imp) {
        int newCoord = (int) (Math.round(imp - minImp) * 100 / (maxImp - minImp));
        if (newCoord >= 100) {
            newCoord = 99;
        }
        if (newCoord < 0) {
            newCoord = 0;
        }
        return emis[newCoord];
    }

    private Color getNewColor(int r, int g, int b, int x, int y) {
        MyPoint current;
        int step = 350 / Nz;
        double dz = 1.0 / Nz;
        double imp;
        double rDouble = (double) r, gDouble = (double) g, bDouble = (double) b;
        for (int z = 0; z < 350; z += step) {
            current = getVoxelCenter((double) x, (double) y, (double) z);
            imp = calcImpact(current.x1, current.y1, current.z1);
            if (isAbs) {
                double exp = Math.exp(-getAbs(imp) * dz);
                rDouble = (rDouble * exp);
                gDouble = (gDouble * exp);
                bDouble = (bDouble * exp);
            }
            if (isEmis) {
                Color emisColor = getEmis(imp);
                rDouble += emisColor.getRed() * dz;
                gDouble += emisColor.getGreen() * dz;
                bDouble += emisColor.getBlue() * dz;
            }
        }
        return trimColor((int) rDouble, (int) gDouble, (int) bDouble);
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

    private MyPoint getVoxelCenter(double x, double y, double z) {
        double dx = x / width;
        double dy = y / height;
        double dz = z / 350;
        BigDecimal bigDecimal;
        if (dx != 0)
            dx = (double) dx / (1.0 / Nx);
        bigDecimal = new BigDecimal(dx);
        bigDecimal = bigDecimal.setScale(0, RoundingMode.HALF_DOWN);
        int voxelnumX = bigDecimal.intValue();
        dx = 1.0 / Nx;
        if (dy != 0)
            dy = (double) dy / (1.0 / Ny);
        bigDecimal = new BigDecimal(dy);
        bigDecimal = bigDecimal.setScale(0, RoundingMode.HALF_DOWN);
        int voxelnumY = bigDecimal.intValue();
        dy = 1.0 / Ny;
        if (dz != 0)
            dz = (double) dz / (1.0 / Nz);
        bigDecimal = new BigDecimal(dz);
        bigDecimal = bigDecimal.setScale(0, RoundingMode.HALF_DOWN);
        int voxelnumZ = bigDecimal.intValue();
        dz = 1.0 / Nz;
        return new MyPoint(dx / 2 + voxelnumX * dx, dy / 2 + voxelnumY * dy, dz / 2 + voxelnumZ * dz);
    }

    public double[] getAbs() {
        return abs;
    }

    public Color[] getEmis() {
        return emis;
    }
}
