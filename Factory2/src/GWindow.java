import factory.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class GWindow extends JFrame{
    public GWindow(AccessorySupplier[] a, BodyProducer b, EngineProducer e, Worker w[], Dealer[] d) {
        super("Factory");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel Alabel = new JLabel("Accessories made: ");
        JLabel Blabel = new JLabel("Bodies made: ");
        JLabel Elabel = new JLabel("Engines made: ");
        JLabel Wlabel = new JLabel("Cars made: ");
        JLabel Dlabel = new JLabel("Cars sold: ");
        Container container = getContentPane();
        container.setLayout(new GridLayout(5,2,2,2));
        container.add(Alabel);
        container.add(Blabel);
        container.add(Elabel);
        container.add(Wlabel);
        container.add(Dlabel);
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int made = 0, sold = 0;
                Alabel.setText("Accessories made: " + Integer.toString(AccessorySupplier.count));
                Blabel.setText("Bodies made: " + Integer.toString(b.count));
                Elabel.setText("Engines made: " + Integer.toString(e.count));
                Wlabel.setText("Cars made: " + Integer.toString(Worker.num));
                Dlabel.setText("Cars sold: " + Integer.toString(Dealer.num));
            }
        });

        timer.start();
    }
}
