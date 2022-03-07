package ru.nsu.g16203.Shustova;

import ru.nsu.g16203.Shustova.logic.LifeSettings;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SettingsDialog extends JDialog {
    private Settings mySettings;// = new Settings(10, 10, 1, 20, false);
    private boolean isXor = false;
    public boolean isOk = false;

    public SettingsDialog(JFrame owner, Settings s) {
        super(owner, "Settings", true);

        ButtonGroup group = new ButtonGroup();
        JRadioButton replace = new JRadioButton("Replace", true);
        replace.addActionListener(e -> {
            isXor = false;
        });
        group.add(replace);
        JRadioButton xor = new JRadioButton("XOR", false);
        xor.addActionListener(e -> {
            isXor = true;
        });
        group.add(xor);

        JPanel settingsPanel = new JPanel(new GridLayout(2, 0, 0, 0));
        settingsPanel.setBorder(new CompoundBorder(new EmptyBorder(0, 10, 10, 10), new TitledBorder("")));

        JPanel insidePanel = new JPanel(new GridLayout(2, 0, 0, 0));
        JTextField mSize = new JTextField(String.valueOf(s.m), 5);
        JTextField nSize = new JTextField(String.valueOf(s.n), 5);
        JLabel m = new JLabel("M:");
        JLabel n = new JLabel("N:");
        JPanel mPanel = new JPanel();
        mPanel.add(m);
        mPanel.add(mSize);
        JPanel nPanel = new JPanel();
        nPanel.add(n);
        nPanel.add(nSize);
        insidePanel.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Field size")));
        insidePanel.add(mPanel);
        insidePanel.add(nPanel);
        JPanel insidePanel2 = new JPanel(new GridLayout(6, 2, 0, 0));
        JTextField birthbeg = new JTextField(String.valueOf(s.lifeSettings.getBIRTH_BEGIN()), 5);
        JTextField birthend = new JTextField(String.valueOf(s.lifeSettings.getBIRTH_END()), 5);
        JTextField lifebeg = new JTextField(String.valueOf(s.lifeSettings.getLIVE_BEGIN()), 5);
        JTextField lifeend = new JTextField(String.valueOf(s.lifeSettings.getLIVE_END()), 5);
        JTextField fstimp = new JTextField(String.valueOf(s.lifeSettings.getIMP1()), 5);
        JTextField sndimp = new JTextField(String.valueOf(s.lifeSettings.getIMP2()), 5);
        JLabel bb = new JLabel("Birth begin:");
        JLabel be = new JLabel("Birth end:");
        JLabel lb = new JLabel("Live begin:");
        JLabel le = new JLabel("Live end:");
        JLabel i1 = new JLabel("First impact:");
        JLabel i2 = new JLabel("Second impact:");
        insidePanel2.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Life params")));
        JPanel bbpanel = new JPanel();
        JPanel bepanel = new JPanel();
        JPanel lbpanel = new JPanel();
        JPanel lepanel = new JPanel();
        JPanel i1panel = new JPanel();
        JPanel i2panel = new JPanel();
        bbpanel.add(bb);
        bbpanel.add(birthbeg);
        bepanel.add(be);
        bepanel.add(birthend);
        lbpanel.add(lb);
        lbpanel.add(lifebeg);
        lepanel.add(le);
        lepanel.add(lifeend);
        i1panel.add(i1);
        i1panel.add(fstimp);
        i2panel.add(i2);
        i2panel.add(sndimp);
        insidePanel2.add(bbpanel);
        insidePanel2.add(bepanel);
        insidePanel2.add(lbpanel);
        insidePanel2.add(lepanel);
        insidePanel2.add(i1panel);
        insidePanel2.add(i2panel);

        settingsPanel.add(insidePanel);
        settingsPanel.add(insidePanel2);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(50, 30));
        textField.setText(String.valueOf(s.k));
        JSlider cellSize = new JSlider(JSlider.VERTICAL, 5, 50, s.k);
        textField.addActionListener(e -> {
            try {
                cellSize.setValue(Integer.parseInt(textField.getText()));
            }catch (NumberFormatException ex1){
                JOptionPane.showMessageDialog(this, "Only numbers are allowed here.");
                textField.setText("" + cellSize.getValue());
            }
        });
        cellSize.setMinorTickSpacing(5);
        cellSize.setMajorTickSpacing(10);
        cellSize.setPaintTicks(true);
        cellSize.setPaintLabels(true);
        cellSize.setLabelTable(cellSize.createStandardLabels(10));
        cellSize.addChangeListener(e -> {
            textField.setText("" + cellSize.getValue());
        });
        JPanel cellSizePanel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(40, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        gbl.setConstraints(textField, c);
        cellSizePanel.setLayout(gbl);
        cellSizePanel.add(cellSize);
        cellSizePanel.add(textField);
        cellSizePanel.setBorder(new CompoundBorder(new EmptyBorder(30, 20, 10, 0), new TitledBorder("Cellsize")));

        JTextField text = new JTextField();
        text.setText(String.valueOf(s.w));
        text.setPreferredSize(new Dimension(50, 30));
        JSlider linewidth = new JSlider(JSlider.VERTICAL, 1, 8, s.w);
        text.addActionListener(e -> {
            try {
                linewidth.setValue(Integer.parseInt(text.getText()));
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Only numbers are allowed here.");
                text.setText("" + linewidth.getValue());
            }
        });
        linewidth.setMajorTickSpacing(1);
        linewidth.setPaintTicks(true);
        linewidth.setPaintLabels(true);
        linewidth.setLabelTable(linewidth.createStandardLabels(1));
        linewidth.addChangeListener(e -> {
            text.setText("" + linewidth.getValue());
        });
        JPanel lineWidthPanel = new JPanel();
        GridBagLayout gbl2 = new GridBagLayout();
        GridBagConstraints c2 = new GridBagConstraints();
        c2.anchor = GridBagConstraints.NORTH;
        c2.fill = GridBagConstraints.NONE;
        c2.gridheight = 1;
        c2.gridwidth = 1;
        c2.gridx = 0;
        c2.gridy = 1;
        c2.insets = new Insets(40, 0, 0, 0);
        c2.ipadx = 0;
        c2.ipady = 0;
        c2.weightx = 0.0;
        c2.weighty = 0.0;
        gbl2.setConstraints(text, c2);
        lineWidthPanel.setLayout(gbl2);
        lineWidthPanel.add(linewidth);
        lineWidthPanel.add(text);
        lineWidthPanel.setBorder(new CompoundBorder(new EmptyBorder(30, 0, 10, 20), new TitledBorder("Linewidth")));

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout());
        radioPanel.add(replace);
        radioPanel.add(xor);
        radioPanel.setBorder(new CompoundBorder(new EmptyBorder(30, 20, 10, 20), new TitledBorder("Mode")));

        JButton ok = new JButton("OK");
        ok.addActionListener(event -> {
            isOk = true;
            try {
                LifeSettings life = new LifeSettings();
                life.setBIRTH_BEGIN(Double.parseDouble(birthbeg.getText()));
                life.setBIRTH_END(Double.parseDouble(birthend.getText()));
                life.setLIVE_BEGIN(Double.parseDouble(lifebeg.getText()));
                life.setLIVE_END(Double.parseDouble(lifeend.getText()));
                life.setIMP1(Double.parseDouble(fstimp.getText()));
                life.setIMP2(Double.parseDouble(sndimp.getText()));
                if (checkConditions(life)) {
                    mySettings = new Settings(Integer.parseInt(mSize.getText()), Integer.parseInt(nSize.getText()), linewidth.getValue(), cellSize.getValue(), isXor);
                    mySettings.setLifeSettings(life);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "One of conditions was violated:\n" +
                            "LIVE BEGIN <= BIRTH BEGIN <= BIRTH END <= LIVE END\n" +
                            "Please check them again");
                }
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, "You've got a letter in one of the " +
                        "numeric fields.\n Please check them again");
            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> {
            isOk = false;
            setVisible(false);
        });

        JPanel confirmPanel = new JPanel();
        confirmPanel.add(ok);
        confirmPanel.add(cancel);

        add(radioPanel, BorderLayout.NORTH);
        add(cellSizePanel, BorderLayout.WEST);
        add(lineWidthPanel, BorderLayout.EAST);
        add(confirmPanel, BorderLayout.AFTER_LAST_LINE);
        add(settingsPanel, BorderLayout.CENTER);
        setSize(500, 600);
    }

    private boolean checkConditions(LifeSettings lifeSettings) {
        if ((lifeSettings.getLIVE_BEGIN() <= lifeSettings.getBIRTH_BEGIN())
                && (lifeSettings.getBIRTH_BEGIN() <= lifeSettings.getBIRTH_END())
                && (lifeSettings.getBIRTH_END() <= lifeSettings.getLIVE_END()))
            return true;
        return false;
    }

    public Settings newSettings() {
        return mySettings;
    }
}
