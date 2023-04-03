import java.awt.*;
import javax.swing.*;

public class KBFrame extends JFrame {// hi
    final int WIDTH = 1250;
    final int HEIGHT = 924;

    public KBFrame(String framename) {
        super(framename);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        add(new KBPanel());
        setVisible(true);
    }
}
