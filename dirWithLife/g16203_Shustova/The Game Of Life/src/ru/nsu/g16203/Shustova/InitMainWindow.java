package ru.nsu.g16203.Shustova;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

import javax.swing.*;

public class InitMainWindow extends MainFrame {
    private boolean isRunning = false;
    private static InitView myView;
    private Settings currentSettings = new Settings(25, 25, 1, 20, false);
    private static boolean isSaved = true;

    public InitMainWindow() {
        super(800, 600, "Life");
        try {
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X, "Exit.png", "onExit");
            addMenuItem("File/New", "Create new file", KeyEvent.VK_N, "New.png", "onSettings");
            addMenuItem("File/Import", "Import field from file", KeyEvent.VK_I, "Import.png", "onOpen");
            addMenuItem("File/Save", "Save field to the current folder", KeyEvent.VK_S, "Save.png", "onSave");
            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About...", "Shows program version and copyright information", KeyEvent.VK_A, "About.png", "onAbout");
            addSubMenu("Options", KeyEvent.VK_O);
            addMenuItem("Options/Settings", "Edit settings", KeyEvent.VK_T, "Settings.png", "onSettings");
            addMenuItem("Options/XOR", "Enter the XOR mode", KeyEvent.VK_R, "XOR.png", "onXOR");
            addMenuItem("Options/Replace", "Enter the Replace mode", KeyEvent.VK_P, "Replace.png", "onReplace");
            addMenuItem("Options/Play", "Start the evolution", KeyEvent.VK_L, "Play.png", "onRun");
            addMenuItem("Options/Pause", "Pause the game", KeyEvent.VK_U, "Pause.png", "onPause");
            addMenuItem("Options/Reset", "Clear the field", KeyEvent.VK_M, "Reset.png", "onReset");
            addMenuItem("Options/Next", "Next", KeyEvent.VK_E, "Next.png", "onStep");
            addMenuItem("Options/Show Impacts", "Show impacts", KeyEvent.VK_I, "Life.png", "onImpacts");


            addToolBarButton("File/New", false);
            addToolBarButton("File/Import", false);
            addToolBarButton("File/Save", false);
            addToolBarSeparator();
            addToolBarButton("Options/Next", false);
            addToolBarButton("Options/Play", true);
            addToolBarButton("Options/Pause", false);
            addToolBarButton("Options/Reset", false);
            addToolBarButton("Options/Show Impacts", true);
            addToolBarSeparator();
            addToolBarButton("Options/Replace", true);
            addToolBarButton("Options/XOR", true);
            addToolBarSeparator();
            addToolBarButton("Options/Settings", false);
            addToolBarSeparator();
            addToolBarButton("Help/About...", false);

            linkToggleButtons();
            setSpecialLinks();

            myView = new InitView();
            JScrollPane scroll = new JScrollPane(myView);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scroll.setPreferredSize(new Dimension(800, 600));
            scroll.setViewportView(myView);
            add(scroll);
            pack();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onOpen() {
        File f = getOpenFileName("txt", "Text files");
        try {
            myView.readFromFile(f);
            curse();
            currentSettings = myView.getMysettings();
            isSaved = true;
        }catch (ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(this, "Total number of living cells is not equal to the given");
        }
    }

    public void onSave() {
        File f = getSaveFileName("txt", "Text files");
        myView.saveToFile(f);
        isSaved = true;
        myView.isSaved = true;
    }

    public void onAbout() {
        JOptionPane.showMessageDialog(this, "Init, version 100500.1\nCopyright © 2019 Marina Shustova, FIT, group 16203", "About Init", JOptionPane.INFORMATION_MESSAGE);
    }

    public void onExit() {
        onSave();
        System.exit(0);
    }

    public void onSettings() {
        checkState();
        if (!isRunning) {
            SettingsDialog dialog = new SettingsDialog(this, currentSettings);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            if (dialog.isOk) {
                boolean oldImp = currentSettings.isImpacts;
                int oldM = currentSettings.m, oldn = currentSettings.n;
                currentSettings = dialog.newSettings();
                currentSettings.isImpacts = oldImp;
                myView.redraw(currentSettings, false);
                resetToggleButtons();
                magicSpell();
                isSaved = false;
            }
        }
    }

    public void onReset() {
        checkState();
        if (!isRunning) {
            myView.redraw(currentSettings, true);
            isSaved = false;
        }
    }

    public void onXOR() {
        checkState();
        if (!isRunning) {
            currentSettings.isXOR = true;
            myView.changeXORstate();
        }
    }

    public void onReplace() {
        checkState();
        if (!isRunning) {
            currentSettings.isXOR = false;
            myView.changeXORstate();
        }
    }

    public void onImpacts() {
        checkState();
        if (!isRunning) {
            currentSettings.isImpacts = !currentSettings.isImpacts;
            myView.redraw(currentSettings, false);
        }
    }

    public void onStep() {
        checkState();
        if (!isRunning) {
            myView.step();
            myView.redraw(currentSettings, false);
            isSaved = false;
        }
    }

    public void onRun() {
        isRunning = true;
        myView.run(currentSettings);
        isSaved = false;
    }

    private void checkState() {
        if (myView.getTimeToRevive()) {
            super.toggleButtons.get(0).setSelected(false);
            isRunning = false;
        }
    }

    public void onPause() {
        myView.pause();
    }

    private void setSpecialLinks() {
        magicSpell();
        super.simpleButtons.get(4).addActionListener(e -> {
            super.toggleButtons.get(0).setSelected(false);
        });
    }

    private void magicSpell() {
        super.toggleButtons.get(2).setSelected(true);
    }

    private void curse() {
        super.toggleButtons.get(1).setSelected(false);
    }

    public static void main(String[] args) {
        InitMainWindow mainFrame = new InitMainWindow();
        mainFrame.setMinimumSize(new Dimension(800, 600));
        mainFrame.setVisible(true);
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                if ((!isSaved)||(!myView.isSaved)) {
                    int confirm = JOptionPane.showOptionDialog(
                            null, "Do you want to save the field?",
                            "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm == 0) {
                        mainFrame.onSave();
                        System.exit(0);
                    }
                }
            }
        };
        mainFrame.addWindowListener(exitListener);
    }
}
