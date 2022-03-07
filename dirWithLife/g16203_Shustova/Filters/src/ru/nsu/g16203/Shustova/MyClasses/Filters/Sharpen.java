package ru.nsu.g16203.Shustova.MyClasses.Filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sharpen implements Filter {
    private int r = 0, g = 0, b = 0;

    @Override
    public void setFilter(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[] newPixels = new int[width * height];
        int br, dr, er, fr, hr, xMax = width - 1, yMax = height - 1;
        Color color;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                er = img.getRGB(x, y);
                color = new Color(er);
                addValue(color, 5, 1);
                br = img.getRGB(x, (y - 1 >= 0 ? y - 1 : y));
                color = new Color(br);
                addValue(color, -1, 1);
                dr = img.getRGB((x - 1 >= 0 ? x - 1 : x), y);
                color = new Color(dr);
                addValue(color, -1, 1);
                fr = img.getRGB((x + 1 <= xMax ? x + 1 : xMax), y);
                color = new Color(fr);
                addValue(color, -1, 1);
                hr = img.getRGB(x, (y + 1 <= yMax ? y + 1 : y));
                color = new Color(hr);
                addValue(color, -1, 1);
                newPixels[x * width + y] = (trimColor(r, g, b)).getRGB();
                r = 0;
                g = 0;
                b = 0;
            }
        }
        for (int x = 0; x < width; ++x)
            for (int y = 0; y < height; ++y) {
                img.setRGB(x, y, newPixels[x * width + y]);
            }
    }

    private void addValue(Color color, int mkoeff, int dkoeff) {
        r += (color.getRed() * mkoeff) / dkoeff;
        g += (color.getGreen() * mkoeff) / dkoeff;
        b += (color.getBlue() * mkoeff) / dkoeff;
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
}
