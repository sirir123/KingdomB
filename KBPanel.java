import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialRef;
import javax.swing.*;
import java.io.*;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.*;

public class KBPanel extends JPanel implements MouseListener {
    private BufferedImage startScr, plyPick, mainScr;
    private BufferedImage lordCd, workCd, discCd, castCd;
    private ArrayList<BufferedImage> plyRects;
    private ArrayList<BoardImage> boards;

    private int numPly; // number of players playing
    private boolean start, end;
    private boolean help = false;
    private Game gm = new Game(-1);

    public KBPanel() {
        // plys = new ArrayList<>();
        numPly = 2;
        start = false;
        end = false;

        plyRects = new ArrayList<>();
        boards = new ArrayList<>();

        try {
            startScr = ImageIO.read(KBPanel.class.getResource("/Pictures/startScreen.png"));
            plyPick = ImageIO.read(KBPanel.class.getResource("/Pictures/playerPick.png"));
            mainScr = ImageIO.read(KBPanel.class.getResource("/Pictures/mainScreen.png"));

            plyRects.add(ImageIO.read(KBPanel.class.getResource("/Pictures/KB_Player1.png")));
            plyRects.add(ImageIO.read(KBPanel.class.getResource("/Pictures/KB_Player2.png")));
            plyRects.add(ImageIO.read(KBPanel.class.getResource("/Pictures/KB_Player3.png")));
            plyRects.add(ImageIO.read(KBPanel.class.getResource("/Pictures/KB_Player4.png")));

            // cards
            lordCd = ImageIO.read(KBPanel.class.getResource("/Pictures/cardLord.png"));
            workCd = ImageIO.read(KBPanel.class.getResource("/Pictures/cardWorker.png"));
            discCd = ImageIO.read(KBPanel.class.getResource("/Pictures/cardDiscoverer.png"));
            castCd = ImageIO.read(KBPanel.class.getResource("/Pictures/cardCastle.png"));

            // boards
            boards.add(new BoardImage(0, ImageIO.read(KBPanel.class.getResource("/Pictures/boardBoat.png"))));
            boards.add(new BoardImage(1, ImageIO.read(KBPanel.class.getResource("/Pictures/boardGrass.png"))));
            boards.add(new BoardImage(2, ImageIO.read(KBPanel.class.getResource("/Pictures/boardHorse.png"))));
            boards.add(new BoardImage(3, ImageIO.read(KBPanel.class.getResource("/Pictures/boardOracle.png"))));
            Collections.shuffle(boards, new Random());
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

            // for (int i = 2; i < numPly; i++) {
            // plys.add(new Player());
            // }
            // firstPly = (int) Math.floor(Math.random() * 4);
            // currPly = firstPly;
            repaint();
            gm = new Game(numPly);
            gm.shuffleDeck();
            for ( int i = 0; i < numPly; i++){ 
                gm.getPlayer(i).setChosen(gm.drawDeck());
            }
        } if (start && x >= 767 && x <=816 && y <= 133 && y>= 95 ){ help = true; repaint(); }
        // cancel out instructions if ( help )

        repaint();
    }

    public void addNotify() { // tbh idk what this does I just always have it and am paranoid its not working
                              // without it
        super.addNotify();
        requestFocus();
    }

    public void paint(Graphics g) {
        g.drawRect(0, 0, getWidth(), getHeight());
        // System.out.println("Dimensions: (" + getWidth() + ", " + getHeight() + ")");
        // System.out.println("Start: " + start);
        if (!start) {

            drawStartScreen(g);
        } else if (end) {
            // nada
        } else { // rest of game
            g.drawImage(mainScr, 0, 0, getWidth(), getHeight(), null);
            drawPlayers(g);
            // drawing cards
            g.drawImage(lordCd, (int) (82 * (getWidth() / 1238.0)), (int) (84 * (getHeight() / 889.0)),
                    (int) (120 * (getWidth() / 1238.0)), (int) (173 * (getHeight() / 889.0)), null);
            g.drawImage(workCd, (int) (81 * (getWidth() / 1238.0)), (int) (286 * (getHeight() / 889.0)),
                    (int) (120 * (getWidth() / 1238.0)), (int) (173 * (getHeight() / 889.0)), null);
            g.drawImage(discCd, (int) (81 * (getWidth() / 1238.0)), (int) (488 * (getHeight() / 889.0)),
                    (int) (120 * (getWidth() / 1238.0)), (int) (173 * (getHeight() / 889.0)), null);

            // drawing info
            g.setColor(Color.white);
            g.drawString("" + gm.getDeckSize(true), (int) (131 * (getWidth() / 1238.0)),
                    (int) (733 * (getHeight() / 889.0)));
            g.drawString("" + gm.getDeckSize(false), (int) (167 * (getWidth() / 1238.0)),
                    (int) (761 * (getHeight() / 889.0)));
            g.setFont(new Font("SansSerif", Font.BOLD, (int) (10 * (getWidth() / 1238.0) * (getHeight() / 889.0))));
            g.drawString("" + gm.discTiles, (int) (176 * (getWidth() / 1238.0)), (int) (782 * (getHeight() / 889.0)));
            if (help){ g.drawImage(workCd, 800, 800, 900, 900, null); }
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
        g.setFont(new Font("SansSerif", Font.BOLD, (int) (15 * (getWidth() / 1238.0) * (getHeight() / 889.0))));
        if (gm != null) {
            for (int i = 0; i < numPly; i++) {
                switch (i) {
                    case 0:
                        g.drawImage(plyRects.get(0), (int) (835 * (getWidth() / 1238.0)),
                                (int) (81 * (getHeight() / 889.0)), (int) (330 * (getWidth() / 1238.0)),
                                (int) (180 * (getHeight() / 889.0)), null);
                        g.drawString("" + (gm.getPlayer(0).getSettlements()), (int) (895 * (getWidth() / 1238.0)),
                                (int) (143 * (getHeight() / 889.0)));
                        if (gm.getCurrPlayer() == 0) { g.drawImage(castCd, 936, 284, 1030-934, 236-92, null); }

                        // if (gm.getCurrPlayer() == 0) { g.drawImage(castCd, 936, 94, 1030-934, 236-92, null); }
                        break;
                    case 1:
                        g.drawImage(plyRects.get(1), (int) (835 * (getWidth() / 1238.0)),
                                (int) (265 * (getHeight() / 889.0)), (int) (330 * (getWidth() / 1238.0)),
                                (int) (180 * (getHeight() / 889.0)), null);
                        g.drawString("" + (gm.getPlayer(1).getSettlements()), (int) (895 * (getWidth() / 1238.0)),
                                (int) (328 * (getHeight() / 889.0)));
                        if (gm.getCurrPlayer() == 1) { g.drawImage(castCd, 936, 286, 1030-934, 423-286, null); }
                        break;
                    case 2:
                        g.drawImage(plyRects.get(2), (int) (835 * (getWidth() / 1238.0)),
                                (int) (450 * (getHeight() / 889.0)), (int) (330 * (getWidth() / 1238.0)),
                                (int) (180 * (getHeight() / 889.0)), null);
                        g.drawString("" + (gm.getPlayer(2).getSettlements()), (int) (895 * (getWidth() / 1238.0)),
                                (int) (512 * (getHeight() / 889.0)));
                        break;
                    case 3:
                        g.drawImage(plyRects.get(3), (int) (835 * (getWidth() / 1238.0)),
                                (int) (635 * (getHeight() / 889.0)), (int) (330 * (getWidth() / 1238.0)),
                                (int) (180 * (getHeight() / 889.0)), null);
                        g.drawString("" + (gm.getPlayer(3).getSettlements()), (int) (895 * (getWidth() / 1238.0)),
                                (int) (696 * (getHeight() / 889.0)));
                        break;
                }
            }
        }
    }

    public void drawBoard(Graphics g) {

    }

    public void drawSettlements(Graphics g) {

    }
}
