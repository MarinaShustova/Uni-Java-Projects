package ru.nsu.g16203.Shustova.MyClasses;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ZoneA extends JPanel {
    public boolean isSelection = false;
    private BufferedImage myImage, borderImage, originalImage;
    private int xBorder = 0, yBorder = 0;
    private double k = 1.0;
    private int size, newX, newY;
    private boolean firstApp;
    private ZoneB myzoneB;
    private BasicStroke dash = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
            5.0f, new float[]{5.0f}, 0.0f);

    public ZoneA() {
        firstApp = true;
        setPreferredSize(new Dimension(351, 351));
        this.setBorder(BorderFactory.createDashedBorder(null));
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (isSelection)
                    selectArea(e.getX(), e.getY());
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isSelection)
                    selectArea(e.getX(), e.getY());
            }
        });
    }

    public void reset() {
        firstApp = true;
    }

    public void clear() {
        if (borderImage != null) {
            for (int x = 0; x < borderImage.getWidth(); ++x)
                for (int y = 0; y < borderImage.getHeight(); ++y) {
                    borderImage.setRGB(x, y, getBackground().getRGB());
                }
            firstApp = true;
            repaint();
        }
    }

    private void selectArea(int x, int y) {
        boolean foundx = false, foundy = false;
        if (myImage != null) {
            if (firstApp)
                firstApp = false;
            else
                drawSquare(borderImage.getGraphics());
            if ((x + size / 2) > newX) {
                foundx = true;
                xBorder = newX - size;
            }
            if (x - size / 2 < 0) {
                foundx = true;
                xBorder = 0;
            }
            else if (!foundx) {
                xBorder = x - size / 2;
            }
            if ((y + size / 2) > newY) {
                foundy = true;
                yBorder = newY - size;
            }
            if (y - size / 2 < 0) {
                foundy = true;
                yBorder = 0;
            }
            else if (!foundy){
                yBorder = y - size / 2;
            }
            translate();
            drawSquare(borderImage.getGraphics());
            repaint();
        }
    }

    public void uploadImage(File imgFile) {
        try {
            myImage = ImageIO.read(imgFile);
            originalImage = myImage;
            myImage = scale();
            ColorModel cm = myImage.getColorModel();
            boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
            WritableRaster raster = myImage.copyData(null);
            borderImage = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void translate() {
        if (myzoneB != null) {
            int w = 350, h = 350;
            if (h > originalImage.getHeight() || w > originalImage.getWidth()){
                w = (int)Math.round(size*k);
                h = (int)Math.round(size*k);
            }
            int side = 350;
            if (h == 350 && w == 350)
                side = 350;
            else
                side = (int)Math.round(size*k);
            int x1 = (int) Math.round(xBorder * k), y1 = (int) Math.round(yBorder * k);
            if (x1 + side > originalImage.getRaster().getWidth())
                x1 = originalImage.getWidth() - side;
            if (y1 + side > originalImage.getRaster().getHeight())
                y1 = originalImage.getHeight() - side;
            try {
                myzoneB.setImage(originalImage.getSubimage(x1, y1, w, h), side);
            } catch (RasterFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMyzoneB(ZoneB b) {
        myzoneB = b;
    }

    private BufferedImage scale() {
        int height = myImage.getHeight();
        int width = myImage.getWidth();
        k = 1;
        if (width > height)
            k = (double) myImage.getWidth() / 350;
        else
            k = (double) myImage.getHeight() / 350;
        if (height < 350 || width < 350){
            size = height < width ? height : width;
            size = (int)Math.round(size/k);
        }
        else
            size = (int) Math.round(350 / k);
        newX = (int) Math.round(width / k);
        newY = (int) Math.round(height / k);
        BufferedImage after = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale((double) 1 / k, (double) 1 / k);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(myImage, after);
        return after;
    }

    private void drawSquare(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setXORMode(Color.WHITE);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(dash);
        g2d.draw(new Rectangle2D.Double(xBorder, yBorder, size - 1, size - 1));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(borderImage, 0, 0, this);
    }

    public void synchronise() {
        if (borderImage != null) {
            drawSquare(borderImage.getGraphics());
            repaint();
        }
    }
}
