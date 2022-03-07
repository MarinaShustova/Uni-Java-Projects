package minesweeper.gui;

import minesweeper.Field;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GControl extends JPanel {
    public JButton fieldelem[][];
    public GControl(Field f){
        setLayout(null);
        fieldelem = new JButton[f.sizey - 2][f.sizex - 2];
        makeField(f);
        for (int i = 0; i<f.sizey - 2; ++i)
            for (int j = 0; j<f.sizex - 2; ++j){
                fieldelem[i][j].setActionCommand(i+","+j);
                int finalI = i;
                int finalJ = j;
                    fieldelem[finalI][finalJ].addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if(e.getButton() == MouseEvent.BUTTON1){
                                System.err.printf("event %d, %d\n", finalJ, finalI);
                                f.updateAfterClick(finalJ, finalI, 0);
                                updateField(f);
                                if(f.bam == 0){
                                    JOptionPane.showMessageDialog(null, "Congratulations, you won!");
                                }
                            }
                            else if(e.getButton() == MouseEvent.BUTTON3){
                                System.err.printf("event %d, %d\n", finalJ, finalI);
                                if(f.ofield[finalJ+1][finalI+1] == 3)
                                    f.updateAfterClick(finalJ, finalI,-1);
                                else
                                    f.updateAfterClick(finalJ, finalI,1);
                                updateField(f);
                                if(f.bam == 0){
                                    JOptionPane.showMessageDialog(null, "Congratulations, you won!");
                                }
                            }
                        }
                        @Override
                        public void mousePressed(MouseEvent e) { }
                        @Override
                        public void mouseReleased(MouseEvent e) { }
                        @Override
                        public void mouseEntered(MouseEvent e) { }
                        @Override
                        public void mouseExited(MouseEvent e) { }
                    });
            }
    }
    protected static ImageIcon createIcon(String path) {
        URL imgURL = GControl.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("File not found " + path);
            return null;
        }
    }
    public void updateField(Field f) {
        ImageIcon im;
            for (int i = 1; i < f.sizex - 1; ++i)
                for (int j = 1; j < f.sizey - 1; ++j) {
                    if ((f.ofield[i][j] == 0)&&(f.gameIsOn)) {
                            im = createIcon("resources/closed.png");
                    }
                    else if(f.ofield[i][j] == 3) {
                        im = createIcon("resources/flaged.png");
                    }
                    else {
                        switch (f.field[i][j]) {
                            case 0: {
                                im = createIcon("resources/zero.png");
                                break;
                            }
                            case 1: {
                                im = createIcon("resources/num1.png");
                                break;
                            }
                            case 2: {
                                im = createIcon("resources/num2.png");
                                break;
                            }
                            case 3: {
                                im = createIcon("resources/num3.png");
                                break;
                            }
                            case 4: {
                                im = createIcon("resources/num4.png");
                                break;
                            }
                            case 5: {
                                im = createIcon("resources/num5.png");
                                break;
                            }
                            case 6: {
                                im = createIcon("resources/num6.png");
                                break;
                            }
                            case 7: {
                                im = createIcon("resources/num7.png");
                                break;
                            }
                            case 8: {
                                im = createIcon("resources/num8.png");
                                break;
                            }
                            case -1: {
                                if(!f.gameIsOn)
                                    im = createIcon("resources/bomb.png");
                                else
                                    im = createIcon("resources/closed.png");
                                break;
                            }
                            case -2: {
                                im = createIcon("resources/bombed.png");
                                break;
                            }
                            default: {
                                im = createIcon("resources/inform.png");
                            }
                        }
                    }
                    fieldelem[j - 1][i - 1].setIcon(im);
                }
    }
    public void makeField(Field f){
        ImageIcon im;
        for (int i = 1; i<f.sizex - 1; ++i)
            for (int j = 1; j<f.sizey - 1; ++j){
                    im = createIcon("resources/closed.png");
                    fieldelem[j-1][i-1] = new JButton();
                    fieldelem[j-1][i-1].setIcon(im);
                    fieldelem[j-1][i-1].setBounds(10+(j-1)*30, 50+(i-1)*30, 30, 30);
                    add(fieldelem[j-1][i-1]);
            }

    }
}