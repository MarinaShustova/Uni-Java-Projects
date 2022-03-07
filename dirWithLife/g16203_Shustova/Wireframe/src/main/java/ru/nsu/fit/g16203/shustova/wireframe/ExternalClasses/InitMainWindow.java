package ru.nsu.fit.g16203.shustova.wireframe.ExternalClasses;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.*;

import ru.nsu.fit.g16203.shustova.wireframe.MyClasses.Logic.Controller;
import ru.nsu.fit.g16203.shustova.wireframe.MyClasses.View.FigureDialog;
import ru.nsu.fit.g16203.shustova.wireframe.MyClasses.View.MyInitView;
import ru.nsu.fit.g16203.shustova.wireframe.MyClasses.View.SplineRedactor;

/**
 * Main window class
 *
 * @author Vasya Pupkin
 * @co-author Marina Shustova
 */
public class InitMainWindow extends MainFrame {
    private MyInitView myView;
    private Controller controller;

    /**
     * Default constructor to create main window
     */
    public InitMainWindow() {
        super(700, 700, "Wireframe");
        try {
            addSubMenu("File", KeyEvent.VK_A);
            addMenuItem("File/Add", "Add new B-spline", KeyEvent.VK_B, "Add.png", "onAdd");
            addMenuItem("File/Reset", "Reset rotations", KeyEvent.VK_B, "Reset.png", "onReset");
            addMenuItem("File/Settings", "Scene settings", KeyEvent.VK_C, "Settings.png", "onSettings");
            addMenuItem("File/Open", "Open", KeyEvent.VK_D, "Open.png", "onOpen");
            addMenuItem("File/Save", "Save", KeyEvent.VK_E, "Save.png", "onSave");
            addMenuItem("File/Exit", "Exit and close application", KeyEvent.VK_X, "Exit.png", "onExit");
            addSubMenu("Help", KeyEvent.VK_I);
            addMenuItem("Help/About...", "Shows program version and copyright information", KeyEvent.VK_J, "Info.png", "onAbout");

            addToolBarButton("File/Open", false, false);
            addToolBarButton("File/Save", false, false);
            addToolBarSeparator();
            addToolBarButton("File/Reset", false, false);
            addToolBarSeparator();
            addToolBarButton("File/Add", false, false);
            addToolBarButton("File/Settings", false, false);
            addToolBarSeparator();
            addToolBarButton("Help/About...", false, false);
            addToolBarButton("File/Exit", false, false);

            myView = new MyInitView();
            controller = new Controller(myView);
//            JScrollPane scroll = new JScrollPane(myView);
//            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//            scroll.setPreferredSize(new Dimension(710, 710));
//            scroll.setViewportView(myView);
//            add(scroll);
            add(myView);
            pack();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onAdd() {
        SplineRedactor splineRedactor = new SplineRedactor(this, controller);
        splineRedactor.setLocationRelativeTo(this);
        splineRedactor.setVisible(true);
    }

    public void onSettings() {
        if (controller.figures.size() > 0) {
            FigureDialog figureDialog = new FigureDialog(this, controller);
            figureDialog.setLocationRelativeTo(this);
            figureDialog.setVisible(true);
        }
    }

    public void onAbout() {
        JOptionPane.showMessageDialog(this, "Wireframe, version 1.0\nCopyright � 2019 Marina Shustova, FIT, group 16203", "About author", JOptionPane.INFORMATION_MESSAGE);
    }

    public void onReset(){
        controller.reset();
    }

    public void onOpen() {
        try {
            File f = getOpenFileName("txt", "Text files");
            controller.readConfigFile(f);
        } catch (Exception e) {
        }
    }

    public void onSave(){
        try {
            File f = getSaveFileName("txt", "Text files");
            controller.writeConfigFile(f);
        }catch (Exception e){

        }
//        myView.saveToFile(f);
    }

    public void onExit() {
        System.exit(0);
    }
}
