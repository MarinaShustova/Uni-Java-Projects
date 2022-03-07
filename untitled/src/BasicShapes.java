import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BasicShapes extends JPanel {

    public void paint(Graphics g) {
        BufferedImage bimage = new BufferedImage(200, 200,
                BufferedImage.TYPE_BYTE_INDEXED);
        Graphics2D g2d = bimage.createGraphics();

        Color transparent = new Color(0, 0, 0, 0);
        g2d.setColor(transparent);
        g2d.setComposite(AlphaComposite.Src);
        g2d.fill(new Rectangle2D.Float(20, 20, 100, 20));
        g2d.dispose();

    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Basic Shapes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new BasicShapes());
        frame.setSize(350, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}