package ru.nsu.fit.g16203.shustova.isolines.MyClasses.View;

import ru.nsu.fit.g16203.shustova.isolines.MyClasses.Logic.Settings;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Dialog extends JDialog {
    private Settings mySettings;
    public boolean isOk = false;

    public Dialog(JFrame owner, Settings s) {
        super(owner, "Settings", true);
        mySettings = s;
        Color color = s.getIsoColor();
        JPanel settingsPanel = new JPanel(new GridLayout(6, 2, 0, 0));
        BigDecimal bigDecimal = new BigDecimal(mySettings.getA());
        bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
        JTextField a = new JTextField(String.valueOf(bigDecimal.doubleValue()), 5);
        bigDecimal = new BigDecimal(mySettings.getB());
        bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
        JTextField b = new JTextField(String.valueOf(bigDecimal.doubleValue()), 5);
        bigDecimal = new BigDecimal(mySettings.getC());
        bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
        JTextField c = new JTextField(String.valueOf(bigDecimal.doubleValue()), 5);
        bigDecimal = new BigDecimal(mySettings.getD());
        bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
        JTextField d = new JTextField(String.valueOf(bigDecimal.doubleValue()), 5);
        JTextField k = new JTextField(String.valueOf(mySettings.getK()), 5);
        JTextField m = new JTextField(String.valueOf(mySettings.getM()), 5);
        JLabel aLabel = new JLabel("a:");
        JLabel bLabel = new JLabel("b:");
        JLabel cLabel = new JLabel("c:");
        JLabel dLabel = new JLabel("d:");
        JLabel kLabel = new JLabel("k:");
        JLabel mLabel = new JLabel("m:");
        settingsPanel.add(aLabel);
        settingsPanel.add(a);
        settingsPanel.add(bLabel);
        settingsPanel.add(b);
        settingsPanel.add(cLabel);
        settingsPanel.add(c);
        settingsPanel.add(dLabel);
        settingsPanel.add(d);
        settingsPanel.add(kLabel);
        settingsPanel.add(k);
        settingsPanel.add(mLabel);
        settingsPanel.add(m);

        JButton ok = new JButton("OK");
        ok.addActionListener(event -> {
            isOk = true;
            try {
                double na = Double.parseDouble(a.getText());
                double nb = Double.parseDouble(b.getText());
                double nc = Double.parseDouble(c.getText());
                double nd = Double.parseDouble(d.getText());
                int nm = Integer.parseInt(m.getText());
                int nk = Integer.parseInt(k.getText());
                mySettings = new Settings(na, nb, nc, nd, nm, nk, color);
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
        add(confirmPanel, BorderLayout.AFTER_LAST_LINE);
        add(settingsPanel, BorderLayout.CENTER);
        pack();
    }

    public Settings getMySettings() {
        return mySettings;
    }
}

