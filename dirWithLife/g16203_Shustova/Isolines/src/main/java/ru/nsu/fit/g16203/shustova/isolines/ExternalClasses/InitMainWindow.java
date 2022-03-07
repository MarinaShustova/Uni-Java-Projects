package ru.nsu.fit.g16203.shustova.isolines.ExternalClasses;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.security.Key;

import javax.swing.*;

import ru.nsu.fit.g16203.shustova.isolines.MyClasses.Logic.Settings;
import ru.nsu.fit.g16203.shustova.isolines.MyClasses.View.Dialog;
import ru.nsu.fit.g16203.shustova.isolines.MyClasses.View.MyInitView;

/**
 * Main window class
 *
 * @author Vasya Pupkin
 * @co-author Marina Shustova
 */
public class InitMainWindow extends MainFrame {
    private MyInitView myView;
    private JLabel statusbar;
    private Settings currentSettings;


    /**
     * Default constructor to create main window
     */
    public InitMainWindow() {
        super(600, 400, "Init application");
        currentSettings = new Settings();
        try {
            addSubMenu("File", KeyEvent.VK_A);
            addMenuItem("File/Settings", "Set definition area, net parameters", KeyEvent.VK_B, "Settings.png", "onSettings");
            addMenuItem("File/Open", "Open", KeyEvent.VK_K, "Open.png", "onOpen");
            addMenuItem("File/Exit", "Exit and close application", KeyEvent.VK_X, "Exit.png", "onExit");
            addSubMenu("View", KeyEvent.VK_C);
            addMenuItem("View/Net", "Show net", KeyEvent.VK_D, "Net.png", "onNet");
            addMenuItem("View/Erase", "Erase user isolines", KeyEvent.VK_N, "Erase.png", "onErase");
            addMenuItem("View/Mode", "Enable/disable interpollation", KeyEvent.VK_E, "Palette2.png", "onInterpolate");
            addMenuItem("View/Custom", "Draw lines mode", KeyEvent.VK_F, "Custom.png", "onDraw");
            addMenuItem("View/Line", "Show isolines", KeyEvent.VK_G, "Line.png", "onShow");
            addMenuItem("View/Dots", "Show crossing", KeyEvent.VK_H, "Dot.png", "onDots");
            addSubMenu("Help", KeyEvent.VK_I);
            addMenuItem("Help/About...", "Shows program version and copyright information", KeyEvent.VK_J, "Info.png", "onAbout");

            addToolBarButton("File/Open", false, false);
            addToolBarSeparator();
            addToolBarButton("File/Settings", false, false);
            addToolBarSeparator();
            addToolBarButton("View/Mode", true, false);
            addToolBarButton("View/Net", true, true);
            addToolBarButton("View/Line", true, true);
            addToolBarButton("View/Dots", true, false);
            addToolBarButton("View/Custom", true, true);
            addToolBarButton("View/Erase", false, true);
            addToolBarSeparator();
            addToolBarButton("Help/About...", false, false);
            addToolBarButton("File/Exit", false, false);

            statusbar = new JLabel("Ready!");
            myView = new MyInitView(statusbar);
            add(myView);
            add(statusbar, BorderLayout.PAGE_END);
            pack();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onInterpolate(){
        myView.getTicksNlegend().getLegend().interpollation = !myView.getTicksNlegend().getLegend().interpollation;
        myView.getIsoMap().interpollation = !myView.getIsoMap().interpollation;
        myView.getTicksNlegend().getLegend().redraw(true);
        myView.getIsoMap().redraw(true);
    }

    public void onDraw(){
        myView.getIsoMap().drawLines = !myView.getIsoMap().drawLines;
    }

    public void onDots(){
        myView.getIsoMap().showDots = !myView.getIsoMap().showDots;
        myView.getIsoMap().redraw(true);
    }

    public void onErase(){
        myView.getIsoMap().dropLines();
    }

    public void onShow(){
        myView.getIsoMap().showLines = !myView.getIsoMap().showLines;
        myView.getIsoMap().redraw(true);
    }

    public void onNet(){
        myView.getIsoMap().showNet = !myView.getIsoMap().showNet;
        myView.getIsoMap().redraw(true);
    }

    public void onSettings() {
        Dialog settingsDialog = new Dialog(this, currentSettings);
        settingsDialog.setLocationRelativeTo(this);
        settingsDialog.setVisible(true);
        if (settingsDialog.isOk) {
            currentSettings = settingsDialog.getMySettings();
            myView.getIsoMap().setParams(currentSettings);
            myView.getIsoMap().redraw(true);
        }
    }

    public void onAbout() {
        JOptionPane.showMessageDialog(this, "Isolines, version 1.0\nCopyright � 2019 Marina Shustova, FIT, group 16203", "About author", JOptionPane.INFORMATION_MESSAGE);
    }

    public void onOpen(){
        try {
            File f = getOpenFileName("txt", "Text files");
            myView.readConfigFile(f);
            currentSettings = myView.getSettings();
            setUnpressed();
        } catch (Exception e) {
        }
    }

    private void setUnpressed(){
        super.toggleButtons.get(0).setSelected(false);
        super.toggleButtons.get(1).setSelected(true);
        super.toggleButtons.get(2).setSelected(true);
        super.toggleButtons.get(3).setSelected(false);
        super.toggleButtons.get(4).setSelected(true);
    }

    public void onExit() {
        System.exit(0);
    }
}
