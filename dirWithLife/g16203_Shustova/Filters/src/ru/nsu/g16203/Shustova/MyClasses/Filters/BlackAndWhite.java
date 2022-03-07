package ru.nsu.g16203.Shustova.MyClasses.Filters;

import ru.nsu.g16203.Shustova.MyClasses.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlackAndWhite implements Filter {
    @Override
    public void setFilter(BufferedImage img) {
        for (int x = 0; x < img.getWidth(); ++x) {
            for (int y = 0; y < img.getHeight(); ++y) {
                int rgba = img.getRGB(x, y);
                Color col = new Color(rgba, true);
                int i = (int) Math.round(0.299 * col.getRed() + 0.587 * col.getGreen() + 0.114 * col.getBlue());
                col = new Color(i, i, i);
                img.setRGB(x, y, col.getRGB());
            }
        }
    }
}
