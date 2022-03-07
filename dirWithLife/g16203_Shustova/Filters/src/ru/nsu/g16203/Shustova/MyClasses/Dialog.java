package ru.nsu.g16203.Shustova.MyClasses;

import javax.swing.*;
import java.awt.*;

public class Dialog extends JDialog {
    private Settings mySettings;
    private int DITHER_TYPE = 0, SOBEL_TYPE = 2, ROBERTS_TYPE = 1, GAMMA_TYPE = 3, ROTATION_TYPE = 4, RENDERING_TYPE = 5;
    public boolean isOk = false;

    public Dialog(JFrame owner, int type, Settings s) {
        super(owner, "Settings", true);
        mySettings = s;
        JPanel settingsPanel = null;
        JTextField DataField1 = null;
        JTextField DataField2 = null;
        JTextField DataField3 = null;
        JSlider slider = null;
        if (type == DITHER_TYPE) {
            settingsPanel = new JPanel(new GridLayout(3, 2, 0, 0));
            DataField1 = new JTextField(String.valueOf(mySettings.Nr), 5);
            DataField2 = new JTextField(String.valueOf(mySettings.Ng), 5);
            DataField3 = new JTextField(String.valueOf(mySettings.Nb), 5);
            JLabel r = new JLabel("Nr(>=0):");
            JLabel g = new JLabel("Ng(>=0):");
            JLabel b = new JLabel("Nb(>=0):");
            settingsPanel.add(r);
            settingsPanel.add(DataField1);
            settingsPanel.add(g);
            settingsPanel.add(DataField2);
            settingsPanel.add(b);
            settingsPanel.add(DataField3);
        }
        if (type == ROBERTS_TYPE) {
            settingsPanel = new JPanel();
            DataField1 = new JTextField(String.valueOf(mySettings.Roberts), 5);
            slider = new JSlider(JSlider.HORIZONTAL, 0, 200, mySettings.Roberts);
            JTextField finalDataField = DataField1;
            JSlider finalSlider = slider;
            DataField1.addActionListener(e -> {
                try {
                    finalSlider.setValue(Integer.parseInt(finalDataField.getText()));
                } catch (NumberFormatException ex1) {
                    JOptionPane.showMessageDialog(this, "Only numbers are allowed here.");
                    finalDataField.setText("" + finalSlider.getValue());
                }
            });
            slider.setMinorTickSpacing(10);
            slider.setMajorTickSpacing(50);
            slider.setPaintTicks(true);
            slider.setPaintLabels(false);
            slider.setLabelTable(slider.createStandardLabels(25));
            slider.addChangeListener(e -> {
                finalDataField.setText("" + finalSlider.getValue());
            });
            settingsPanel.add(slider);
            settingsPanel.add(DataField1);
        }
        if (type == SOBEL_TYPE) {
            settingsPanel = new JPanel();
            DataField1 = new JTextField(String.valueOf(mySettings.Sobel), 5);
            slider = new JSlider(JSlider.HORIZONTAL, 0, 1000, mySettings.Sobel);
            JTextField finalDataField1 = DataField1;
            JSlider finalSlider1 = slider;
            DataField1.addActionListener(e -> {
                try {
                    finalSlider1.setValue(Integer.parseInt(finalDataField1.getText()));
                } catch (NumberFormatException ex1) {
                    JOptionPane.showMessageDialog(this, "Only numbers are allowed here.");
                    finalDataField1.setText("" + finalSlider1.getValue());
                }
            });
            slider.setMinorTickSpacing(25);
            slider.setMajorTickSpacing(50);
            slider.setPaintTicks(true);
            slider.setPaintLabels(false);
            slider.setLabelTable(slider.createStandardLabels(50));
            slider.addChangeListener(e -> {
                finalDataField1.setText("" + finalSlider1.getValue());
            });
            settingsPanel.add(slider);
            settingsPanel.add(DataField1);
        }
        if (type == GAMMA_TYPE) {
            settingsPanel = new JPanel();
            DataField1 = new JTextField(String.valueOf(mySettings.gamma), 5);
            JLabel r = new JLabel("Gamma (0-10):");
            JTextField finalDataField2 = DataField1;
            settingsPanel.add(r);
            settingsPanel.add(DataField1);
        }
        if (type == ROTATION_TYPE) {
            settingsPanel = new JPanel();
            DataField1 = new JTextField(String.valueOf(mySettings.angle), 5);
            slider = new JSlider(JSlider.HORIZONTAL, -180, 180, mySettings.angle);
            JTextField finalDataField3 = DataField1;
            JSlider finalSlider3 = slider;
            DataField1.addActionListener(e -> {
                try {
                    finalSlider3.setValue(Integer.parseInt(finalDataField3.getText()));
                } catch (NumberFormatException ex1) {
                    JOptionPane.showMessageDialog(this, "Only numbers are allowed here.");
                    finalDataField3.setText("" + finalSlider3.getValue());
                }
            });
            slider.setMinorTickSpacing(10);
            slider.setMajorTickSpacing(50);
            slider.setPaintTicks(true);
            slider.setPaintLabels(false);
            slider.setLabelTable(slider.createStandardLabels(10));
            slider.addChangeListener(e -> {
                finalDataField3.setText("" + finalSlider3.getValue());
            });
            settingsPanel.add(slider);
            settingsPanel.add(DataField1);
        }
        if (type == RENDERING_TYPE) {
            settingsPanel = new JPanel(new GridLayout(3, 2, 0, 0));
            DataField1 = new JTextField(String.valueOf(mySettings.Nx), 5);
            DataField2 = new JTextField(String.valueOf(mySettings.Ny), 5);
            DataField3 = new JTextField(String.valueOf(mySettings.Nz), 5);
            JLabel r = new JLabel("Nx(1-350):");
            JLabel g = new JLabel("Ny(1-350):");
            JLabel b = new JLabel("Nz(1-350):");
            settingsPanel.add(r);
            settingsPanel.add(DataField1);
            settingsPanel.add(g);
            settingsPanel.add(DataField2);
            settingsPanel.add(b);
            settingsPanel.add(DataField3);
        }

        JButton ok = new JButton("OK");
        JTextField finalDataField4 = DataField1;
        JTextField finalDataField5 = DataField2;
        JTextField finalDataField6 = DataField3;
        JSlider finalSlider4 = slider;
        ok.addActionListener(event -> {
            boolean inRange = true;
            isOk = true;
            try {
                switch (type) {
                    case 0:
                        int temp = Integer.parseInt(finalDataField4.getText());
                        if (temp >= 0)
                            s.Nr = temp;
                        else
                            inRange = false;
                        temp = Integer.parseInt(finalDataField5.getText());
                        if (temp >= 0)
                            s.Ng = temp;
                        else
                            inRange = false;
                        temp = Integer.parseInt(finalDataField6.getText());
                        if (temp >= 0)
                            s.Nb = temp;
                        else
                            inRange = false;
                        break;
                    case 1:
                        s.Roberts = finalSlider4.getValue();
                        break;
                    case 2:
                        s.Sobel = finalSlider4.getValue();
                        break;
                    case 3:
                        double temp2 = Double.parseDouble(finalDataField4.getText());
                        if (temp2 <= 10.0 && temp2 >=0)
                            s.gamma = temp2;
                        else
                            inRange = false;
                        break;
                    case 4:
                        s.angle = finalSlider4.getValue();
                        break;
                    case 5:
                        int temp3 = Integer.parseInt(finalDataField4.getText());
                        if (temp3 >= 1 && temp3 <= 350)
                            s.Nx = temp3;
                        else
                            inRange = false;
                        temp3 = Integer.parseInt(finalDataField5.getText());
                        if (temp3 >= 1 && temp3 <= 350)
                            s.Ny = temp3;
                        else
                            inRange = false;
                        temp3 = Integer.parseInt(finalDataField6.getText());
                        if (temp3 >= 1 && temp3 <= 350)
                            s.Nz = temp3;
                        else
                            inRange = false;
                        break;
                }
                if (!inRange){
                    JOptionPane.showMessageDialog(this, "Value is out of range." +
                            "\n Please check again.");
                    isOk = false;
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "You've got a letter in one of the " +
                        "numeric fields.\n Please check them again");
            }
            setVisible(false);
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> {
            isOk = false;
            setVisible(false);
        });

        JPanel confirmPanel = new JPanel();
        confirmPanel.add(ok);
        confirmPanel.add(cancel);

        if (settingsPanel != null) {
            add(confirmPanel, BorderLayout.AFTER_LAST_LINE);
            add(settingsPanel, BorderLayout.CENTER);
            pack();
        }
    }

    public Settings getMySettings() {
        return mySettings;
    }
}
