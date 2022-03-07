package ru.nsu.fit.g16203.shustova.isolines.MyClasses.View;

import ru.nsu.fit.g16203.shustova.isolines.MyClasses.Logic.Function;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Ticks extends JPanel {
    private int nColors;
    private Function function;
    private BufferedImage ticks;
    private Legend legend;
    private int sizeX, sizeY;

    public Ticks(int n, Color[] colors, Function f) {
        sizeX = 150;
        sizeY = 600;
        setLayout(new BorderLayout());
        legend = new Legend(colors);
        this.setBorder(new MatteBorder(0, 0, 0, 1, Color.WHITE));
        add(legend, BorderLayout.WEST);
        nColors = n;
        function = f;
        ticks = new BufferedImage(sizeX/3, sizeY, BufferedImage.TYPE_INT_ARGB);
        drawTicks();
        repaint();
    }

    private void drawTicks() {
        Graphics g = ticks.getGraphics();
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.BOLD, 15));
        int offset = sizeY / nColors;
        double step = (function.getMax() - function.getMin()) / nColors;
        for (int i = 0; i < nColors; ++i) {
            BigDecimal bigDecimalFI = new BigDecimal(function.getMin() + i * step);
            bigDecimalFI = bigDecimalFI.setScale(3, RoundingMode.HALF_UP);
            g.drawString("" + bigDecimalFI.doubleValue(), 5, i * offset);
        }
    }

    public Legend getLegend(){
        return legend;
    }

    @Override
    public void setBounds(int x, int y, int width, int height){
        super.setBounds(x,y,width,height);
        sizeX = width;
        sizeY = height;
        ticks = null;
        legend.setBounds(0,0,2*width/3, height);
        legend.setPreferredSize(new Dimension(2*width/3, height));
    }

    private void recompute(){
        ticks = new BufferedImage(sizeX/3, sizeY, BufferedImage.TYPE_INT_ARGB);
        drawTicks();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ticks == null)
           recompute();
        g.drawImage(ticks, (int) (2 * sizeX)/3, 0, this);
    }
}
