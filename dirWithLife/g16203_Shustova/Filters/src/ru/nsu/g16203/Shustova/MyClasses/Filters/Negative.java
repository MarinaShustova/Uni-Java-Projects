package ru.nsu.g16203.Shustova.MyClasses.Filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Negative implements Filter {
    @Override
    public void setFilter(BufferedImage img) {
        for (int x = 0; x < img.getWidth(); ++x) {
            for (int y = 0; y < img.getHeight(); ++y) {
                int rgba = img.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                        255 - col.getGreen(),
                        255 - col.getBlue());
                img.setRGB(x, y, col.getRGB());
            }
        }
    }
}
