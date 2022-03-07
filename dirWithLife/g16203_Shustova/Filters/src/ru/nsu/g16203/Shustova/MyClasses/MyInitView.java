package ru.nsu.g16203.Shustova.MyClasses;


import ru.nsu.g16203.Shustova.MyClasses.Filters.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class MyInitView extends JPanel {
    private ZoneA zoneA;
    private ZoneB zoneB;
    private ZoneC zoneC;
    private GraphZone abs, emis;
    private Volume volume;

    public MyInitView() {
        super();
        setPreferredSize(new Dimension(1090, 370));
        zoneA = new ZoneA();
        zoneB = new ZoneB();
        zoneC = new ZoneC();
        abs = new GraphZone();
        emis = new GraphZone();
        add(zoneA);
        add(zoneB);
        add(zoneC);
        add(abs);
        add(emis);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            }
        });
    }

    public void reset() {
        zoneA.reset();
    }

    public void upload(File f) {
        zoneA.uploadImage(f);
    }

    public void select() {
        zoneA.setMyzoneB(zoneB);
    }

    public void sync() {
        zoneA.synchronise();
    }

    public void save(File f) throws IOException {
        zoneC.save(f);
    }

    public void resetAll() {
        zoneA.clear();
        zoneB.clear();
        zoneC.clear();
    }

    public void setSelection() {
        zoneA.isSelection = !zoneA.isSelection;
    }

    public void translate(boolean BtoC) {
        if (BtoC) {
            zoneB.setMyZoneC(zoneC);
            zoneB.translate();
        } else {
            zoneC.setMyzoneB(zoneB);
            zoneC.translate();
        }
    }

    public void filter(int filterNumber, Settings settings) {
        Filter filter;
        switch (filterNumber) {
            case 0:
                translate(true);
                zoneC.setMyFilter(new BlackAndWhite());
                break;
            case 1:
                translate(true);
                zoneC.setMyFilter(new Negative());
                break;
            case 2:
                translate(true);
                filter = new Dither();
                ((Dither) filter).setParams(settings.Nr, settings.Ng, settings.Nb);
                zoneC.setMyFilter(filter);
                break;
            case 3:
                translate(true);
                zoneC.setMyFilter(new OrderedDither());
                break;
            case 4:
                translate(true);
                zoneC.setMyFilter(new Zoom());
                break;
            case 5:
                translate(true);
                filter = new Roberts();
                ((Roberts) filter).setC(settings.Roberts);
                zoneC.setMyFilter(filter);
                break;
            case 6:
                translate(true);
                filter = new Sobel();
                ((Sobel) filter).setC(settings.Sobel);
                zoneC.setMyFilter(filter);
                break;
            case 7:
                translate(true);
                zoneC.setMyFilter(new Sharpen());
                break;
            case 8:
                translate(true);
                zoneC.setMyFilter(new Blur());
                break;
            case 9:
                translate(true);
                zoneC.setMyFilter(new Stamping());
                break;
            case 10:
                translate(true);
                zoneC.setMyFilter(new Watercolor());
                break;
            case 11:
                translate(true);
                filter = new Gamma();
                ((Gamma) filter).setGamma(settings.gamma);
                zoneC.setMyFilter(filter);
                break;
            case 12:
                translate(true);
                filter = new Rotate();
                ((Rotate) filter).setAngle(settings.angle);
                zoneC.setMyFilter(filter);
                break;
            case 13:
                translate(true);
                filter = volume;
                ((Volume) filter).setParams(settings.Nx, settings.Ny, settings.Nz, settings.isAbs, settings.isEmis);
                zoneC.setMyFilter(filter);
                break;
        }

    }

    public void prepareToRender(File f) {
        volume = new Volume();
        volume.readConfigFile(f);
        abs.drawGraph(volume.getAbs());
        emis.drawGraph(volume.getEmis());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
