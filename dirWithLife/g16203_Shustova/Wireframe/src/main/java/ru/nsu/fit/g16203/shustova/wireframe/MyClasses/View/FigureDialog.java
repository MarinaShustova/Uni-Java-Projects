package ru.nsu.fit.g16203.shustova.wireframe.MyClasses.View;

import ru.nsu.fit.g16203.shustova.wireframe.MyClasses.Logic.Controller;
import ru.nsu.fit.g16203.shustova.wireframe.MyClasses.Logic.Figure;

import javax.swing.*;
import java.awt.*;

public class FigureDialog extends JDialog {

    public FigureDialog(JFrame owner, Controller controller){
        super(owner, "Settings", false);
        final Figure[] f = new Figure[1];
        JPanel settingsPanel = new JPanel(new GridLayout(7, 2, 0, 0));
        JSpinner spinner = new JSpinner();
        final SpinnerModel[] numModel = {new SpinnerNumberModel(controller.figures.size(), 1, controller.figures.size(), 1)};
        spinner.setModel(numModel[0]);
        f[0] = controller.getScene().getFigures().get((Integer) spinner.getValue()-1);
        JSlider Cx = new JSlider(JSlider.HORIZONTAL, -300, 300,
                (int)Math.round(controller.figures.get((Integer) spinner.getValue()-1).Cx));
        JSlider Cy = new JSlider(JSlider.HORIZONTAL, -300, 300,
                (int)Math.round(controller.figures.get((Integer) spinner.getValue()-1).Cy));
        JSlider Cz = new JSlider(JSlider.HORIZONTAL, -300, 300,
                (int)Math.round(controller.figures.get((Integer) spinner.getValue()-1).Cz));
        JSlider rx = new JSlider(JSlider.HORIZONTAL, 0, 360,
                controller.figures.get((Integer) spinner.getValue()-1).rx);
        JSlider ry = new JSlider(JSlider.HORIZONTAL, 0, 360,
                controller.figures.get((Integer) spinner.getValue()-1).ry);
        JSlider rz = new JSlider(JSlider.HORIZONTAL, 0, 360,
                controller.figures.get((Integer) spinner.getValue()-1).rz);
        JLabel spinLabel = new JLabel("№:");
        JLabel xLabel = new JLabel("Cx:");
        JLabel yLabel = new JLabel("Cy:");
        JLabel zLabel = new JLabel("Cz:");
        JLabel rxLabel = new JLabel("rx:");
        JLabel ryLabel = new JLabel("ry:");
        JLabel rzLabel = new JLabel("rz:");
        settingsPanel.add(spinLabel);
        settingsPanel.add(spinner);
        settingsPanel.add(xLabel);
        settingsPanel.add(Cx);
        settingsPanel.add(yLabel);
        settingsPanel.add(Cy);
        settingsPanel.add(zLabel);
        settingsPanel.add(Cz);
        settingsPanel.add(rxLabel);
        settingsPanel.add(rx);
        settingsPanel.add(ryLabel);
        settingsPanel.add(ry);
        settingsPanel.add(rzLabel);
        settingsPanel.add(rz);

        spinner.addChangeListener(e->{
            f[0] = controller.getScene().getFigures().get((Integer) spinner.getValue()-1);
            int cx = (int)Math.round(f[0].Cx),
                    cy = (int)Math.round(f[0].Cy),
                    cz = (int)Math.round(f[0].Cz),
                    x = f[0].rx,
                    y = f[0].ry,
                    z = f[0].rz;
            Cx.setValue(cx);
            Cy.setValue(cy);
            Cz.setValue(cz);
            rx.setValue(x);
            ry.setValue(y);
            rz.setValue(z);
        });
        Cx.addChangeListener(e -> {
            controller.getScene().moveFigure((Integer) spinner.getValue()-1,
                    Cx.getValue(), Cy.getValue(), Cz.getValue());
            controller.getScene().recreate();
            controller.getInitView().createScene();
        });
        Cy.addChangeListener(e -> {
            controller.getScene().moveFigure((Integer) spinner.getValue()-1,
                    Cx.getValue(), Cy.getValue(), Cz.getValue());
            controller.getScene().recreate();
            controller.getInitView().createScene();
        });
        Cz.addChangeListener(e -> {
            controller.getScene().moveFigure((Integer) spinner.getValue()-1,
                    Cx.getValue(), Cy.getValue(), Cz.getValue());
            controller.getScene().recreate();
            controller.getInitView().createScene();
        });

        rx.addChangeListener(e -> {
            controller.getScene().rotateFigure((Integer) spinner.getValue()-1,
                    rx.getValue(), 0);
            controller.getScene().recreate();
            controller.getInitView().createScene();
        });
        ry.addChangeListener(e -> {
            controller.getScene().rotateFigure((Integer) spinner.getValue()-1,
                    ry.getValue(), 1);
            controller.getScene().recreate();
            controller.getInitView().createScene();
        });
        rz.addChangeListener(e -> {
            controller.getScene().rotateFigure((Integer) spinner.getValue()-1,
                    rz.getValue(), 2);
            controller.getScene().recreate();
            controller.getInitView().createScene();
        });


        JButton delete = new JButton("DELETE");
        delete.addActionListener(event -> {
            controller.getScene().getFigures().remove((Integer) spinner.getValue() - 1);
            controller.splines.remove((Integer) spinner.getValue() - 1);
            controller.getScene().getAxis().remove(((Integer) spinner.getValue() - 1)*3+2);
            controller.getScene().getAxis().remove(((Integer) spinner.getValue() - 1)*3+1);
            controller.getScene().getAxis().remove(((Integer) spinner.getValue() - 1)*3);
            controller.getScene().recreate();
            controller.getInitView().recreate();
            if (controller.figures.size() == 0) {
                dispose();
            }
            else {
                numModel[0] = new SpinnerNumberModel(controller.figures.size(), 1, controller.figures.size(), 1);
                spinner.setModel(numModel[0]);
                f[0] = controller.getScene().getFigures().get((Integer) spinner.getValue()-1);
                int cx = (int)Math.round(f[0].Cx),
                        cy = (int)Math.round(f[0].Cy),
                        cz = (int)Math.round(f[0].Cz),
                        x = f[0].rx,
                        y = f[0].ry,
                        z = f[0].rz;
                Cx.setValue(cx);
                Cy.setValue(cy);
                Cz.setValue(cz);
                rx.setValue(x);
                ry.setValue(y);
                rz.setValue(z);
            }
        });
        JPanel confirmPanel = new JPanel();
        confirmPanel.add(delete);
        add(confirmPanel, BorderLayout.AFTER_LAST_LINE);
        add(settingsPanel, BorderLayout.CENTER);
        pack();
    }

}
