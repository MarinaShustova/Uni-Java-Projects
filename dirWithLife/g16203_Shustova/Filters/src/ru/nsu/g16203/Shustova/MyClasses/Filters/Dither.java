package ru.nsu.g16203.Shustova.MyClasses.Filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Dither implements Filter {
    private int Nr = 5, Ng = 5, Nb = 5;

    @Override
    public void setFilter(BufferedImage img) {
        Color curr;
        int width = img.getWidth();
        int height = img.getHeight();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                int rgb = img.getRGB(x, y);
                curr = new Color(rgb);
                int r = closestComponent(curr.getRed(), Nr);
                int g = closestComponent(curr.getGreen(), Ng);
                int b = closestComponent(curr.getBlue(), Nb);
                img.setRGB(x, y, (new Color(r, g, b)).getRGB());
                r = err(curr.getRed(), r);
                g = err(curr.getGreen(), g);
                b = err(curr.getBlue(), b);
                if (x + 1 < width) {
                    curr = new Color(img.getRGB(x + 1, y));
                    img.setRGB(x + 1, y, (trimColor(curr.getRed() + (int) Math.round(r * 7. / 16),
                            curr.getGreen() + (int) Math.round(g * 7. / 16), curr.getBlue() + (int) Math.round(b * 7. / 16))).getRGB());
                }
                if ((x - 1 >= 0) && (y + 1 < height)) {
                    curr = new Color(img.getRGB(x - 1, y + 1));
                    img.setRGB(x - 1, y + 1, (trimColor(curr.getRed() + (int) Math.round(r * 3. / 16),
                            curr.getGreen() + (int) Math.round(g * 3. / 16), curr.getBlue() + (int) Math.round(b * 3. / 16))).getRGB());
                }
                if (y + 1 < height) {
                    curr = new Color(img.getRGB(x, y + 1));
                    img.setRGB(x, y + 1, (trimColor(curr.getRed() + (int) Math.round(r * 5. / 16),
                            curr.getGreen() + (int) Math.round(g * 5. / 16), curr.getBlue() + (int) Math.round(b * 5. / 16))).getRGB());
                }
                if ((x + 1 < width) && (y + 1 < height)) {
                    curr = new Color(img.getRGB(x + 1, y + 1));
                    img.setRGB(x + 1, y + 1, (trimColor(curr.getRed() + (int) Math.round(r * 7. / 16),
                            curr.getGreen() + (int) Math.round(g * 1. / 16), curr.getBlue() + (int) Math.round(b * 1. / 16))).getRGB());
                }
            }
        }
    }

    public void setParams(int r, int g, int b) {
        Nr = r;
        Ng = g;
        Nb = b;
    }

    private int closestComponent(int color, int n) {
        if (n == 1) {
            return 0;
        }
        int closeColor = ((int) Math.round(color * (n - 1) / 255d)) * 255 / (n - 1);
        return closeColor;
    }

    private int err(int oldcomp, int newComp) {
        return oldcomp - newComp;
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
