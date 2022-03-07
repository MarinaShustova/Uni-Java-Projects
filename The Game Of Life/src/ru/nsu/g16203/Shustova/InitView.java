package ru.nsu.g16203.Shustova;

import ru.nsu.g16203.Shustova.logic.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.*;

public class InitView extends JPanel {

    private int size = 20;
    private int Bwidth = 1;
    private int xsize, ysize, mcount = 25, ncount = 25;
    private boolean isXOR = false, isWithImpacts = false, recovery = false, timeToRevive = true, isClick = false;
    private BufferedImage myImage;
    private HexaCell currentCell, cellFromTheField, previousCell = new HexaCell(-1, -1, -1, -1, -1);
    private Field field;
    private Timer myTimer;
    private Settings mysettings;
    public boolean isSaved = true;

    public Settings getMysettings() {
        return mysettings;
    }

    public InitView() {
        super();
        double width = Math.sqrt(3.0) * size;
        double height = 2 * size * 3.0 / 4.0;
        xsize = (int) Math.round(mcount * width + width / 2);
        ysize = (int) Math.round(ncount * height + height / 2);
        field = new Field(ncount, mcount, size);
        mysettings = new Settings(mcount, ncount, size, Bwidth, false);
        setPreferredSize(new Dimension(xsize, ysize));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (timeToRevive) {
                    try {
                        if (myImage.getRGB(e.getX(), e.getY()) != Color.BLACK.getRGB()) {
                            currentCell = getHexa(e.getX(), e.getY());
                            cellFromTheField = field.getCell(currentCell.getRow(), currentCell.getCol(), field.field, mysettings.m, mysettings.n);
                            isClick = true;
                            control(e.getX(), e.getY());
                            isClick = false;
                            if ((!cellFromTheField.isAlive() && (!isXOR)) || (isXOR))
                                field.changeState(cellFromTheField);
                            repaint();
                            isSaved = false;
                        }
                    } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
                    }
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (timeToRevive) {
                    try {
                        if (myImage.getRGB(e.getX(), e.getY()) != Color.BLACK.getRGB()) {
                            currentCell = getHexa(e.getX(), e.getY());
                            cellFromTheField = field.getCell(currentCell.getRow(), currentCell.getCol(), field.field, mysettings.m, mysettings.n);
                            if (!cellFromTheField.equals(previousCell)) {
                                previousCell = cellFromTheField;
                                isClick = true;
                                control(e.getX(), e.getY());
                                isClick = false;
                                if ((!cellFromTheField.isAlive() && (!isXOR)) || (isXOR))
                                    field.changeState(cellFromTheField);
                                repaint();
                                isSaved = false;
                            }
                        }
                    } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image img = (myImage == null) ? createImage() : myImage;
        g.drawImage(img, 0, 0, this);
    }

    protected Image createImage() {
        BufferedImage bufferedImage = new BufferedImage(xsize, ysize, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.getGraphics();
        g.setColor(Color.BLACK);
        drawField(g, mcount, ncount);
        myImage = bufferedImage;
        return bufferedImage;
    }

    protected void span2(int x, int y, Color c){
        ArrayList<Span> spans = new ArrayList<>();
        int currx, curry;
        spans.add(findSpan(x, y));
        boolean isAddedUp = false, isAddedDown = false;
        while (!spans.isEmpty()) {
            Span curr = spans.get(spans.size() - 1);
            spans.remove(curr);
            currx = (int) curr.a.x;
            curry = (int) curr.a.y;
            while (currx <= (int) curr.b.x) {
                myImage.setRGB(currx, curry, c.getRGB());
                ++currx;
            }
            currx = (int) curr.a.x;
            for (int i = 0; i < curr.length(); ++i) {
                if ((myImage.getRGB(currx, curry + 1) != Color.BLACK.getRGB()) && (myImage.getRGB(currx, curry + 1) != c.getRGB())) {
                    if (!isAddedUp) {
                        spans.add(findSpan(currx, curry + 1));
                        isAddedUp = true;
                    }
                }
                if (myImage.getRGB(currx, curry + 1) == Color.BLACK.getRGB())
                    isAddedUp = false;
                if ((myImage.getRGB(currx, curry - 1) != Color.BLACK.getRGB()) && (myImage.getRGB(currx, curry - 1) != c.getRGB())) {
                    if (!isAddedDown) {
                        spans.add(findSpan(currx, curry - 1));
                        isAddedDown = true;
                    }
                }
                if (myImage.getRGB(currx, curry - 1) == Color.BLACK.getRGB())
                    isAddedDown = false;
                ++currx;
            }
            isAddedDown = false;
            isAddedUp = false;
        }
    }

    protected void control(int x, int y){
        if (!isXOR)
            span2(x, y, Color.RED);
        else if (cellFromTheField.isAlive() && !recovery)
            span2(x,y, new Color(0.0f, 0.0f, 0.0f, 0.0f));
        else
            span2(x, y, Color.RED);
        if (isWithImpacts && (size > 10) && isClick) {
            Graphics g = myImage.getGraphics();
            drawImpact(g, true);
        }
    }

    protected Span findSpan(int x, int y) {
        int currx = x;
        while (myImage.getRGB(currx, y) != Color.BLACK.getRGB()) {
            ++currx;
        }
        Point end = new Point(currx - 1, y);
        currx = x;
        while (myImage.getRGB(currx, y) != Color.BLACK.getRGB()) {
            --currx;
        }
        Point beg = new Point(currx + 1, y);
        return new Span(beg, end);
    }

    protected void drawField(Graphics g, int m, int n) {
        double width = Math.sqrt(3.0) * size;
        double height = 2 * size;
        for (int i = 0; i < n; ++i)
            if (i % 2 == 0) {
                for (int j = 0; j < m; ++j) {
                    cellFromTheField = field.getCell(i, j, field.field, m, n);
                    if (Bwidth == 1)
                        drawHexagon(width / 2 + j * width, height / 2 + 3 * i * (height / 4), g, true);
                    else
                        drawFatHexagon(width / 2 + j * width/* + Bwidth*/, height / 2 + 3 * i * (height / 4)/* + Bwidth*/, g);
                    if (cellFromTheField.isAlive()) {
                        recovery = true;
                        control(cellFromTheField.getXcenter(), cellFromTheField.getYcenter());
                        recovery = false;
                    }
                    if ((isWithImpacts) && (size > 10)) {
                        drawImpact(g, false);
                        cellFromTheField.setOldImpact();
                    }
                }
            } else {
                for (int j = 0; j < m - 1; ++j) {
                    cellFromTheField = field.getCell(i, j, field.field, m, n);
                    if (Bwidth == 1)
                        drawHexagon(width * (j + 1), height / 2 + 3 * i * (height / 4), g, false);
                    else
                        drawFatHexagon(width * (j + 1)/* + Bwidth*/, height / 2 + 3 * i * (height / 4)/* + Bwidth*/, g);
                    if (cellFromTheField.isAlive()) {
                        recovery = true;
                        control(cellFromTheField.getXcenter(), cellFromTheField.getYcenter());
                        recovery = false;
                    }
                    if ((isWithImpacts) && (size > 10)) {
                        drawImpact(g, false);
                        cellFromTheField.setOldImpact();
                    }
                }
            }
    }

    private void drawImpact(Graphics g, boolean old) {
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.DARK_GRAY);
        BigDecimal bd;
        if (old)
            bd = new BigDecimal(cellFromTheField.getOldImpact().toString());
        else
            bd = new BigDecimal(cellFromTheField.getNewImpact().toString());
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        int height = Math.round(fm.getHeight() / 4);
        if (bd.movePointRight(1).remainder(new BigDecimal(10)).equals(new BigDecimal(0))) {
            String val = String.valueOf(bd.intValue());
            int width = Math.round(fm.charsWidth(val.toCharArray(), 0, 1) / 2);
            g.drawString(val, cellFromTheField.getXcenter() - width, cellFromTheField.getYcenter() + height);
        } else {
            String val = String.valueOf(bd.doubleValue());
            int width = Math.round(fm.charsWidth(val.toCharArray(), 0, 3) / 2);
            g.drawString(val, cellFromTheField.getXcenter() - width, cellFromTheField.getYcenter() + height);
        }
        g.setColor(Color.BLACK);
    }

    protected void drawBresenhamLine(Graphics g, int x1, int y1, int x2, int y2) {
        int dx = (x2 - x1) >= 0 ? 1 : -1;
        int dy = (y2 - y1) >= 0 ? 1 : -1;
        int lenX = Math.abs(x2 - x1);
        int lenY = Math.abs(y2 - y1);
        int len = Math.max(lenX, lenY);

        if (len == 0)
            g.drawLine(x1, y1, x2, y2);
        int x = x1;
        int y = y1;
        ++len;
        if (lenY <= lenX) {
            int d = -1 * lenX;
            while (len > 0) {
                g.drawLine(x, y, x, y);
                x += dx;
                d += 2 * lenY;
                if (d > 0) {
                    d -= 2 * lenX;
                    y += dy;
                }
                --len;
            }
        } else {
            int d = -1 * lenY;
            while (len > 0) {
                g.drawLine(x, y, x, y);
                y += dy;
                d += 2 * lenX;
                if (d > 0) {
                    d -= 2 * lenY;
                    x += dx;
                }
                --len;
            }
        }
    }

    protected void drawHexagon(double x, double y, Graphics g, boolean even) {
        MyPoint center = new MyPoint(x, y);
        MyPoint prevPoint = new MyPoint(-1, -1);
        MyPoint endPoint = null;
        MyPoint currPoint;
        for (int i = 0; i <= 6; ++i) {
            currPoint = getCornerPoint(center, size, i);
            if (i == 0)
                endPoint = currPoint;
            if (prevPoint.x != -1) {
                if (i == 6)
                    drawBresenhamLine(g, (int) Math.round(prevPoint.x), (int) Math.round(prevPoint.y), (int) Math.round(endPoint.x), (int) Math.round(endPoint.y));
                else if (even)
                    drawBresenhamLine(g, (int) Math.round(prevPoint.x), (int) Math.round(prevPoint.y), (int) Math.round(currPoint.x), (int) Math.round(currPoint.y));
                else
                    drawBresenhamLine(g, (int) Math.round(currPoint.x), (int) Math.round(currPoint.y), (int) Math.round(prevPoint.x), (int) Math.round(prevPoint.y));
            }
            prevPoint = currPoint;
        }
    }

    protected void drawFatHexagon(double x, double y, Graphics g) {
        MyPoint center = new MyPoint(x, y);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(Bwidth));
        MyPoint prevPoint = new MyPoint(-1, -1);
        MyPoint currPoint;
        for (int i = 0; i <= 6; ++i) {
            currPoint = getCornerPoint(center, size, i);
            if (prevPoint.x != -1)
                g2d.drawLine((int) Math.round(prevPoint.x), (int) Math.round(prevPoint.y), (int) Math.round(currPoint.x), (int) Math.round(currPoint.y));
            prevPoint = currPoint;
        }
    }

    protected MyPoint getCornerPoint(MyPoint center, double size, int i) {
        double angleDeg = 60 * i - 30;
        double angleRad = Math.PI / 180 * angleDeg;
        return new MyPoint(center.x + size * Math.cos(angleRad),
                center.y + size * Math.sin(angleRad));
    }

    public void redraw(Settings s, boolean reset) {
        double width = Math.sqrt(3.0) * s.k;
        double height = 2 * s.k * 3.0 / 4.0;
        mcount = s.m;
        ncount = s.n;
        BufferedImage bufferedImage = new BufferedImage((int) Math.round(s.m * width + width / 2), (int) Math.round(s.n * height + height / 2), BufferedImage.TYPE_INT_ARGB);
        setPreferredSize(new Dimension((int) Math.round(s.m * width + width / 2), (int) Math.round(s.n * height + height / 2)));
        size = s.k;
        Bwidth = s.w;
        isXOR = s.isXOR;
        isWithImpacts = s.isImpacts;
        myImage = bufferedImage;
        if (reset)
            field = new Field(s.n, s.m, s.k);
        else
            field.resize(mysettings, s);
        field.setSettings(s.lifeSettings);
        mysettings = s;
        Graphics g = myImage.getGraphics();
        g.setColor(Color.BLACK);
        drawField(g, s.m, s.n);
        repaint();
    }

    public HexaCell getHexa(int x, int y) {
        double mindistance = size, distance;
        HexaCell currCell = null;
        for (HexaCell hc : field.field) {
            distance = Point2D.distance(x, y, hc.getXcenter(), hc.getYcenter());
            if (distance <= mindistance) {
                mindistance = distance;
                currCell = hc;
            }
        }
        return currCell;
    }

    public void changeXORstate() {
        isXOR = !isXOR;
    }

    public void run(Settings s) {
        timeToRevive = false;
        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run() {
                step();
                redraw(s, false);
                if (field.AA == 0) {
                    timeToRevive = true;
                    cancel();
                }
            }
        };
        myTimer = new Timer();
        myTimer.scheduleAtFixedRate(repeatedTask, 1000L, 1000L);
    }

    public void pause() {
        myTimer.cancel();
        timeToRevive = true;
    }

    public void readFromFile(File f) {
        try {
            Scanner scanner = new Scanner(f);
            String s = scanner.nextLine();
            String[] retval;
            int nm = 25, nn = 25, nw = 1, nk = 20, amount = 0;
            while (s.startsWith("//"))
                s = scanner.nextLine();
            if (!s.startsWith("//")) {
                retval = s.split(" ");
                nm = Integer.parseInt(retval[0]);
                if (!retval[1].contains("//"))
                    nn = Integer.parseInt(retval[1]);
                else{
                    int beg = retval[1].indexOf("//");
                    String nAsAString = retval[1].substring(0,beg);
                    nn = Integer.parseInt(nAsAString);
                }
            }
            s = scanner.nextLine();
            while (s.startsWith("//"))
                s = scanner.nextLine();
            if (!s.startsWith("//")) {
                retval = s.split("//| ");
                nw = Integer.parseInt(retval[0]);
            }
            s = scanner.nextLine();
            while (s.startsWith("//"))
                s = scanner.nextLine();
            if (!s.startsWith("//")) {
                retval = s.split("//| ");
                nk = Integer.parseInt(retval[0]);
            }
            s = scanner.nextLine();
            while (s.startsWith("//"))
                s = scanner.nextLine();
            if (!s.startsWith("//")) {
                retval = s.split("//| ");
                amount = Integer.parseInt(retval[0]);
            }
            Settings newSettings = new Settings(nm, nn, nw, nk, false);
            redraw(newSettings, true);
            while (scanner.hasNextLine()) {
                s = scanner.nextLine();
                if (!s.startsWith("//")) {
                    retval = s.split(" ");
                    int nnn, mmm;
                    mmm = Integer.parseInt(retval[0]);
                    if (retval[1].contains("//")){
                        int beg = retval[1].indexOf("//");
                        String nAsAString = retval[1].substring(0,beg);
                        nnn = Integer.parseInt(nAsAString);
                    }
                    else
                        nnn = Integer.parseInt(retval[1]);
                    field.changeState(field.getCell(nnn, mmm,
                            field.field, mysettings.m, mysettings.n));
                    --amount;
                }
            }
            if(amount > 0){
                throw new ArrayIndexOutOfBoundsException();
            }
            redraw(mysettings, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile(File f) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(mcount + " " + ncount);
            bw.newLine();
            bw.write(String.valueOf(Bwidth));
            bw.newLine();
            bw.write(String.valueOf(size));
            bw.newLine();
            bw.write(String.valueOf(field.AA));
            bw.newLine();
            for (HexaCell hc : field.field) {
                if (hc.isAlive()) {
                    bw.write(hc.getCol() + " " + hc.getRow());
                    bw.newLine();
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void step() {
        field.step();
    }

    public boolean getTimeToRevive() {
        return timeToRevive;
    }

    public class MyPoint {
        double x;
        double y;

        MyPoint(double a, double b) {
            x = a;
            y = b;
        }
    }

    class Span {
        Point a;
        Point b;

        Span(Point x, Point y) {
            a = x;
            b = y;
        }

        int length() {
            return (int) (b.x - a.x);
        }
    }
}
