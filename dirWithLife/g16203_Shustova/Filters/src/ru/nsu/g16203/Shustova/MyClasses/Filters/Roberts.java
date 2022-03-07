package ru.nsu.g16203.Shustova.MyClasses.Filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Roberts implements Filter {
    private int C = 40;

    @Override
    public void setFilter(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[] newPixels = new int[width * height];
        Color c;
        float gij = 0, gi1j = 0, gij1 = 0, gi1j1 = 0, Fij;
        for (int x = 1; x < width - 1; ++x) {
            for (int y = 1; y < height - 1; ++y) {
                gij = img.getRGB(x, y);
                c = new Color((int) gij);
                gij = (c.getRed() * 0.2126f + c.getGreen() * 0.7152f + c.getBlue() * 0.0722f);
                gi1j = img.getRGB(x + 1, y);
                c = new Color((int) gi1j);
                gi1j = (c.getRed() * 0.2126f + c.getGreen() * 0.7152f + c.getBlue() * 0.0722f);
                gij1 = img.getRGB(x, y + 1);
                c = new Color((int) gij1);
                gij1 = (c.getRed() * 0.2126f + c.getGreen() * 0.7152f + c.getBlue() * 0.0722f);
                gi1j1 = img.getRGB(x + 1, y + 1);
                c = new Color((int) gi1j1);
                gi1j1 = (c.getRed() * 0.2126f + c.getGreen() * 0.7152f + c.getBlue() * 0.0722f);
                Fij = Math.abs(Math.abs(gij) - Math.abs(gi1j1)) + Math.abs(Math.abs(gi1j) - Math.abs(gij1));
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

    public void setC(int newC) {
        C = newC;
    }
}
