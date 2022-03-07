package ru.nsu.g16203.Shustova.MyClasses.Filters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Rotate implements Filter {
    private int angle = 30;

    @Override
    public void setFilter(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[] newPixels = new int[width * height];
        int white = Color.WHITE.getRGB();
        Arrays.fill(newPixels, white);
        double angleRad = Math.toRadians(angle);
        double sin = Math.sin(angleRad);
        double cos = Math.cos(angleRad);
        double x0 = 0.5 * (width - 1);
        double y0 = 0.5 * (height - 1);
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                double a = x - x0;
                double b = y - y0;
                int xx = (int) (+a * cos - b * sin + x0);
                int yy = (int) (+a * sin + b * cos + y0);

                if (xx >= 0 && xx < width && yy >= 0 && yy < height) {
                    newPixels[x * width + y] = img.getRGB(xx, yy);
                }
            }
        }
        for (int x = 0; x < width; ++x)
            for (int y = 0; y < height; ++y) {
                img.setRGB(x, y, newPixels[x * width + y]);
            }
    }
    public void setAngle(int newA){
        angle = newA;
    }
}
