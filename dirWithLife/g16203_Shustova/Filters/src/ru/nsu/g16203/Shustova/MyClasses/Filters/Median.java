package ru.nsu.g16203.Shustova.MyClasses.Filters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Median implements Filter {
    @Override
    public void setFilter(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[] newPixels = new int[width * height];
        Color[] pixel = new Color[25];
        int[] R = new int[25];
        int[] B = new int[25];
        int[] G = new int[25];
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                for (int i = 0; i < 5; ++i) {
                    for (int j = 0; j < 5; ++j) {
                        if (x - 2 + j >= 0 && y - 2 + i >= 0) {
                            if (x - 2 + j < width && y - 2 + i < height)
                                pixel[i * 5 + j] = new Color(img.getRGB(x - 2 + j, y - 2 + i));
                            else {
                                pixel[i*5+j] = new Color(img.getRGB(x,y));
                            }
                        } else {
                            pixel[i*5+j] = new Color(img.getRGB(x,y));
                        }
                    }
                }
                for (int k = 0; k < 25; k++) {
                    R[k] = pixel[k].getRed();
                    B[k] = pixel[k].getBlue();
                    G[k] = pixel[k].getGreen();
                }
                Arrays.sort(R);
                Arrays.sort(G);
                Arrays.sort(B);
                newPixels[x * width + y] = (new Color(R[13], G[13], B[13])).getRGB();
            }
        }
        for (int x = 0; x < width; ++x)
            for (int y = 0; y < height; ++y) {
                img.setRGB(x, y, newPixels[x * width + y]);
            }
    }
}
