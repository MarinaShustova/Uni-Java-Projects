package ru.nsu.g16203.Shustova.MyClasses.Filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Gamma implements Filter {
    private double gamma = 1;

    @Override
    public void setFilter(BufferedImage img) {
        int alpha, red, green, blue;
        int newPixel;

        double gamma_new =  gamma;
        int[] gamma_LUT = gamma_LUT(gamma_new);

        for (int x = 0; x < img.getWidth(); ++x) {
            for (int y = 0; y < img.getHeight(); ++y) {
                alpha = new Color(img.getRGB(x, y)).getAlpha();
                red = new Color(img.getRGB(x, y)).getRed();
                green = new Color(img.getRGB(x, y)).getGreen();
                blue = new Color(img.getRGB(x, y)).getBlue();

                red = gamma_LUT[red];
                green = gamma_LUT[green];
                blue = gamma_LUT[blue];
                newPixel = (new Color(red, green, blue, alpha)).getRGB();
                img.setRGB(x, y, newPixel);
            }
        }
    }

    private int[] gamma_LUT(double gamma_new) {
        int[] gamma_LUT = new int[256];

        for (int i = 0; i < gamma_LUT.length; i++) {
            gamma_LUT[i] = (int) (255 * (Math.pow((double) i / (double) 255, gamma_new)));
        }

        return gamma_LUT;
    }

    public void setGamma(double newG) {
        gamma = newG;
    }
}
