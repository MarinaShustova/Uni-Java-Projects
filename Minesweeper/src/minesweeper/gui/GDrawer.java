package minesweeper.gui;
import minesweeper.Field;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class GDrawer {
    public JFrame window;
    private GControl plane;
    private Field ff;
    private JButton NG;
    public GDrawer(int xsize, int ysize, int bnum){
        window = new JFrame("Minesweeper");
        window.setSize(30*(ysize+1), 30*(xsize+2)+50);
        ff = new Field(xsize,ysize,bnum);
        plane = new GControl(ff);
        window.add(plane);
        NG = new JButton("New Game");
        NG.setBounds(15*(ysize)-40, 0, 100, 50);
        NG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset(xsize, ysize, bnum);
                plane.add(NG);
            }
        });
        plane.add(NG);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
    public void reset(int xsize, int ysize, int bnum){
        Field f = new Field(xsize,ysize,bnum);
        ff = f;
        GControl plane1 = new GControl(ff);
        window.remove(plane);
        window.add(plane1);
        plane = plane1;
        window.revalidate();
        window.repaint();
    }
}
