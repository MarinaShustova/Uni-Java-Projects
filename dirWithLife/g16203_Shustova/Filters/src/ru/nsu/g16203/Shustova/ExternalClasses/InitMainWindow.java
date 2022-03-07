package ru.nsu.g16203.Shustova.ExternalClasses;

import ru.nsu.g16203.Shustova.MyClasses.Dialog;
import ru.nsu.g16203.Shustova.MyClasses.MyInitView;
import ru.nsu.g16203.Shustova.MyClasses.Settings;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

/**
 * Main window class
 *
 * @author Vasya Pupkin
 * @co-author Marina Shustova
 */
public class InitMainWindow extends MainFrame {
    private static MyInitView myView;
    private Settings currentSettings = new Settings();
    private boolean canApply = false, isLoaded = false;

    public InitMainWindow() {
        super(1200, 500, "Filters");

        try {
            addSubMenu("File", KeyEvent.VK_A);
            addMenuItem("File/Load", "Load image", KeyEvent.VK_B, "Upload.png", "onLoad");
            addSubMenu("Help", KeyEvent.VK_C);
            addMenuItem("Help/About...", "Shows program version and copyright information", KeyEvent.VK_D, "About.png", "onAbout");
            addMenuItem("File/Select", "Select area", KeyEvent.VK_E, "Choose.png", "onSelect");
            addMenuItem("File/TranslateBtoC", "Copy selected area to zone C", KeyEvent.VK_F, "BtoC.png", "onTranslateBC");
            addMenuItem("File/TranslateCtoB", "Copy area from zone C back to B", KeyEvent.VK_G, "CtoB.png", "onTranslateCB");
            addSubMenu("Filter", KeyEvent.VK_H);
            addMenuItem("Filter/BlackAndWhite", "Black-and-White", KeyEvent.VK_I, "BlackAndWhite.png", "onBlackAndWhite");
            addMenuItem("Filter/Negative", "Negative", KeyEvent.VK_J, "Negative.png", "onNegative");
            addMenuItem("Filter/Dither", "Floyd-Steinberg dither", KeyEvent.VK_K, "Dither.png", "onDither");
            addMenuItem("Filter/OrderDither", "Ordered dither", KeyEvent.VK_L, "OrderDither.png", "onOrderDither");
            addMenuItem("Filter/Zoom", "Zoom x2", KeyEvent.VK_M, "Zoom.png", "onZoom");
            addMenuItem("Filter/Roberts", "Robert's edges", KeyEvent.VK_N, "Roberts.png", "onRoberts");
            addMenuItem("Filter/Sobel", "Sobel's edges", KeyEvent.VK_O, "Sobel.png", "onSobel");
            addMenuItem("Filter/Sharpen", "Sharp edges", KeyEvent.VK_P, "Sharpen.png", "onSharpen");
            addMenuItem("Filter/Blur", "Blur image", KeyEvent.VK_Q, "Blur.png", "onBlur");
            addMenuItem("Filter/Stamping", "Stamp image", KeyEvent.VK_R, "Stamping.png", "onStamp");
            addMenuItem("Filter/Watercolor", "Watercolor effect", KeyEvent.VK_S, "Watercolor.png", "onWatercolor");
            addMenuItem("Filter/Gamma", "Gamma correction", KeyEvent.VK_T, "Gamma.png", "onGamma");
            addMenuItem("Filter/Rotate", "Rotate image", KeyEvent.VK_U, "Rotate.png", "onRotate");
            addMenuItem("File/Save", "Saves image from zone C to file", KeyEvent.VK_V, "Save.png", "onSave");
            addMenuItem("File/Reset", "Clear all zones", KeyEvent.VK_W, "Reset.png", "onReset");
            addMenuItem("Filter/Rendering", "Start volume rendering", KeyEvent.VK_X, "Render.png", "onRender");
            addMenuItem("File/Config", "Read config file", KeyEvent.VK_Y, "Config.png", "onConfig");
            addMenuItem("Filter/Absorbtion", "Enable/disable absorbtion", KeyEvent.VK_Z, "Absorbtion.png", "onAbs");
            addMenuItem("Filter/Emission", "Enable/disable emission", KeyEvent.VK_Z, "Emission.png", "onEmis");


            addToolBarButton("File/Load", false);
            addToolBarSeparator();
            addToolBarButton("File/Select", true);
            addToolBarButton("File/TranslateBtoC", false);
            addToolBarButton("File/TranslateCtoB", false);
            addToolBarButton("File/Save", false);
            addToolBarSeparator();
            addToolBarButton("File/Reset", false);
            addToolBarSeparator();
            addToolBarButton("Filter/BlackAndWhite", false);
            addToolBarButton("Filter/Negative", false);
            addToolBarButton("Filter/Dither", false);
            addToolBarButton("Filter/OrderDither", false);
            addToolBarButton("Filter/Zoom", false);
            addToolBarButton("Filter/Roberts", false);
            addToolBarButton("Filter/Sobel", false);
            addToolBarButton("Filter/Sharpen", false);
            addToolBarButton("Filter/Blur", false);
            addToolBarButton("Filter/Stamping", false);
            addToolBarButton("Filter/Watercolor", false);
            addToolBarButton("Filter/Gamma", false);
            addToolBarButton("Filter/Rotate", false);
            addToolBarSeparator();
            addToolBarButton("File/Config", false);
            addToolBarButton("Filter/Absorbtion", true);
            addToolBarButton("Filter/Emission", true);
            addToolBarButton("Filter/Rendering", false);
            addToolBarSeparator();
            addToolBarButton("Help/About...", false);

            myView = new MyInitView();
            JScrollPane scroll = new JScrollPane(myView);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scroll.setPreferredSize(new Dimension(1200, 550));
            scroll.setViewportView(myView);
            add(scroll);
            pack();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onConfig() {
        try {
            File f = getOpenFileName("txt", "Text files");
            myView.prepareToRender(f);
            setSelected();
        } catch (Exception e) {
        }
    }

    public void onBlackAndWhite() {
        myView.filter(0, currentSettings);
    }

    public void onNegative() {
        myView.filter(1, currentSettings);
    }

    public void onDither() {
        if (canApply) {
            Dialog settingsDialog = new Dialog(this, 0, currentSettings);
            settingsDialog.setLocationRelativeTo(this);
            settingsDialog.setVisible(true);
            if (settingsDialog.isOk)
                myView.filter(2, currentSettings);
        }
    }

    public void onOrderDither() {
        myView.filter(3, currentSettings);
    }

    public void onZoom() {
        myView.filter(4, currentSettings);
    }

    public void onRoberts() {
        if (canApply) {
            Dialog settingsDialog = new Dialog(this, 1, currentSettings);
            settingsDialog.setLocationRelativeTo(this);
            settingsDialog.setVisible(true);
            if (settingsDialog.isOk)
                myView.filter(5, currentSettings);
        }
    }

    public void onSobel() {
        if (canApply) {
            Dialog settingsDialog = new Dialog(this, 2, currentSettings);
            settingsDialog.setLocationRelativeTo(this);
            settingsDialog.setVisible(true);
            if (settingsDialog.isOk)
                myView.filter(6, currentSettings);
        }
    }

    public void onSharpen() {
        myView.filter(7, currentSettings);
    }

    public void onBlur() {
        myView.filter(8, currentSettings);
    }

    public void onStamp() {
        myView.filter(9, currentSettings);
    }

    public void onWatercolor() {
        myView.filter(10, currentSettings);
    }

    public void onGamma() {
        if (canApply) {
            Dialog settingsDialog = new Dialog(this, 3, currentSettings);
            settingsDialog.setLocationRelativeTo(this);
            settingsDialog.setVisible(true);
            if (settingsDialog.isOk)
                myView.filter(11, currentSettings);
        }
    }

    public void onRotate() {
        if (canApply) {
            Dialog settingsDialog = new Dialog(this, 4, currentSettings);
            settingsDialog.setLocationRelativeTo(this);
            settingsDialog.setVisible(true);
            if (settingsDialog.isOk)
                myView.filter(12, currentSettings);
        }
    }

    public void onRender() {
        if (canApply) {
            Dialog settingsDialog = new Dialog(this, 5, currentSettings);
            settingsDialog.setLocationRelativeTo(this);
            settingsDialog.setVisible(true);
            if (settingsDialog.isOk)
                myView.filter(13, currentSettings);
        }
    }

    public void onAbs() {
        currentSettings.isAbs = !currentSettings.isAbs;
    }

    public void onEmis() {
        currentSettings.isEmis = !currentSettings.isEmis;
    }

    public void onSelect() {
        if (isLoaded) {
            canApply = true;
            myView.setSelection();
            myView.select();
        }
    }

    public void onTranslateBC() {
        myView.translate(true);
    }

    public void onTranslateCB() {
        myView.translate(false);
    }

    public void onLoad() {
        try {
            File f = getOpenFileName("bmp", "Images");
            myView.reset();
            myView.upload(f);
            isLoaded = true;
            canApply = false;
        } catch (Exception e) {
            myView.sync();
        }
    }

    public void onSave() {
        File f = getSaveFileName("bmp", "Images");
        try {
            myView.save(f);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to save image.", "", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {/**/ }
    }

    public void onAbout() {
        JOptionPane.showMessageDialog(this, "Filters, version 1.0\nCopyright" +
                        " © 2019 Marina Shustova, FIT, group 16203",
                "About Filters", JOptionPane.INFORMATION_MESSAGE);
    }

    public void onReset() {
        myView.resetAll();
        canApply = false;
        isLoaded = false;
    }

    public void onExit() {
        System.exit(0);
    }

    public static void main(String[] args) {
        InitMainWindow mainFrame = new InitMainWindow();
        mainFrame.setVisible(true);
    }
}
