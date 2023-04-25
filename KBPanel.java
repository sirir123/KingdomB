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
import java.net.HttpURLConnection;
import java.net.URL;

public class KBPanel extends JPanel implements MouseListener, Runnable {
    private BufferedImage startScr, plyPick, mainScr, startTile;
    private BufferedImage lordCd, workCd, discCd, castCd;
    private BufferedImage tFlwr, tGrs, tDes, tFor, tCnyn;
    private BufferedImage infoUp, buttonX;
    private BufferedImage currCol;
    private BufferedImage colPink;
    private ArrayList<BufferedImage> plyRects;
    private ArrayList<BoardImage> boards;
    boolean download = false;
    String home = System.getProperty("user.home");
    File out = new File(home + "/Downloads/KingdomBuilderInstructions.pdf");

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

            tFlwr = ImageIO.read(KBPanel.class.getResource("pictures/flower.png"));//
            tGrs = ImageIO.read(KBPanel.class.getResource("pictures/meadow.png"));//
            tCnyn = ImageIO.read(KBPanel.class.getResource("pictures/canyon.png"));//
            tDes = ImageIO.read(KBPanel.class.getResource("pictures/desert.png"));//
            tFor = ImageIO.read(KBPanel.class.getResource("pictures/forest.png"));//

            infoUp = ImageIO.read(KBPanel.class.getResource("pictures/helpPopUp.png"));
            buttonX = ImageIO.read(KBPanel.class.getResource("pictures/buttonX.png"));

            currCol = ImageIO.read(KBPanel.class.getResource("pictures/currentColor.png"));

            // boards
            boards.add(new BoardImage(0, ImageIO.read(KBPanel.class.getResource("/Pictures/boardBoat.png"))));
            boards.add(new BoardImage(1, ImageIO.read(KBPanel.class.getResource("/Pictures/boardGrass.png"))));
            boards.add(new BoardImage(2, ImageIO.read(KBPanel.class.getResource("/Pictures/boardHorse.png"))));
            boards.add(new BoardImage(3, ImageIO.read(KBPanel.class.getResource("/Pictures/boardOracle.png"))));
            Collections.shuffle(boards, new Random());

            startTile = ImageIO.read(KBPanel.class.getResource("pictures/startTile.png"));

            colPink = ImageIO.read(KBPanel.class.getResource("pictures/colorPink.png"));

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
        if (!help && !start && y >= 569 * (getHeight() / 889.0) && y <= 624 * (getHeight() / 889.0)) {
            if (x >= 654 * (getWidth() / 1238.0) && x <= 690 * (getWidth() / 1238.0)) { // for start screen
                // System.out.println("2");
                numPly = 2;
            } else if (x >= 744 * (getWidth() / 1238.0) && x <= 782 * (getWidth() / 1238.0)) {
                // System.out.println("3");
                numPly = 3;
            } else if (x >= 833 * (getWidth() / 1238.0) && x <= 869 * (getWidth() / 1238.0)) {
                // System.out.println("4");
                numPly = 4;
            }
        }

        if (!help && !start && x >= 516 * (getWidth() / 1238.0) && x <= 730 * (getWidth() / 1238.0)
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
            // System.out.println(getWidth() + "+" + getHeight());
            gm.shuffleDeck();
            for (int i = 0; i < numPly; i++) {
                gm.getPlayer(i).setChosen(gm.drawDeck());
            }
            gm.setBoards(boards);
            gm.updateAvaliable();
        }

        if (start && intpoint_inside_circle(x, y, new intPoint(785, 110), 25)) {
            help = !help;
            repaint();
        }
        if (help && x >= 253 * (getWidth() / 1238.0) && x <= 400 * (getWidth() / 1238.0)
                && y >= 690 * (getHeight() / 889.0) && y <= 718 * (getHeight() / 889.0)) {
            run();
        }
        // if (start && help && x >= 754 && x <= 795 && y <= 306 && y >= 276) {
        // help = false;
        // repaint();
        // }

        repaint();
    }

    public void addNotify() { // tbh idk what this does I just always have it and am paranoid its not working
                              // without it
        super.addNotify();
        requestFocus();
    }

    public void run() {
        try {
            URL url = new URL("https://rules.queen-games.com/kingdom-builder_en.pdf");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            BufferedInputStream in = new BufferedInputStream(http.getInputStream());
            FileOutputStream fos = new FileOutputStream(this.out);
            BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = in.read(buffer, 0, 1024)) >= 0) {
                bout.write(buffer, 0, read);
            }
            Desktop.getDesktop().open(out);
            bout.close();
            in.close();
            // 🦧
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        // g.setColor(Color.black);
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

            // boards
            drawBoard(g);

            if (help) {
                // System.out.println("fuck");
                g.setColor(new Color(55, 24, 18, 95));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.drawImage(infoUp, (int) (223 * (getWidth() / 1238.0)), (int) (238 * (getHeight() / 889.0)),
                        (int) ((810 - 220) * (getWidth() / 1238.0)),
                        (int) ((742 - 229) * (getHeight() / 889.0)), null);
                g.drawImage(buttonX, (int) (762 * (getWidth() / 1238.0)), (int) (90 * (getHeight() / 889.0)),
                        (int) (46 * (getWidth() / 1238.0)),
                        (int) (46 * (getWidth() / 1238.0)), null);
            }
            drawShaders(g);
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

    public BufferedImage getTerrImage(String t) {
        if (t.equals("flwr")) {
            return tFlwr;
        }
        if (t.equals("grs")) {
            return tGrs;
        }
        if (t.equals("des")) {
            return tDes;
        }
        if (t.equals("cnyn")) {
            return tCnyn;
        }
        if (t.equals("for")) {
            return tFor;
        }
        return null;
    }

    public void drawPlayers(Graphics g) {
        g.setFont(new Font("SansSerif", Font.BOLD, (int) (15 * (getWidth() / 1238.0) * (getHeight() / 889.0))));
        if (gm != null) {

            // System.out.println("Curr: " + gm.getCurrPlayer());
            // System.out.println("Type: " +
            // gm.getPlayer(gm.getCurrPlayer()).getChosen().getTerr());
            for (int i = 0; i < numPly; i++) {
                switch (i) {
                    case 0:
                        g.drawImage(plyRects.get(0), (int) (835 * (getWidth() / 1238.0)),
                                (int) (81 * (getHeight() / 889.0)), (int) (330 * (getWidth() / 1238.0)),
                                (int) (180 * (getHeight() / 889.0)), null);

                        if (gm.getCurrPlayer() == 0) {
                            g.drawImage(currCol, (int) (835 * (getWidth() / 1238.0)),
                                    (int) (81 * (getHeight() / 889.0)), (int) (318 * (getWidth() / 1238.0)),
                                    (int) (168 * (getHeight() / 889.0)), null);
                            g.drawImage(getTerrImage(gm.getPlayer(gm.getCurrPlayer()).getChosen().getTerr()),
                                    (int) (getWidth() / 1.335), (int) (getHeight() / 9.53),
                                    (int) ((1030 - 934) * (getWidth() / 1250.0)),
                                    (int) ((236 - 92) * (getHeight() / 896.0)), null);
                        }
                        g.drawString("" + (gm.getPlayer(0).getSettlements()), (int) (895 * (getWidth() / 1238.0)),
                                (int) (143 * (getHeight() / 889.0)));

                        if (gm.getCurrPlayer() == 0) {
                            g.drawImage(getTerrImage(gm.getPlayer(gm.getCurrPlayer()).getChosen().getTerr()),
                                    (int) (938 * (getWidth() / 1250.0)), (int) (96 * (getHeight() / 896.0)),
                                    (int) ((1030 - 934) * (getWidth() / 1250.0)),
                                    (int) ((236 - 92) * (getHeight() / 896.0)), null);
                        }
                        break;
                    case 1:
                        g.drawImage(plyRects.get(1), (int) (835 * (getWidth() / 1238.0)),
                                (int) (265 * (getHeight() / 889.0)), (int) (330 * (getWidth() / 1238.0)),
                                (int) (180 * (getHeight() / 889.0)), null);

                        if (gm.getCurrPlayer() == 1) {
                            g.drawImage(currCol, (int) (835 * (getWidth() / 1238.0)),
                                    (int) (265 * (getHeight() / 889.0)), (int) (318 * (getWidth() / 1238.0)),
                                    (int) (168 * (getHeight() / 889.0)), null);
                            g.drawImage(getTerrImage(gm.getPlayer(gm.getCurrPlayer()).getChosen().getTerr()),
                                    (int) (getWidth() / 1.335), (int) (getHeight() / 3.17),
                                    (int) ((1030 - 934) * (getWidth() / 1250.0)),
                                    (int) ((236 - 92) * (getHeight() / 896.0)), null);

                        } // hi
                        g.drawString("" + (gm.getPlayer(1).getSettlements()), (int) (895 * (getWidth() / 1238.0)),
                                (int) (328 * (getHeight() / 889.0)));

                        if (gm.getCurrPlayer() == 1) {
                            g.drawImage(getTerrImage(gm.getPlayer(gm.getCurrPlayer()).getChosen().getTerr()),
                                    (int) (938 * (getWidth() / 1250.0)), (int) (283 * (getHeight() / 896.0)),
                                    (int) ((1030 - 934) * (getWidth() / 1250.0)),
                                    (int) ((236 - 92) * (getHeight() / 896.0)), null);

                        } // hi
                        break;
                    case 2:
                        g.drawImage(plyRects.get(2), (int) (835 * (getWidth() / 1238.0)),
                                (int) (450 * (getHeight() / 889.0)), (int) (330 * (getWidth() / 1238.0)),
                                (int) (180 * (getHeight() / 889.0)), null);

                        if (gm.getCurrPlayer() == 2) {
                            g.drawImage(currCol, (int) (835 * (getWidth() / 1238.0)),
                                    (int) (450 * (getHeight() / 889.0)), (int) (318 * (getWidth() / 1238.0)),
                                    (int) (168 * (getHeight() / 889.0)), null);
                            g.drawImage(getTerrImage(gm.getPlayer(gm.getCurrPlayer()).getChosen().getTerr()),
                                    (int) (getWidth() / 1.335), (int) (getHeight() / 1.92),
                                    (int) ((1030 - 934) * (getWidth() / 1250.0)),
                                    (int) ((236 - 92) * (getHeight() / 896.0)), null);

                        }
                        g.drawString("" + (gm.getPlayer(2).getSettlements()), (int) (895 * (getWidth() / 1238.0)),
                                (int) (512 * (getHeight() / 889.0)));

                        if (gm.getCurrPlayer() == 2) {
                            g.drawImage(getTerrImage(gm.getPlayer(gm.getCurrPlayer()).getChosen().getTerr()),
                                    (int) (938 * (getWidth() / 1250.0)), (int) (469 * (getHeight() / 896.0)),
                                    (int) ((1030 - 934) * (getWidth() / 1250.0)),
                                    (int) ((236 - 92) * (getHeight() / 896.0)), null);

                        }
                        break;
                    case 3:
                        g.drawImage(plyRects.get(3), (int) (835 * (getWidth() / 1238.0)),
                                (int) (635 * (getHeight() / 889.0)), (int) (330 * (getWidth() / 1238.0)),
                                (int) (180 * (getHeight() / 889.0)), null);

                        if (gm.getCurrPlayer() == 3) {
                            g.drawImage(currCol, (int) (835 * (getWidth() / 1238.0)),
                                    (int) (635 * (getHeight() / 889.0)), (int) (318 * (getWidth() / 1238.0)),
                                    (int) (168 * (getHeight() / 889.0)), null);
                            g.drawImage(getTerrImage(gm.getPlayer(gm.getCurrPlayer()).getChosen().getTerr()),
                                    (int) (getWidth() / 1.335), (int) (getHeight() / 1.366),
                                    (int) ((1030 - 934) * (getWidth() / 1250.0)),
                                    (int) ((236 - 92) * (getHeight() / 896.0)), null);

                        }
                        g.drawString("" + (gm.getPlayer(3).getSettlements()), (int) (895 * (getWidth() / 1238.0)),
                                (int) (696 * (getHeight() / 889.0)));

                        if (gm.getCurrPlayer() == 3) {
                            g.drawImage(getTerrImage(gm.getPlayer(gm.getCurrPlayer()).getChosen().getTerr()),
                                    (int) (938 * (getWidth() / 1250.0)), (int) (656 * (getHeight() / 896.0)),
                                    (int) ((1030 - 934) * (getWidth() / 1250.0)),
                                    (int) ((236 - 92) * (getHeight() / 896.0)), null);
                        }
                        break;
                }
            }

            if (gm.startPlayer == 0) {
                g.drawImage(startTile, 850, 150, 85, 70, null);
            } else if (gm.startPlayer == 1) {
                g.drawImage(startTile, 850, 330, 85, 70, null);
            } else if (gm.startPlayer == 2) {
                g.drawImage(startTile, 850, 510, 85, 70, null);
            } else if (gm.startPlayer == 3) {
                g.drawImage(startTile, 850, 700, 85, 70, null);
            }
        }
    }

    public void drawBoard(Graphics g) {
        ArrayList<Section> tempSet = gm.getBoards();
        // System.out.println("SIZE: " + tempSet.size());
        if (tempSet != null && tempSet.size() > 0)
            g.drawImage(tempSet.get(0).getImage(), (int) (244 * (getWidth() / 1238.0)),
                    (int) (255 * (getHeight() / 889.0)), (int) (279 * (getWidth() / 1238.0)),
                    (int) (238 * (getHeight() / 889.0)), null);
        g.drawImage(tempSet.get(1).getImage(), (int) ((242 + 279 - 11) * (getWidth() / 1238.0)),
                (int) (255 * (getHeight() / 889.0)), (int) (279 * (getWidth() / 1238.0)),
                (int) (238 * (getHeight() / 889.0)), null);
        g.drawImage(tempSet.get(2).getImage(), (int) (244 * (getWidth() / 1238.0)),
                (int) ((254 + 237 - 5) * (getHeight() / 889.0)),
                (int) (279 * (getWidth() / 1238.0)), (int) (238 * (getHeight() / 889.0)), null);
        g.drawImage(tempSet.get(3).getImage(), (int) ((242 + 279 - 11) * (getWidth() / 1238.0)),
                (int) ((254 + 237 - 5) * (getHeight() / 889.0)), (int) (279 * (getWidth() / 1238.0)),
                (int) (238 * (getHeight() / 889.0)), null);
    }

    public void drawShaders(Graphics g) {
        // start 241, 255
        // width 30
        // height 30?

        // int x = 240;

        // g.drawImage(colPink, 230 + (27 * (int) (gm.avaliable.get(0).getCol())),
        // 250 + (int) (26 + (23 * (gm.avaliable.get(0).getRow()))), 30, 29, null);
        // System.out.println("START");
        System.out.println(gm.avaliable.toString());
        for (Hex hx : gm.avaliable) {
            // System.out.println("(" + (hx.getCol()) + ", " + hx.getRow() + ")");
            if (hx.getRow() % 2 == 0) {
                // System.out.println("even: " + hx.getCol() / 2);
                g.drawImage(colPink, 235 + (29 * (hx.getCol() / 2)),
                        255 + 23 * hx.getRow(), 29, 30, null);
            } else {
                // System.out.println("odd: " + ((hx.getCol() - 1) / 2));
                g.drawImage(colPink, 253 + (30 * ((hx.getCol() - 1)) / 2),
                        255 + 23 * hx.getRow(), 29, 30, null);
            }
        }

        // g.drawImage(colPink, 240, 255, 30, 29, null);
        // g.drawImage(colPink, 240 + 13 * 1, 255 + 23 * 1, 29, 30, null);
        // g.drawImage(colPink, 240 + 13 * 2, 255 + 23 * 2, 29, 30, null);
        // g.drawImage(colPink, 240 + 13 * 3, 255 + 23 * 3, 29, 30, null);
        // g.drawImage(colPink, 240 + 13 * 4, 255 + 23 * 4, 29, 30, null);
        // g.drawImage(colPink, 240 + 13 * 5, 255 + 23 * 5, 29, 30, null);
        // g.drawImage(colPink, 240 + 13 * 6, 255 + 23 * 6, 29, 30, null);
        // g.drawImage(colPink, 240 + 13 * 7, 255 + 23 * 7, 29, 30, null);
        // g.drawImage(colPink, 240 + 13 * 8, 255 + 23 * 8, 29, 30, null);
        // g.drawImage(colPink, 240 + 13 * 9, 255 + 23 * 9, 29, 30, null);

        // g.drawImage(colPink, 508 + 13 * 0, 255 + 23 * 0, 29, 30, null);
        // g.drawImage(colPink, 508 + 13 * 1, 255 + 23 * 1, 29, 30, null);
        // g.drawImage(colPink, 508 + 13 * 2, 255 + 23 * 2, 29, 30, null);
        // g.drawImage(colPink, 508 + 13 * 3, 255 + 23 * 3, 29, 30, null);
        // g.drawImage(colPink, 508 + 13 * 4, 255 + 23 * 4, 29, 30, null);
        // g.drawImage(colPink, 508 + 13 * 5, 255 + 23 * 5, 29, 30, null);
        // g.drawImage(colPink, 508 + 13 * 6, 255 + 23 * 6, 29, 30, null);
        // g.drawImage(colPink, 508 + 13 * 7, 255 + 23 * 7, 29, 30, null);
        // g.drawImage(colPink, 508 + 13 * 8, 255 + 23 * 8, 29, 30, null);
        // g.drawImage(colPink, 508 + 13 * 9, 255 + 23 * 9, 29, 30, null);

        // g.drawImage(colPink, 243 + 13 * 10, 257 + 23 * 10, 29, 30, null);
        // g.drawImage(colPink, 243 + 13 * 11, 257 + 23 * 11, 29, 30, null);
        // g.drawImage(colPink, 243 + 13 * 12, 257 + 23 * 12, 29, 30, null);
        // g.drawImage(colPink, 243 + 13 * 13, 257 + 23 * 13, 29, 30, null);
        // g.drawImage(colPink, 243 + 13 * 14, 257 + 23 * 14, 29, 30, null);
        // g.drawImage(colPink, 243 + 13 * 15, 257 + 23 * 15, 29, 30, null);
        // g.drawImage(colPink, 243 + 13 * 16, 257 + 23 * 16, 29, 30, null);
        // g.drawImage(colPink, 243 + 13 * 17, 257 + 23 * 17, 29, 30, null);
        // g.drawImage(colPink, 243 + 13 * 18, 257 + 23 * 18, 29, 30, null);
        // g.drawImage(colPink, 243 + 13 * 19, 257 + 23 * 19, 29, 30, null);

        // g.drawImage(colPink, 511 + 13 * 10, 257 + 23 * 10, 29, 30, null);
        // g.drawImage(colPink, 511 + 13 * 11, 257 + 23 * 11, 29, 30, null);
        // g.drawImage(colPink, 511 + 13 * 12, 257 + 23 * 12, 29, 30, null);
        // g.drawImage(colPink, 511 + 13 * 13, 257 + 23 * 13, 29, 30, null);
        // g.drawImage(colPink, 511 + 13 * 14, 257 + 23 * 14, 29, 30, null);
        // g.drawImage(colPink, 511 + 13 * 15, 257 + 23 * 15, 29, 30, null);
        // g.drawImage(colPink, 511 + 13 * 16, 257 + 23 * 16, 29, 30, null);
        // g.drawImage(colPink, 511 + 13 * 17, 257 + 23 * 17, 29, 30, null);
        // g.drawImage(colPink, 511 + 13 * 18, 257 + 23 * 18, 29, 30, null);
        // g.drawImage(colPink, 511 + 13 * 19, 257 + 23 * 19, 29, 30, null);
        // for (Hex hx : gm.avaliable) {
    }

    public void drawSettlements(Graphics g) {

    }

    public boolean intpoint_inside_circle(int x, int y, intPoint center, int rad) {
        // System.out.println("please-----");
        int dist = (int) (Math.sqrt(Math.pow(x - center.x, 2) + Math.pow(y - center.y, 2)));
        // System.out.println(rad + " vs. " + dist);
        if (dist <= rad)
            return true;
        return false;
    }

    public boolean intpoint_inside_hexagon(int x, int y, intPoint square, intPoint triangleUp, intPoint triangleDown) {
        return false;
    }

    public boolean intpoint_inside_trigon(int x, int y, intPoint a, intPoint b, intPoint c) {
        int as_x = x - a.x;
        int as_y = y - a.y;

        boolean s_ab = (b.x - a.x) * as_y - (b.y - a.y) * as_x > 0;

        if ((c.x - a.x) * as_y - (c.y - a.y) * as_x > 0 == s_ab)
            return false;
        if ((c.x - b.x) * (y - b.y) - (c.y - b.y) * (x - b.x) > 0 != s_ab)
            return false;
        return true;
    }
}