package ru.nsu.fit.g16203.shustova.wireframe.MyClasses.View;

import ru.nsu.fit.g16203.shustova.wireframe.MyClasses.Logic.BSpline;
import ru.nsu.fit.g16203.shustova.wireframe.MyClasses.Logic.Controller;

import javax.swing.*;
import java.awt.*;

public class SplineRedactor extends JDialog {
    private JPanel rootPanel;
    private JPanel bSplinePanel;
    private JSpinner a;
    private JSpinner m;
    private JSpinner zn;
    private JSpinner b;
    private JSpinner n;
    private JSpinner zf;
    private JSpinner c;
    private JSpinner k;
    private JSpinner sw;
    private JSpinner d;
    private JSpinner number;
    private JSpinner sh;
    private JSpinner R;
    private JSpinner G;
    private JSpinner B;
    private JButton OKButton;
    private JButton cancelButton;

    public SplineRedactor(JFrame owner, Controller controller) {
        super(owner, "Settings", false);
        $$$setupUI$$$();
        SpinnerModel aModel = new SpinnerNumberModel(controller.getA(), 0, 1, 0.1);
        final SpinnerModel[] bModel = {new SpinnerNumberModel(controller.getB(), 0, 1, 0.1)};
        SpinnerModel cModel = new SpinnerNumberModel(controller.getC(), 0, 2 * Math.PI, Math.PI / 4);
        SpinnerModel dModel = new SpinnerNumberModel(controller.getD(), 0, 2 * Math.PI, Math.PI / 4);
        SpinnerModel RModel = new SpinnerNumberModel(Color.CYAN.getRed(), 0, 255, 1);
        SpinnerModel GModel = new SpinnerNumberModel(Color.CYAN.getGreen(), 0, 255, 1);
        SpinnerModel BModel = new SpinnerNumberModel(Color.CYAN.getBlue(), 0, 255, 1);
        SpinnerModel numModel = new SpinnerNumberModel(controller.splines.size() + 1, 1, controller.splines.size() + 1, 1);
        SpinnerModel kModel = new SpinnerNumberModel(controller.getK(), 1, 10, 1);
        SpinnerModel mModel = new SpinnerNumberModel(controller.getM(), 3, 15, 1);
        SpinnerModel nModel = new SpinnerNumberModel(controller.getN(), 3, 15, 1);
        SpinnerModel swModel = new SpinnerNumberModel(controller.getSw(), 1, 500, 1);
        SpinnerModel shModel = new SpinnerNumberModel(controller.getSh(), 1, 500, 1);
        SpinnerModel znModel = new SpinnerNumberModel(controller.getZn(), -500, 500, 1);
        SpinnerModel zfModel = new SpinnerNumberModel(controller.getZf(), -500, 500, 1);
        a.setModel(aModel);
        b.setModel(bModel[0]);
        c.setModel(cModel);
        d.setModel(dModel);
        R.setModel(RModel);
        G.setModel(GModel);
        B.setModel(BModel);
        number.setModel(numModel);
        k.setModel(kModel);
        m.setModel(mModel);
        n.setModel(nModel);
        sw.setModel(swModel);
        sh.setModel(shModel);
        zn.setModel(znModel);
        zf.setModel(zfModel);
        add(rootPanel);
        pack();
        a.addChangeListener(e -> {
            if ((Double) a.getValue() <= (Double) b.getValue()) {
            if (((BSplinePanel) bSplinePanel).spline != null)
                ((BSplinePanel) bSplinePanel).setAB((Double) a.getValue(), (Double) b.getValue());
            controller.setA((Double) a.getValue());
//            bModel[0] = new SpinnerNumberModel(controller.getB(), (double) a.getValue(), 1, 0.1);
//            b.setModel(bModel[0]);
            } else {
                JOptionPane.showMessageDialog(this, "a should be <= b",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        b.addChangeListener(e -> {
            if ((Double) a.getValue() <= (Double) b.getValue()) {
            if (((BSplinePanel) bSplinePanel).spline != null)
                ((BSplinePanel) bSplinePanel).setAB((Double) a.getValue(), (Double) b.getValue());
            controller.setB((Double) b.getValue());
            } else {
                JOptionPane.showMessageDialog(this, "a should be <= b",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        c.addChangeListener(e -> {
            if ((Double) c.getValue() <= (Double) d.getValue()) {
                controller.setC((Double) c.getValue());
            } else {
                JOptionPane.showMessageDialog(this, "c should be <= d",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        d.addChangeListener(e -> {
            if ((Double) c.getValue() <= (Double) d.getValue()) {
                controller.setD((Double) d.getValue());
            } else {
                JOptionPane.showMessageDialog(this, "c should be <= d",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        k.addChangeListener(e -> {
            controller.setK((Integer) k.getValue());
        });
        m.addChangeListener(e -> {
            controller.setM((Integer) m.getValue());
        });
        n.addChangeListener(e -> {
            controller.setN((Integer) n.getValue());
        });
        sw.addChangeListener(e -> {
            controller.setSw((Double) sw.getValue());
        });
        sh.addChangeListener(e -> {
            controller.setSh((Double) sh.getValue());
        });
        zn.addChangeListener(e -> {
            controller.setZn((Double) zn.getValue());
        });
        zf.addChangeListener(e -> {
            controller.setZf((Double) zf.getValue());
        });
        R.addChangeListener(e -> {
            if (((BSplinePanel) bSplinePanel).spline != null) {
                ((BSplinePanel) bSplinePanel).spline.setColor(new Color((Integer) R.getValue(), (Integer) G.getValue(), (Integer) B.getValue()));
                ((BSplinePanel) bSplinePanel).repaint();
            }
        });
        G.addChangeListener(e -> {
            if (((BSplinePanel) bSplinePanel).spline != null) {
                ((BSplinePanel) bSplinePanel).spline.setColor(new Color((Integer) R.getValue(), (Integer) G.getValue(), (Integer) B.getValue()));
                ((BSplinePanel) bSplinePanel).repaint();
            }
        });
        B.addChangeListener(e -> {
            if (((BSplinePanel) bSplinePanel).spline != null) {
                ((BSplinePanel) bSplinePanel).spline.setColor(new Color((Integer) R.getValue(), (Integer) G.getValue(), (Integer) B.getValue()));
                ((BSplinePanel) bSplinePanel).repaint();
            }
        });
        number.addChangeListener(e -> {
            if ((Integer) number.getValue() < controller.splines.size() + 1) {
                ((BSplinePanel) bSplinePanel).setSpline(controller.splines.get((Integer) number.getValue() - 1));
                ((BSplinePanel) bSplinePanel).setAB((Double) a.getValue(), (Double) b.getValue());
                int r = ((BSplinePanel) bSplinePanel).spline.getColor().getRed(),
                        g = ((BSplinePanel) bSplinePanel).spline.getColor().getGreen(),
                        b = ((BSplinePanel) bSplinePanel).spline.getColor().getBlue();
                R.setValue(r);
                G.setValue(g);
                B.setValue(b);
            } else {
                ((BSplinePanel) bSplinePanel).setAB((Double) a.getValue(), (Double) b.getValue());
                ((BSplinePanel) bSplinePanel).clear();
            }
        });
        OKButton.addActionListener(event -> {
            try {
                if ((Integer) number.getValue() == controller.splines.size() + 1) {
                    if (((BSplinePanel) bSplinePanel).spline != null)
                        controller.addSpline(((BSplinePanel) bSplinePanel).getSpline());
                    controller.getScene().recreate();
                    controller.getInitView().createScene();
                } else {
                    controller.getScene().getFigures().get((Integer) number.getValue() - 1)
                            .setSpline(controller.splines.get((Integer) number.getValue() - 1));
                    controller.getScene().recreate();
                    controller.getInitView().createScene();
                }
            } catch (NumberFormatException e) {
            }
            dispose();
        });
        cancelButton.addActionListener(e -> {
            dispose();
        });
    }

    private void createUIComponents() {
        bSplinePanel = new BSplinePanel();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 17;
        gbc.weightx = 100.0;
        gbc.weighty = 80.0;
        gbc.fill = GridBagConstraints.BOTH;
        rootPanel.add(bSplinePanel, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("a");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rootPanel.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("m");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        rootPanel.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("zn");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        rootPanel.add(label3, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("b");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rootPanel.add(label4, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("n");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        rootPanel.add(label5, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("zf");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        rootPanel.add(label6, gbc);
        final JLabel label7 = new JLabel();
        label7.setText("c");
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rootPanel.add(label7, gbc);
        final JLabel label8 = new JLabel();
        label8.setText("k");
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        rootPanel.add(label8, gbc);
        final JLabel label9 = new JLabel();
        label9.setText("sw");
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        rootPanel.add(label9, gbc);
        final JLabel label10 = new JLabel();
        label10.setText("d");
        gbc = new GridBagConstraints();
        gbc.gridx = 10;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rootPanel.add(label10, gbc);
        final JLabel label11 = new JLabel();
        label11.setText("№");
        gbc = new GridBagConstraints();
        gbc.gridx = 10;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        rootPanel.add(label11, gbc);
        final JLabel label12 = new JLabel();
        label12.setText("sh");
        gbc = new GridBagConstraints();
        gbc.gridx = 10;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        rootPanel.add(label12, gbc);
        a = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 10.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(a, gbc);
        m = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 10.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(m, gbc);
        zn = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 10.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(zn, gbc);
        b = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.weightx = 5.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(b, gbc);
        n = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.weightx = 5.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(n, gbc);
        zf = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 3;
        gbc.weightx = 5.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(zf, gbc);
        c = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 1;
        gbc.weightx = 10.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(c, gbc);
        k = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 2;
        gbc.weightx = 10.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(k, gbc);
        sw = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 3;
        gbc.weightx = 10.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(sw, gbc);
        d = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 11;
        gbc.gridy = 1;
        gbc.weightx = 10.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(d, gbc);
        number = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 11;
        gbc.gridy = 2;
        gbc.weightx = 10.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(number, gbc);
        sh = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 11;
        gbc.gridy = 3;
        gbc.weightx = 10.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(sh, gbc);
        final JLabel label13 = new JLabel();
        label13.setText("R");
        gbc = new GridBagConstraints();
        gbc.gridx = 13;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rootPanel.add(label13, gbc);
        final JLabel label14 = new JLabel();
        label14.setText("G");
        gbc = new GridBagConstraints();
        gbc.gridx = 13;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        rootPanel.add(label14, gbc);
        final JLabel label15 = new JLabel();
        label15.setText("B");
        gbc = new GridBagConstraints();
        gbc.gridx = 13;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        rootPanel.add(label15, gbc);
        R = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 14;
        gbc.gridy = 1;
        gbc.weightx = 10.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(R, gbc);
        G = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 14;
        gbc.gridy = 2;
        gbc.weightx = 10.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(G, gbc);
        B = new JSpinner();
        gbc = new GridBagConstraints();
        gbc.gridx = 14;
        gbc.gridy = 3;
        gbc.weightx = 10.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(B, gbc);
        OKButton = new JButton();
        OKButton.setText("OK");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.weightx = 10.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(OKButton, gbc);
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 4;
        gbc.weightx = 5.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rootPanel.add(cancelButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}
