package ru.nsu.g16203.Shustova.MyClasses.Filters;

import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("ALL")
public class Sobel implements Filter {
    private int C = 150;

    @Override
    public void setFilter(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[] newPixels = new int[width * height];
        float a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0, i = 0, Sx, Sy, Fij;
        Color color;
        for (int x = 1; x < width - 1; ++x) {
            for (int y = 1; y < height - 1; ++y) {
                e = img.getRGB(x, y);
                color = new Color((int) e);
                e = (color.getRed() * 0.2126f + color.getGreen() * 0.7152f + color.getBlue() * 0.0722f);
                a = img.getRGB(x - 1, y - 1);
                color = new Color((int) a);
                a = (color.getRed() * 0.2126f + color.getGreen() * 0.7152f + color.getBlue() * 0.0722f);
                b = img.getRGB(x, y - 1);
                color = new Color((int) b);
                b = (color.getRed() * 0.2126f + color.getGreen() * 0.7152f + color.getBlue() * 0.0722f);
                c = img.getRGB(x + 1, y - 1);
                color = new Color((int) c);
                c = (color.getRed() * 0.2126f + color.getGreen() * 0.7152f + color.getBlue() * 0.0722f);
                d = img.getRGB(x - 1, y);
                color = new Color((int) d);
                d = (color.getRed() * 0.2126f + color.getGreen() * 0.7152f + color.getBlue() * 0.0722f);
                f = img.getRGB(x + 1, y);
                color = new Color((int) f);
                f = (color.getRed() * 0.2126f + color.getGreen() * 0.7152f + color.getBlue() * 0.0722f);
                g = img.getRGB(x - 1, y + 1);
                color = new Color((int) g);
                g = (color.getRed() * 0.2126f + color.getGreen() * 0.7152f + color.getBlue() * 0.0722f);
                h = img.getRGB(x, y + 1);
                color = new Color((int) h);
                h = (color.getRed() * 0.2126f + color.getGreen() * 0.7152f + color.getBlue() * 0.0722f);
                i = img.getRGB(x + 1, y + 1);
                color = new Color((int) i);
                i = (color.getRed() * 0.2126f + color.getGreen() * 0.7152f + color.getBlue() * 0.0722f);
                Sx = (c + 2 * f + i) - (a + 2 * d + g);
                Sy = (g + 2 * h + i) - (a + 2 * b + c);
                Fij = Math.abs(Sx) + Math.abs(Sy);
                if (Fij > C)
                    newPixels[x * width + y] = Color.WHITE.getRGB();
                else
                    newPixels[x * width + y] = Color.BLACK.getRGB();
            }
        }
        for (int x = 0; x < width; ++x)
            for (int y = 0; y < height; ++y) {
                img.setRGB(x, y, newPixels[x * width + y]);
            }
    }
    public void setC(int newC){
        C = newC;
    }
}
