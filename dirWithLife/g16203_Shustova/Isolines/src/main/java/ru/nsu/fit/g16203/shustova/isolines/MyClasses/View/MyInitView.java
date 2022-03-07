package ru.nsu.fit.g16203.shustova.isolines.MyClasses.View;

import ru.nsu.fit.g16203.shustova.isolines.MyClasses.Logic.Function;
import ru.nsu.fit.g16203.shustova.isolines.MyClasses.Logic.Settings;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

public class MyInitView extends JPanel {
    private Map isoMap;
    private Ticks ticksNlegend;
    private Color[] colors = {
            new Color(255, 255, 51),
            new Color(153, 255, 51),
            new Color(51, 255, 51),
            new Color(51, 255, 153),
            new Color(51, 255, 255),
            new Color(51, 153, 255),
            new Color(51, 51, 255),
            new Color(153, 51, 255)
    };
    private int k, m, n, w = 800, h = 550;
    private ArrayList<Color> newPalette;
    private Color isoColor;
    private Function function;
    private JLabel status;
    private Settings settings;

    public MyInitView(JLabel statusbar) {
        super();
        status = statusbar;
        int NCOLORS = colors.length;
        function = new Function();
        setPreferredSize(new Dimension(800, 550));
        isoMap = new Map(NCOLORS, colors, function, statusbar);
        ticksNlegend = new Ticks(NCOLORS, colors, function);
        add(isoMap);
        add(ticksNlegend);
    }

    private void recreate() {
        int NCOLORS = colors.length;
        settings = new Settings();
        settings.setK(k);
        settings.setM(m);
        settings.setIsoColor(isoColor);
        remove(isoMap);
        remove(ticksNlegend);
        isoMap = new Map(NCOLORS, colors, function, status);
        isoMap.setParams(settings);
        ticksNlegend = new Ticks(NCOLORS, colors, function);
        add(isoMap);
        add(ticksNlegend);
        repaint();
        setBounds(0,0,800,551);
    }

    public Map getIsoMap() {
        return isoMap;
    }

    public Ticks getTicksNlegend() {
        return ticksNlegend;
    }

    public void readConfigFile(File f) {
        newPalette = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(f);
            String[] retval;
            String s;
            s = rewind(scanner);
            s = deleteComments(s);
            retval = s.split(" ");
            k = Integer.parseInt(retval[0]);
            m = Integer.parseInt(retval[1]);
            s = rewind(scanner);
            s = deleteComments(s);
            n = Integer.parseInt(s);
            colors = new Color[n];
            for (int i = 0; i < n; ++i) {
                s = rewind(scanner);
                s = deleteComments(s);
                retval = s.split(" ");
                colors[i] = new Color(Integer.parseInt(retval[0]), Integer.parseInt(retval[1]),
                        Integer.parseInt(retval[2]));
                newPalette.add(new Color(Integer.parseInt(retval[0]), Integer.parseInt(retval[1]),
                        Integer.parseInt(retval[2])));
            }
            s = rewind(scanner);
            s = deleteComments(s);
            retval = s.split(" ");
            isoColor = new Color(Integer.parseInt(retval[0]), Integer.parseInt(retval[1]),
                    Integer.parseInt(retval[2]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        recreate();
    }

    private String deleteComments(String source) {
        if (!source.contains("//"))
            return source;
        int start = source.indexOf("//");
        return source.substring(0, start).trim();
    }

    private String rewind(Scanner scanner) {
        String s = scanner.nextLine();
        while (s.startsWith("//") || s.isEmpty())
            s = scanner.nextLine();
        return s;
    }

    public Settings getSettings() {
        return settings;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            isoMap.setBounds(0, 0, 3 * width / 5, height - 25);
            isoMap.setPreferredSize(new Dimension(3 * width / 5, height - 25));
            ticksNlegend.setBounds(3 * width / 5, 0, width / 5, height - 25);
            ticksNlegend.setPreferredSize(new Dimension(width / 5, height - 25));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
