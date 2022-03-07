package ru.nsu.g16203.Shustova.MyClasses;

import ru.nsu.g16203.Shustova.MyClasses.Filters.Filter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ZoneC extends JPanel {
    private BufferedImage myImageC, currentImage;
    private ZoneB myZoneB;
    private Filter myFilter;
    private int size;

    public ZoneC() {
        setPreferredSize(new Dimension(351, 351));
        this.setBorder(BorderFactory.createDashedBorder(null));
    }

    public void setImage(BufferedImage img, int s) {
        BufferedImage temp = new BufferedImage(img.getColorModel(), img.getRaster().createCompatibleWritableRaster(s, s),
                img.isAlphaPremultiplied(), null);
        img.copyData(temp.getRaster());
        size = s;
        myImageC = temp;
        repaint();
    }

    public void setMyzoneB(ZoneB b) {
        myZoneB = b;
        translate();
    }

    public void translate() {
        if ((myZoneB != null) && (myImageC != null)) {
            myZoneB.setImage(myImageC, size);
        }
    }

    public void setMyFilter(Filter filter) {
        if (myImageC != null) {
            myFilter = filter;
            myFilter.setFilter(myImageC);
            repaint();
        }
    }

    public void save(File f) throws IOException {
        ImageIO.write(myImageC, "bmp", f);
    }

    public void clear() {
        if (myImageC != null) {
            for (int x = 0; x < myImageC.getWidth(); ++x)
                for (int y = 0; y < myImageC.getHeight(); ++y) {
                    myImageC.setRGB(x, y, getBackground().getRGB());
                }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(myImageC, 0, 0, this);
    }
}
