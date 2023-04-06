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
    private BufferedImage startScr, plyPick, mainScr;
    private ArrayList<BufferedImage> plyRects;
    // private ArrayList<Player> plys;
    private int firstPly, currPly; // stores who was first player and who is current

    private int numPly; // number of players playing
    private boolean start, end;
    private Game gm;

    public KBPanel() {
        // plys = new ArrayList<>();
        numPly = 2;
        start = false;
        end = false;

        plyRects = new ArrayList<>();

        try {
            startScr = ImageIO.read(KBPanel.class.getResource("/Pictures/startScreen.png"));
            plyPick = ImageIO.read(KBPanel.class.getResource("/Pictures/playerPick.png"));
            mainScr = ImageIO.read(KBPanel.class.getResource("/Pictures/mainScreen.png"));

            plyRects.add(ImageIO.read(KBPanel.class.getResource("/Pictures/KB_Player1.png")));
            plyRects.add(ImageIO.read(KBPanel.class.getResource("/Pictures/KB_Player2.png")));
            plyRects.add(ImageIO.read(KBPanel.class.getResource("/Pictures/KB_Player3.png")));
            plyRects.add(ImageIO.read(KBPanel.class.getResource("/Pictures/KB_Player4.png")));
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
        if (!start && y >= 569 * (getHeight() / 889.0) && y <= 624 * (getHeight() / 889.0)) {
            if (x >= 654 * (getWidth() / 1238.0) && x <= 690 * (getWidth() / 1238.0)) { // for start screen
                System.out.println("2");
                numPly = 2;
            } else if (x >= 744 * (getWidth() / 1238.0) && x <= 782 * (getWidth() / 1238.0)) {
                System.out.println("3");
                numPly = 3;
            } else if (x >= 833 * (getWidth() / 1238.0) && x <= 869 * (getWidth() / 1238.0)) {
                System.out.println("4");
                numPly = 4;
            }
        }

        if (!start && x >= 516 * (getWidth() / 1238.0) && x <= 730 * (getWidth() / 1238.0)
                && y >= 665 * (getHeight() / 889.0)
                && y <= 734 * (getHeight() / 889.0)) {
            start = true;
            gm = new Game(numPly);
            // for (int i = 2; i < numPly; i++) {
            // plys.add(new Player());
            // }
            // firstPly = (int) Math.floor(Math.random() * 4);
            // currPly = firstPly;
        }

        repaint();
    }

    public void addNotify() { // tbh idk what this does I just always have it and am paranoid its not working
                              // without it
        super.addNotify();
        requestFocus();
    }

    public void paint(Graphics g) {
        System.out.println("Dimensions: (" + getWidth() + ", " + getHeight() + ")");
        if (!start) {

            drawStartScreen(g);
        } else if (end) {

        } else { // rest of game
            g.drawImage(mainScr, 0, 0, getWidth(), getHeight(), null);
        }

    }

    public void drawStartScreen(Graphics g) {
        g.drawImage(startScr, 0, 0, getWidth(), getHeight(), null);
        // shows how many players choosen
        switch (numPly) {
            case 2:
                g.drawImage(plyPick, (int) (654 * (getWidth() / 1238.0)), (int) (569 * (getHeight() / 889.0)),
                        (int) (37 * (getWidth() / 1238.0)), (int) (55 * (getHeight() / 889.0)), null);
                break;
            case 3:
                g.drawImage(plyPick, (int) (744 * (getWidth() / 1238.0)), (int) (569 * (getHeight() / 889.0)),
                        (int) (37 * (getWidth() / 1238.0)), (int) (55 * (getHeight() / 889.0)), null);
                // g.drawImage(plyPick, 744, 569, 37, 55, null);
                break;
            case 4:
                g.drawImage(plyPick, (int) (833 * (getWidth() / 1238.0)), (int) (569 * (getHeight() / 889.0)),
                        (int) (37 * (getWidth() / 1238.0)), (int) (55 * (getHeight() / 889.0)), null);
                // g.drawImage(plyPick, 833, 569, 37, 55, null);
                break;
        }
    }

    public void drawPlayers(Graphics g) {
        for (int i = 0; i < numPly; i++) {

        }
    }

    public void drawBoard(Graphics g) {

    }

    public void drawSettlements(Graphics g) {

    }
}
