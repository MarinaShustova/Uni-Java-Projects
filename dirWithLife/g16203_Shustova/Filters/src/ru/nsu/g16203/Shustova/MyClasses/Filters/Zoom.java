package ru.nsu.g16203.Shustova.MyClasses.Filters;

import java.awt.image.BufferedImage;

public class Zoom implements Filter {
    int side;
    @Override
    public void setFilter(BufferedImage img) {
        int height = img.getHeight();
        int width = img.getWidth();
        side = height;
        BufferedImage zoomedimg = img.getSubimage(height/4, width/4, (int)width/2, (int)height/2);
        int[] newPixels = resizeBilinear(zoomedimg.getRGB(0, 0, (int)width/2, (int)height/2, null, 0, (int)height/2), (int)width/2, (int)height/2, width, height);
        interpolate(img, newPixels);
    }

    private void interpolate(BufferedImage newI, int[] pixels) {
        int width = newI.getWidth();
        int height = newI.getHeight();
        for (int x = 0; x < width; ++x)
            for (int y = 0; y < height; ++y) {
                newI.setRGB(x, y, pixels[y * side + x]);
            }
    }

    public int[] resizeBilinear(int[] pixels, int w, int h, int w2, int h2) {
        int[] temp = new int[w2 * h2];
        int a, b, c, d, x, y, index;
        float x_ratio = ((float) (w - 1)) / w2;
        float y_ratio = ((float) (h - 1)) / h2;
        float x_diff, y_diff, blue, red, green;
        int offset = 0;
        for (int i = 0; i < h2; i++) {
            for (int j = 0; j < w2; j++) {
                x = (int) (x_ratio * j);
                y = (int) (y_ratio * i);
                x_diff = (x_ratio * j) - x;
                y_diff = (y_ratio * i) - y;
                index = (y * w + x);
                a = pixels[index];
                if (index + 1 < pixels.length) b = pixels[index + 1];
                else b = pixels[index];
                if (index + w < pixels.length) c = pixels[index + w];
                else c = pixels[index];
                if (index + w + 1 < pixels.length) d = pixels[index + w + 1];
                else d = pixels[index];

                // blue element
                // Yb = Ab(1-w)(1-h) + Bb(w)(1-h) + Cb(h)(1-w) + Db(wh)
                blue = (a & 0xff) * (1 - x_diff) * (1 - y_diff) + (b & 0xff) * (x_diff) * (1 - y_diff) +
                        (c & 0xff) * (y_diff) * (1 - x_diff) + (d & 0xff) * (x_diff * y_diff);

                // green element
                // Yg = Ag(1-w)(1-h) + Bg(w)(1-h) + Cg(h)(1-w) + Dg(wh)
                green = ((a >> 8) & 0xff) * (1 - x_diff) * (1 - y_diff) + ((b >> 8) & 0xff) * (x_diff) * (1 - y_diff) +
                        ((c >> 8) & 0xff) * (y_diff) * (1 - x_diff) + ((d >> 8) & 0xff) * (x_diff * y_diff);

                // red element
                // Yr = Ar(1-w)(1-h) + Br(w)(1-h) + Cr(h)(1-w) + Dr(wh)
                red = ((a >> 16) & 0xff) * (1 - x_diff) * (1 - y_diff) + ((b >> 16) & 0xff) * (x_diff) * (1 - y_diff) +
                        ((c >> 16) & 0xff) * (y_diff) * (1 - x_diff) + ((d >> 16) & 0xff) * (x_diff * y_diff);

                temp[offset++] =
                        0xff000000 | // hardcode alpha
                                ((((int) red) << 16) & 0xff0000) |
                                ((((int) green) << 8) & 0xff00) |
                                ((int) blue);
            }
        }
        return temp;
    }
}
