import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class KBFrame extends JFrame {// hi
    private static final int WIDTH = 1400;
    private static final int HEIGHT = 1002;
    private BufferedImage icon;

    public KBFrame(String framename) {
        super(framename);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);// finally centered this bitch
        add(new KBPanel());
        setVisible(true);
        setResizable(false);

        try {
            icon = ImageIO.read(KBPanel.class.getResource("/Pictures/icon.jpg"));
        } catch (Exception E) {
            System.out.println("Exception Error in frame");
            E.printStackTrace();
            return;
        }
        setIconImage(icon);

    }
}
