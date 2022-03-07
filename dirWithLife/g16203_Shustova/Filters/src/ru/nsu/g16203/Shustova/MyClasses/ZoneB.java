package ru.nsu.g16203.Shustova.MyClasses;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ZoneB extends JPanel {
    private BufferedImage myImageB;
    private ZoneC myZoneC;
    private int size;

    public ZoneB() {
        setPreferredSize(new Dimension(351, 351));
        this.setBorder(BorderFactory.createDashedBorder(null));
    }

    public void setImage(BufferedImage img, int size) {
        BufferedImage temp = new BufferedImage(img.getColorModel(), img.getRaster().createCompatibleWritableRaster(size, size),
                img.isAlphaPremultiplied(), null);
        img.copyData(temp.getRaster());
        myImageB = temp;
        this.size = size;
        repaint();
    }

    public void setMyZoneC(ZoneC c) {
        myZoneC = c;
        translate();
    }

    public void translate() {
        if ((myZoneC != null) && (myImageB != null)) {
            myZoneC.setImage(myImageB, size);
        }
    }

    public void clear() {
        if (myImageB != null) {
            for (int x = 0; x < myImageB.getWidth(); ++x)
                for (int y = 0; y < myImageB.getHeight(); ++y) {
                    myImageB.setRGB(x, y, getBackground().getRGB());
                }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(myImageB, 0, 0, this);
    }
}
