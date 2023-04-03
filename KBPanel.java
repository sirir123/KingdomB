import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.*;

public class KBPanel extends JPanel implements MouseListener {
    private BufferedImage startScr, plyPick;
    private int numPly;
    private boolean start;

    public KBPanel() {
        start = false;
        try {
            startScr = ImageIO.read(KBPanel.class.getResource("/Pictures/startScreen.png"));
            plyPick = ImageIO.read(KBPanel.class.getResource("/Pictures/playerPick.png"));
        } catch (Exception E) {
            System.out.println("Exception Error");
            return;
        }
        addMouseListener(this);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        System.out.println("loc is(" + x + ", " + y + ")");
        if (!start && y >= 569 && y <= 624) {
            if (x >= 654 && x <= 791) { // for start screen
                numPly = 2;
            } else if (x >= 744 && x <= 782) {

            } else if (x >= 833 && x < 869) {

            }
        }

    }

    public void addNotify() { // tbh idk what this does I just always have it and am paranoid its not working
                              // without it
        super.addNotify();
        requestFocus();
    }

    public void paint(Graphics g) {
        g.drawImage(startScr, 0, 0, getWidth(), getHeight(), null);
        g.drawImage(plyPick, 654, 569, 37, 55, null);
    }
}
