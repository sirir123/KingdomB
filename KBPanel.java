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
    private boolean start, end;

    public KBPanel() {
        numPly = 2;
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
            if (x >= 654 && x <= 690) { // for start screen
                System.out.println("2");
                numPly = 2;
            } else if (x >= 744 && x <= 782) {
                System.out.println("3");
                numPly = 3;
            } else if (x >= 833 && x <= 869) {
                System.out.println("4");
                numPly = 4;
            }
        }
        repaint();
    }

    public void addNotify() { // tbh idk what this does I just always have it and am paranoid its not working
                              // without it
        super.addNotify();
        requestFocus();
    }

    public void paint(Graphics g) {
        if (!start) {
            drawStartScreen(g);
        }
        if (end) {

        } else { // rest of game

        }

    }

    public void drawStartScreen(Graphics g) {
        g.drawImage(startScr, 0, 0, getWidth(), getHeight(), null);
        // shows how many players choosen
        switch (numPly) {
            case 2:
                g.drawImage(plyPick, 654, 569, 37, 55, null);
                break;
            case 3:
                g.drawImage(plyPick, 744, 569, 37, 55, null);
                break;
            case 4:
                g.drawImage(plyPick, 833, 569, 37, 55, null);
                break;
        }
    }

    public void drawPlayers(Graphics g) {

    }

    public void drawBoard(Graphics g) {

    }

    public void drawSettlements(Graphics g) {

    }
}
