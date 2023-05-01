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
    private BufferedImage sOrcl, sHrse, sFrm, sBt, orcl, hrse, frm, bt;
    private BufferedImage infoUp, buttonX, darken;
    private BufferedImage currCol;
    private BufferedImage colWhite;
    private ArrayList<BufferedImage> plyRects, settColors;
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
        settColors = new ArrayList<>();
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

            darken = ImageIO.read(KBPanel.class.getResource("pictures/dark.png"));//

            sOrcl = ImageIO.read(KBPanel.class.getResource("pictures/shOracle.png")); //

            sHrse = ImageIO.read(KBPanel.class.getResource("pictures/shHorse.png"));
            sFrm = ImageIO.read(KBPanel.class.getResource("pictures/shFarm.png"));
            sBt = ImageIO.read(KBPanel.class.getResource("pictures/shBoat.png"));
            orcl = ImageIO.read(KBPanel.class.getResource("pictures/tOracle.png"));
            hrse = ImageIO.read(KBPanel.class.getResource("pictures/tHorse.png"));
            frm = ImageIO.read(KBPanel.class.getResource("pictures/tFarm.png"));
            bt = ImageIO.read(KBPanel.class.getResource("pictures/tBoat.png"));

            // sHrse = (1040, 93) 54 50, sOrcl = ()

            infoUp = ImageIO.read(KBPanel.class.getResource("/Pictures/helpPopUp.png"));
            buttonX = ImageIO.read(KBPanel.class.getResource("/Pictures/buttonX.png"));

            currCol = ImageIO.read(KBPanel.class.getResource("/Pictures/currentColor.png"));

            // boards
            boards.add(new BoardImage(0, ImageIO.read(KBPanel.class.getResource("/Pictures/boardBoat.png"))));
            boards.add(new BoardImage(1, ImageIO.read(KBPanel.class.getResource("/Pictures/boardGrass.png"))));
            boards.add(new BoardImage(2, ImageIO.read(KBPanel.class.getResource("/Pictures/boardHorse.png"))));
            boards.add(new BoardImage(3, ImageIO.read(KBPanel.class.getResource("/Pictures/boardOracle.png"))));
            Collections.shuffle(boards, new Random());

            // startTile =
            // ImageIO.read(KBPanel.class.getResource("/Pictures/startTile.png"));
            settColors.add(ImageIO.read(KBPanel.class.getResource("/Pictures/settPink.png")));
            settColors.add(ImageIO.read(KBPanel.class.getResource("/Pictures/settOrg.png")));
            settColors.add(ImageIO.read(KBPanel.class.getResource("/Pictures/settGreen.png")));
            settColors.add(ImageIO.read(KBPanel.class.getResource("/Pictures/settBlue.png")));

            colWhite = ImageIO.read(KBPanel.class.getResource("/Pictures/colorWhite.png"));

        } catch (Exception E) {
            System.out.println("Exception Error in panel");
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
            // TileHex test = new TileHex("farm", 0, 0, 0);
            // gm.getPlayer(0).addTile(test);
            // test.statUnused();
            // TileHex test2 = new TileHex("oracle", 0, 0, 0);
            // gm.getPlayer(0).addTile(test2);
            // gm.getPlayer(1).addTile(new TileHex ("horse", 0, 0, 0));

            gm.setBoards(boards);
            gm.updateAvaliable(true);
        }

        if (start && intpoint_inside_circle(x, y, new intPoint(877, 124), 25)) {
            help = !help;
            repaint();
        }
        if (start && help && x >= 576 && x <= 819
                && y >= 719 && y <= 797) {
            run();
        }

        if (start && !help && x >= 258 && x <= 897 && y >= 267 && y <= 787) {
            int[] cds = findCircle(x, y);
            System.out.println("LOC: (" + cds[0] + ", " + cds[1] + ")");
            if (gm.placed < 3 && gm.avaliable(cds[1], cds[0])) {
                // System.out.println("AVALIABLE");
                gm.placed++;
            }
        }

        repaint();
    }

    public void addNotify() {
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
            g.drawImage(lordCd, 93, 93, 133, 186, null);
            g.drawImage(workCd, 91, 311, 133, 186, null);
            g.drawImage(discCd, 91, 529, 133, 186, null);

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
                g.setColor(new Color(55, 24, 18, 95));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.drawImage(infoUp, 255, 266, 900 - 255, 801 - 266, null);
                g.drawImage(buttonX, 854, 100, 50, 50, null);
            }

            drawShaders(g);
            drawSettlements(g);
        }

    }

    public void drawStartScreen(Graphics g) {
        // System.out.println("(" + getWidth() + ", " + getHeight() + ")");
        g.drawImage(startScr, 0, 0, getWidth(),
                getHeight(), null);

        // shows how many players choosen
        switch (numPly) {
            case 2:
                g.drawImage(plyPick, 733, 623, 42, 52, null);
                break;
            case 3:
                g.drawImage(plyPick, 834, 623, 42, 52, null);
                break;
            case 4:
                g.drawImage(plyPick, 935, 623, 42, 52, null);
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

    public BufferedImage getTileImage(String t) {
        if (t.equals("farm")) {
            return frm;
        }
        if (t.equals("boat")) {
            return bt;
        }
        if (t.equals("oracle")) {
            return orcl;
        }
        if (t.equals("horse")) {
            return hrse;
        }
        if (t.equals("shfarm")) {
            return sFrm;
        }
        if (t.equals("shboat")) {
            return sBt;
        }
        if (t.equals("shoracle")) {
            return sOrcl;
        }
        if (t.equals("shhorse")) {
            return sHrse;
        }

        return null;
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

                        if (gm.getCurrPlayer() == 0) {
                            g.drawImage(currCol, (int) (835 * (getWidth() / 1238.0)),
                                    (int) (81 * (getHeight() / 889.0)), (int) (318 * (getWidth() / 1238.0)),
                                    (int) (168 * (getHeight() / 889.0)), null);
                            g.drawImage(getTerrImage(gm.getPlayer(gm.getCurrPlayer()).getChosen().getTerr()),
                                    (int) (getWidth() / 1.335), (int) (getHeight() / 9.53),
                                    (int) ((1030 - 934) * (getWidth() / 1250.0)),
                                    (int) ((236 - 92) * (getHeight() / 896.0)), null);
                            for (int x = 0; x < gm.getPlayer(0).getAllTiles().size(); x++) {
                                if (gm.getPlayer(0).getTile(x).getStat() == 1) {
                                    if (x < 2) {
                                        g.drawImage(getTileImage(gm.getPlayer(0).getTile(x).getType()), 1167 + (x * 61),
                                                105, 54, 50, null);
                                    } else if (x < 4) {
                                        g.drawImage(getTileImage(gm.getPlayer(0).getTile(x).getType()), 1045 + (x * 61),
                                                160, 54, 50, null);
                                    } else {
                                        g.drawImage(getTileImage(gm.getPlayer(0).getTile(x).getType()), 923 + (x * 61),
                                                215, 54, 50, null);
                                    }
                                } else if (gm.getPlayer(0).getTile(x).getStat() == 2) {
                                    if (x < 2) {
                                        g.drawImage(getTileImage("sh" + gm.getPlayer(0).getTile(x).getType()),
                                                1167 + (x * 61), 105, 54, 50, null);
                                    } else if (x < 4) {
                                        g.drawImage(getTileImage("sh" + gm.getPlayer(0).getTile(x).getType()),
                                                1045 + (x * 61), 160, 54, 50, null);
                                    } else {
                                        g.drawImage(getTileImage("sh" + gm.getPlayer(0).getTile(x).getType()),
                                                923 + (x * 61), 215, 54, 50, null);
                                    }
                                }

                            }
                        }
                        if (gm.getCurrPlayer() != 0) {
                            for (int x = 0; x < gm.getPlayer(0).getAllTiles().size(); x++) {
                                if (x < 2) {
                                    g.drawImage(getTileImage(gm.getPlayer(0).getTile(x).getType()), 1167 + (x * 61),
                                            105, 54, 50, null);
                                } else if (x < 4) {
                                    g.drawImage(getTileImage(gm.getPlayer(0).getTile(x).getType()), 1045 + (x * 61),
                                            160, 54, 50, null);
                                } else {
                                    g.drawImage(getTileImage(gm.getPlayer(0).getTile(x).getType()), 923 + (x * 61), 215,
                                            54, 50, null);
                                }
                            }
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

                            for (int x = 0; x < gm.getPlayer(1).getAllTiles().size(); x++) {
                                if (gm.getPlayer(0).getTile(x).getStat() == 1) {
                                    if (x < 2) {
                                        g.drawImage(getTileImage(gm.getPlayer(1).getTile(x).getType()), 1167 + (x * 61),
                                                307, 54, 50, null);
                                    } else if (x < 4) {
                                        g.drawImage(getTileImage(gm.getPlayer(1).getTile(x).getType()), 1045 + (x * 61),
                                                363, 54, 50, null);
                                    } else {
                                        g.drawImage(getTileImage(gm.getPlayer(1).getTile(x).getType()), 923 + (x * 61),
                                                417, 54, 50, null);
                                    }
                                } else if (gm.getPlayer(0).getTile(x).getStat() == 2) {
                                    if (x < 2) {
                                        g.drawImage(getTileImage("sh" + gm.getPlayer(1).getTile(x).getType()),
                                                1167 + (x * 61), 307, 54, 50, null);
                                    } else if (x < 4) {
                                        g.drawImage(getTileImage("sh" + gm.getPlayer(1).getTile(x).getType()),
                                                1045 + (x * 61), 363, 54, 50, null);
                                    } else {
                                        g.drawImage(getTileImage("sh" + gm.getPlayer(1).getTile(x).getType()),
                                                923 + (x * 61), 417, 54, 50, null);
                                    }
                                }
                            }

                        }
                        g.drawString("" + (gm.getPlayer(1).getSettlements()), (int) (895 * (getWidth() / 1238.0)),
                                (int) (328 * (getHeight() / 889.0)));

                        if (gm.getCurrPlayer() != 1) {
                            for (int x = 0; x < gm.getPlayer(1).getAllTiles().size(); x++) {
                                if (x < 2) {
                                    g.drawImage(getTileImage(gm.getPlayer(0).getTile(x).getType()), 1167 + (x * 61),
                                            307, 54, 50, null);
                                } else if (x < 4) {
                                    g.drawImage(getTileImage(gm.getPlayer(0).getTile(x).getType()), 1045 + (x * 61),
                                            363, 54, 50, null);
                                } else {
                                    g.drawImage(getTileImage(gm.getPlayer(0).getTile(x).getType()), 923 + (x * 61), 417,
                                            54, 50, null);
                                }
                            }
                        }
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

                            for (int x = 0; x < gm.getPlayer(2).getAllTiles().size(); x++) {
                                if (gm.getPlayer(0).getTile(x).getStat() == 1) {
                                    if (x < 2) {
                                        g.drawImage(getTileImage(gm.getPlayer(2).getTile(x).getType()), 1167 + (x * 61),
                                                510, 54, 50, null);
                                    } else if (x < 4) {
                                        g.drawImage(getTileImage(gm.getPlayer(2).getTile(x).getType()), 1045 + (x * 61),
                                                565, 54, 50, null);
                                    } else {
                                        g.drawImage(getTileImage(gm.getPlayer(2).getTile(x).getType()), 923 + (x * 61),
                                                619, 54, 50, null);
                                    }
                                } else if (gm.getPlayer(0).getTile(x).getStat() == 2) {
                                    if (x < 2) {
                                        g.drawImage(getTileImage("sh" + gm.getPlayer(2).getTile(x).getType()),
                                                1167 + (x * 61), 510, 54, 50, null);
                                    } else if (x < 4) {
                                        g.drawImage(getTileImage("sh" + gm.getPlayer(2).getTile(x).getType()),
                                                1045 + (x * 61), 565, 54, 50, null);
                                    } else {
                                        g.drawImage(getTileImage("sh" + gm.getPlayer(2).getTile(x).getType()),
                                                923 + (x * 61), 619, 54, 50, null);
                                    }
                                }
                            }
                        }
                        g.drawString("" + (gm.getPlayer(2).getSettlements()), (int) (895 * (getWidth() / 1238.0)),
                                (int) (512 * (getHeight() / 889.0)));

                        if (gm.getCurrPlayer() != 2) {
                            for (int x = 0; x < gm.getPlayer(2).getAllTiles().size(); x++) {
                                if (x < 2) {
                                    g.drawImage(getTileImage(gm.getPlayer(2).getTile(x).getType()), 1167 + (x * 61),
                                            510, 54, 50, null);
                                } else if (x < 4) {
                                    g.drawImage(getTileImage(gm.getPlayer(2).getTile(x).getType()), 1045 + (x * 61),
                                            565, 54, 50, null);
                                } else {
                                    g.drawImage(getTileImage(gm.getPlayer(2).getTile(x).getType()), 923 + (x * 61), 619,
                                            54, 50, null);
                                }
                            }
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
                            for (int x = 0; x < gm.getPlayer(3).getAllTiles().size(); x++) {
                                if (gm.getPlayer(0).getTile(x).getStat() == 1) {
                                    if (x < 2) {
                                        g.drawImage(getTileImage(gm.getPlayer(3).getTile(x).getType()), 1167 + (x * 61),
                                                711, 54, 50, null);
                                    } else if (x < 4) {
                                        g.drawImage(getTileImage(gm.getPlayer(3).getTile(x).getType()), 1045 + (x * 61),
                                                768, 54, 50, null);
                                    } else {
                                        g.drawImage(getTileImage(gm.getPlayer(3).getTile(x).getType()), 923 + (x * 61),
                                                822, 54, 50, null);
                                    }
                                } else if (gm.getPlayer(0).getTile(x).getStat() == 3) {
                                    if (x < 2) {
                                        g.drawImage(getTileImage(gm.getPlayer(3).getTile(x).getType()), 1167 + (x * 61),
                                                711, 54, 50, null);
                                    } else if (x < 4) {
                                        g.drawImage(getTileImage(gm.getPlayer(3).getTile(x).getType()), 1045 + (x * 61),
                                                768, 54, 50, null);
                                    } else {
                                        g.drawImage(getTileImage(gm.getPlayer(3).getTile(x).getType()), 923 + (x * 61),
                                                822, 54, 50, null);
                                    }
                                }
                            }
                        }
                        g.drawString("" + (gm.getPlayer(3).getSettlements()), (int) (895 * (getWidth() / 1238.0)),
                                (int) (696 * (getHeight() / 889.0)));
                        if (gm.getCurrPlayer() != 3) {
                            for (int x = 0; x < gm.getPlayer(3).getAllTiles().size(); x++) {
                                if (x < 2) {
                                    g.drawImage(getTileImage(gm.getPlayer(3).getTile(x).getType()), 1167 + (x * 61),
                                            711, 54, 50, null);
                                } else if (x < 4) {
                                    g.drawImage(getTileImage(gm.getPlayer(3).getTile(x).getType()), 1045 + (x * 61),
                                            768, 54, 50, null);
                                } else {
                                    g.drawImage(getTileImage(gm.getPlayer(3).getTile(x).getType()), 923 + (x * 61), 822,
                                            54, 50, null);
                                }
                            }
                        }
                        break;
                }
            }

            if (gm.startPlayer == 0) {
                g.drawImage(startTile, 955, 170, 85, 70, null); // ouch
            } else if (gm.startPlayer == 1) {
                g.drawImage(startTile, 955, 370, 85, 70, null); // ouch
            } else if (gm.startPlayer == 2) {
                g.drawImage(startTile, 955, 570, 85, 70, null); // ouch
            } else if (gm.startPlayer == 3) {
                g.drawImage(startTile, 955, 770, 85, 70, null); // ouch
            }
        }
    }

    public void drawBoard(Graphics g) {
        ArrayList<Section> tempSet = gm.getBoards();
        // System.out.println("SIZE: " + tempSet.size());
        g.drawImage(tempSet.get(0).getImage(), (int) (244 * (getWidth() / 1238.0)),
                (int) (254 * (getHeight() / 889.0)), (int) (279 * (getWidth() / 1238.0)),
                (int) (238 * (getHeight() / 889.0)), null);
        g.drawImage(tempSet.get(1).getImage(), (int) ((244 + 279 - 11) * (getWidth() / 1238.0)),
                (int) (254 * (getHeight() / 889.0)), (int) (279 * (getWidth() / 1238.0)),
                (int) (238 * (getHeight() / 889.0)), null);
        g.drawImage(tempSet.get(2).getImage(), (int) (244 * (getWidth() / 1238.0)),
                (int) ((254 + 237 - 5) * (getHeight() / 889.0)),
                (int) (279 * (getWidth() / 1238.0)), (int) (238 * (getHeight() / 889.0)), null);
        g.drawImage(tempSet.get(3).getImage(), (int) ((244 + 279 - 11) * (getWidth() / 1238.0)),
                (int) ((254 + 237 - 5) * (getHeight() / 889.0)), (int) (279 * (getWidth() / 1238.0)),
                (int) (238 * (getHeight() / 889.0)), null);
    }

    public void drawShaders(Graphics g) {
        // System.out.println("hello");
        if (gm.placed < 3) {
            for (Hex hx : gm.avaliable) {
                // System.out.println("hex -- (" + hx.getRow() + ", " + hx.getCol() + ")");
                if (hx.getRow() % 2 == 0) {
                    g.drawImage(colWhite, (int) (271 + 30 * (hx.getCol() / 2)), (int) (279 + 25 * hx.getRow()), 30, 31,
                            null);
                } else {
                    g.drawImage(colWhite, (int) (285 + 30 * ((hx.getCol() - 1) / 2)), (int) (279 + 25 * hx.getRow()),
                            30, 31,
                            null);
                }
            }
        }

        if (gm.placed < 3) {
            g.drawImage(darken, 684, 100, 150, 45, null);
        }

    }

    public void drawSettlements(Graphics g) {
        for (Hex hx : gm.bb.getHexes()) {
            // System.out.println("hex -- (" + hx.getRow() + ", " + hx.getCol() + ")");
            if (hx.getpNum() != -1) {
                switch (gm.getCurrPlayer()) {
                    case 0:
                        if (hx.getRow() % 2 == 0) {
                            g.drawImage(settColors.get(0), (int) (276 + 30 * (hx.getCol() / 2)),
                                    (int) (284 + 25 * hx.getRow()), 20, 20,
                                    null);
                        } else {
                            g.drawImage(settColors.get(0), (int) (290 + 30 * ((hx.getCol() - 1) / 2)),
                                    (int) (284 + 25 * hx.getRow()), 20, 20,
                                    null);
                        }
                        break;
                    case 1:
                        if (hx.getRow() % 2 == 0) {
                            g.drawImage(settColors.get(1), (int) (276 + 30 * (hx.getCol() / 2)),
                                    (int) (284 + 25 * hx.getRow()), 20, 20,
                                    null);
                        } else {
                            g.drawImage(settColors.get(1), (int) (290 + 30 * ((hx.getCol() - 1) / 2)),
                                    (int) (284 + 25 * hx.getRow()), 20, 20,
                                    null);
                        }
                        break;
                    case 2:
                        if (hx.getRow() % 2 == 0) {
                            g.drawImage(settColors.get(2), (int) (276 + 30 * (hx.getCol() / 2)),
                                    (int) (284 + 25 * hx.getRow()), 20, 20,
                                    null);
                        } else {
                            g.drawImage(settColors.get(2), (int) (290 + 30 * ((hx.getCol() - 1) / 2)),
                                    (int) (284 + 25 * hx.getRow()), 20, 20,
                                    null);
                        }
                        break;
                    case 3:
                        if (hx.getRow() % 2 == 0) {
                            g.drawImage(settColors.get(3), (int) (276 + 30 * (hx.getCol() / 2)),
                                    (int) (284 + 25 * hx.getRow()), 20, 20,
                                    null);
                        } else {
                            g.drawImage(settColors.get(3), (int) (290 + 30 * ((hx.getCol() - 1) / 2)),
                                    (int) (284 + 25 * hx.getRow()), 20, 20,
                                    null);
                        }
                        break;
                }
            }

        }
    }

    public boolean intpoint_inside_circle(int x, int y, intPoint center, int rad) {
        // System.out.println("please-----");
        int dist = (int) (Math.sqrt(Math.pow(x - center.x, 2) + Math.pow(y - center.y, 2)));
        // System.out.println(rad + " vs. " + dist);
        if (dist <= rad)
            return true;
        return false;
    }

    public int[] findCircle(int x, int y) {
        int[] cds = new int[2];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                //
                if (j % 2 == 0) {
                    if (intpoint_inside_circle(x, y, new intPoint(285 + 30 * i, 296 + 25 * j), 15)) {
                        cds[0] = i * 2;
                        cds[1] = j;
                    }

                    // }s[0] =
                } else {
                    if (intpoint_inside_circle(x, y, new intPoint(300 + 30 * i, 300 + 25 * j), 15)) {
                        cds[0] = 1 + i * 2;
                        cds[1] = j;
                    }
                }
            }
        }
        return cds;
    }

    // public boolean intpoint_inside_hexagon(int x, int y, intPoint square,
    // intPoint triangleUp, intPoint triangleDown) {
    // return false;
    // }

    // public boolean intpoint_inside_trigon(int x, int y, intPoint a, intPoint b,
    // intPoint c) {
    // int as_x = x - a.x;
    // int as_y = y - a.y;

    // boolean s_ab = (b.x - a.x) * as_y - (b.y - a.y) * as_x > 0;

    // if ((c.x - a.x) * as_y - (c.y - a.y) * as_x > 0 == s_ab)
    // return false;
    // if ((c.x - b.x) * (y - b.y) - (c.y - b.y) * (x - b.x) > 0 != s_ab)
    // return false;
    // return true;
    // }
}