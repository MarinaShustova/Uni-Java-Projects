package ru.nsu.fit.g16203.shustova.raytracing.MyClasses.View;

import javafx.util.Pair;
import ru.nsu.fit.g16203.shustova.raytracing.MyClasses.Logic.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class MyInitView extends JPanel {
    private int n, w = 700, h = 700, sceneW, sceneH, x, y, newW, newH;
    private double sw = 1, sh = 1;
    private BufferedImage scene;
    private Scene box;
    public Color background = Color.BLACK;

    public MyInitView() {
        super();
        setPreferredSize(new Dimension(w, h));
        setBackground(Color.BLACK);
        sceneW = w - 20;
        sceneH = h - 20;
        newW = sceneW;
        newH = sceneH;
        scene = new BufferedImage(sceneW, sceneH, BufferedImage.TYPE_INT_ARGB);
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (box != null) {
                    int deltaX = e.getX() - x;
                    int deltaY = e.getY() - y;
                    x = e.getX();
                    y = e.getY();
                    box.thetaX = (double) deltaX / 100;
                    box.thetaY = (double) deltaY / 100;
                    box.recreate();
                    createScene();
                }
            }
        });
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (box != null) {
                    if (e.getWheelRotation() < 0) {
                        box.camera.zn = box.camera.zn * 1.07;
                        box.camera.zf = box.camera.zf * 1.07;
                        box.recreate();
                        createScene();

                    } else {
                        box.camera.zn = box.camera.zn / 1.07;
                        box.camera.zf = box.camera.zf / 1.07;
                        box.recreate();
                        createScene();
                    }
                }
            }
        });
    }

    public void Background(Color c) {
        setBackground(c);
        background = c;
    }

    public void setBox(Scene box) {
        this.box = box;
//        this.box.recreate();
    }

    public void createScene() {
        scene = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics g = scene.getGraphics();
        for (Figure f : box.getFigures()) {
            drawFigure(f, g, f.color);
        }
        for (Figure f : box.getAxis()) {
            drawFigure(f, g, f.color);
        }
        repaint();
        box.resetScene();
    }

    private void drawFigure(Figure figure, Graphics g, Color color) {
        Point3 point1, point2;
        g.setColor(color);
        for (Pair<Point3, Point3> p : figure.newEdges) {
            point1 = p.getKey();
            point2 = p.getValue();
            int x1 = (int) Math.round(newW * (point1.x + 1) / (1 + 1));
            int y1 = (int) Math.round(newH * (point1.y + 1) / (1 + 1));
            int x2 = (int) Math.round(newW * (point2.x + 1) / (1 + 1));
            int y2 = (int) Math.round(newH * (point2.y + 1) / (1 + 1));
            g.drawLine(x1, y1, x2, y2);
            repaint();
        }
    }

    public void recreate(double sw, double sh) {
        this.sw = sw;
        this.sh = sh;
        if (sw >= sh) {
            if (sw == sh) {
                if (sceneW > sceneH) {
                    newW = newH = sceneH;
                } else {
                    newW = newH = sceneW;
                }
            } else {
                newW = sceneW;
                newH = (int) Math.round(sceneW * sh / sw);
            }
        } else {
            newH = sceneH;
            newW = (int) Math.round(sceneH * sw / sh);
        }
        if (box != null)
            box.recreate();
        createScene();
    }

    public void recreate() {
        scene = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        createScene();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        if (width>=0 && height >= 0) {
            sceneW = width - 20;
            sceneH = height - 20;
            newW = width - 20;
            newH = height - 20;
            recreate(sw, sh);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(scene, 10, 10, this);
        drawBorder(g);
    }


    private void drawBorder(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(10, 10, newW, newH);
    }
}
