package ru.nsu.g16203.Shustova.MyClasses;

import javafx.scene.image.PixelFormat;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphZone extends JPanel {
    private BufferedImage myGraph;

    public GraphZone() {
        setPreferredSize(new Dimension(400, 135));
        this.setBorder(new MatteBorder(0, 1, 1, 0, Color.BLACK));
    }

    public void drawGraph(double[] graph) {
        myGraph = new BufferedImage(400, 128, BufferedImage.TYPE_INT_ARGB);
        Graphics g = myGraph.getGraphics();
        g.setColor(Color.BLACK);
        for (int i = 0; i < 99; ++i) {
            if ((graph[i] == 0 && graph[i + 1] != 0) || (graph[i] != 0 && graph[i + 1] == 0)) {
                g.drawLine(i * 4, (int) ((1 - graph[i]) * 128), i * 4, (int) ((1 - graph[i + 1]) * 128));
                g.drawLine(i * 4, (int) ((1 - graph[i + 1]) * 128), (i + 1) * 4, (int) ((1 - graph[i + 1]) * 128));
            } else
                g.drawLine(i * 4, (int) ((1 - graph[i]) * 128), (i + 1) * 4, (int) ((1 - graph[i + 1]) * 128));
        }
        repaint();
    }

    public void drawGraph(Color[] graph) {
        myGraph = new BufferedImage(400, 135, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = myGraph.getGraphics();
        Color currentColor, nextColor;
        int r, g, b, r2, g2, b2;
        for (int i = 0; i < 99; ++i) {
            currentColor = graph[i];
            nextColor = graph[i + 1];
            r = currentColor.getRed();
            g = currentColor.getGreen();
            b = currentColor.getBlue();
            r2 = nextColor.getRed();
            g2 = nextColor.getGreen();
            b2 = nextColor.getBlue();
            graphics.setColor(Color.RED);
            graphics.drawLine(i * 4, (int) ((255 - r) / 2.2 + 10), (i + 1) * 4, (int) ((255 - r2) / 2.2 + 10));
            graphics.setColor(Color.GREEN);
            graphics.drawLine((i + 1) * 4, (int) ((255 - g) / 2.2 + 11), (i + 2) * 4, (int) ((255 - g2) / 2.2 + 11));
            graphics.setColor(Color.BLUE);
            graphics.drawLine((i + 2) * 4, (int) ((255 - b) / 2.2 + 12), (i + 3) * 4, (int) ((255 - b2) / 2.2 + 12));
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(myGraph, 0, 6, this);
    }

}
