package ru.nsu.fit.g16203.shustova.isolines.MyClasses.View;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Legend extends JPanel {
    private BufferedImage legend;
    private Color[] palette;
    public boolean interpollation = false;
    private int sizeX, sizeY;

    public Legend(Color[] palette) {
        sizeX = 100;
        sizeY = 600;
        this.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        legend = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
        this.palette = palette;
        redraw(true);
    }

    public void redraw(boolean callrepaint){
        if (interpollation)
            interpollate();
        else
            colorImage();
        if(callrepaint)
            repaint();
    }

    @Override
    public void setBounds(int x, int y, int width, int height){
        super.setBounds(x,y,width,height);
        sizeX = width;
        sizeY = height;
        legend = null;
        repaint();
    }

    private void recompute(){
        legend = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
        redraw(false);
    }

    private void colorImage() {
        int height = legend.getHeight();
        int width = legend.getWidth();
        int offset = height / palette.length;
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j){
                try {
                    legend.setRGB(i, j, palette[j / offset].getRGB());
                }
                catch (ArrayIndexOutOfBoundsException e){
                    legend.setRGB(i, j, palette[palette.length-1].getRGB());
                }
                catch (ArithmeticException ex){}
            }
        }
        repaint();
    }

    private void interpollate(){
        int height = legend.getHeight();
        int width = legend.getWidth();
        double offset = (double) height / palette.length;
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j){
                if (j < offset/2)
                    legend.setRGB(i,j,palette[0].getRGB());
                if (j >= (palette.length-1)*offset + offset/2)
                    legend.setRGB(i,j,palette[(int)(j/offset)].getRGB());
                else {
                    int c1 = (int)((j - offset/2)/offset) < 0?0:(int)((j - offset/2)/offset);
                    int c2 = c1+1 > palette.length-1? palette.length-1 : c1+1;
                    legend.setRGB(i, j, interpolatedColor(palette[c1], palette[c2], j,
                            offset * c1 + offset / 2, offset * c2 + offset / 2).getRGB());
                }
            }
        }
    }

    private Color interpolatedColor(Color c1, Color c2, double u, double v1, double v2){
        int r = (int) (c1.getRed() * (v2 - u) / (v2 - v1) + c2.getRed() * (u - v1) / (v2 - v1));
        int g = (int) (c1.getGreen() * (v2 - u) / (v2 - v1) + c2.getGreen() * (u - v1) / (v2 - v1));
        int b = (int) (c1.getBlue() * (v2 - u) / (v2 - v1) + c2.getBlue() * (u - v1) / (v2 - v1));
        return trimColor( r, g, b );
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (legend == null)
            recompute();
        g.drawImage(legend, 0, 0, this);
    }
}
