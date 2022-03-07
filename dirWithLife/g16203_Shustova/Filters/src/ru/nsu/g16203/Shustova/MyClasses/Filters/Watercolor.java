package ru.nsu.g16203.Shustova.MyClasses.Filters;

import java.awt.image.BufferedImage;

public class Watercolor implements Filter {
    @Override
    public void setFilter(BufferedImage img) {
        Median median = new Median();
        median.setFilter(img);
        Sharpen sharpen = new Sharpen();
        sharpen.setFilter(img);
    }
}
