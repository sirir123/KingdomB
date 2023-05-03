import java.awt.image.BufferedImage;
import java.util.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Game {
    private int currPlayer;
    public int startPlayer;// should be random

    public int state; // 0 terrain card, 1 tiles
    public HashSet<Hex> avaliable;
    public int placed;

    private ArrayList<Player> players;
    private ArrayList<Card> deck;
    private ArrayList<Card> discard;
    public int discTiles;
    public Board bb;
    private ArrayList<Section> boards;

    public Game(int amt) {
        bb = new Board();
        currPlayer = (int) (Math.random() * amt);
        startPlayer = currPlayer;
        players = new ArrayList<>();
        bb = new Board(); // constructor
        deck = new ArrayList<Card>();
        discard = new ArrayList<Card>();
        placed = 0;
        for (int i = 0; i < 5; i++) {
            for (int x = 0; x < 5; x++) {
                if (i == 0) {
                    deck.add(new Card("des"));
                }
                if (i == 1) {
                    deck.add(new Card("for"));
                }
                if (i == 2) {
                    deck.add(new Card("flwr"));
                }
                if (i == 3) {
                    deck.add(new Card("cnyn"));
                }
                if (i == 4) {
                    deck.add(new Card("grs"));
                }
            }
        }
        for (int i = 0; i < amt; i++) {
            players.add(new Player());
        }
        // setting up the boards
        boards = new ArrayList<>();

    }

    public void updateAvaliable(boolean terrain) {
        System.out.println("update");
        avaliable = new HashSet<Hex>();
        // if (terrain) {

        if (players.get(currPlayer).getPlaced().size() >= 1) {
            System.out.println("update1");
            for (Hex hx : players.get(currPlayer).getPlaced()) {
                System.out.println("Neighbors: " + hx.getNeighbors().toString());
                for (Hex h : hx.getNeighbors()) {
                    if (h != null && h.getpNum() == -1
                            && h.getType().equals(players.get(currPlayer).getChosen().getTerr())) {
                        avaliable.add(h);
                    }
                }
            }
        }

        if (avaliable.size() < 1) {
            for (Hex hx : bb.getHexes()) {
                if (hx.getType().equals(players.get(currPlayer).getChosen().getTerr()) && hx.getpNum() == -1) {
                    avaliable.add(hx);
                }
            }
        }
    }

    public void updateAvaliable() {
        HashSet<Hex> tempAvaliable = new HashSet<Hex>();
        for (Hex hx : avaliable) {
            if (hx.getpNum() == -1) {
                tempAvaliable.add(hx);
            }
        }
        avaliable = tempAvaliable;
        // System.out.println("BOARD: " + bb.getHexes().size());
        // System.out.println("AVALAIABLE: " + avaliable.size());
    }

    public boolean avaliable(int r, int c) {
        if (avaliable != null) {
            for (Hex hx : avaliable) {
                if (hx.getRow() == r && hx.getCol() == c) {
                    bb.updateHex(r, c, currPlayer);
                    players.get(currPlayer).addPlaced(hx);
                    players.get(currPlayer).setSettlements(players.get(currPlayer).getSettlements() - 1);
                    // updateAvaliable(true);
                    return true;
                }
            }
        }

        // updateAvaliable(true);
        return false;
    }

    public ArrayList<Section> getBoards() {
        return boards;
    }

    public int getCurrPlayer() {
        return currPlayer;
    }

    public void setBoards(ArrayList<BoardImage> imgs) {
        // System.out.println("I HATE");
        String[][] hexMapBoat = { { "grs", "grs", "for", "for", "for", "wat", "grs", "for", "for", "flwr" },
                { "grs", "flwr", "for", "for", "wat", "grs", "for", "for", "flwr", "flwr" },
                { "grs", "flwr", "flwr", "for", "wat", "grs", "grs", "flwr", "flwr", "flwr" },
                { "flwr", "flwr", "for", "for", "wat", "grs", "mt", "flwr", "des", "des" },
                { "cnyn", "flwr", "cas", "for", "wat", "grs", "des", "des", "des", "des" },
                { "cnyn", "cnyn", "for", "wat", "grs", "grs", "mt", "mt", "des", "des" },
                { "cnyn", "cnyn", "wat", "wat", "wat", "grs", "des", "des", "des", "cnyn" },
                { "wat", "wat", "grs", "grs", "wat", "wat", "tiB", "cnyn", "mt", "cnyn" },
                { "wat", "des", "cas", "grs", "wat", "mt", "wat", "cnyn", "cnyn", "cnyn" },
                { "wat", "des", "des", "wat", "wat", "wat", "wat", "cnyn", "cnyn", "cnyn" } };

        String[][] hexMapGrass = { { "des", "des", "cnyn", "wat", "wat", "for", "for", "for", "grs", "grs" },
                { "des", "cas", "cnyn", "wat", "for", "for", "for", "tiG", "grs", "grs" },
                { "cnyn", "cnyn", "cnyn", "flwr", "flwr", "flwr", "for", "cnyn", "flwr", "flwr" },
                { "cnyn", "cnyn", "flwr", "flwr", "wat", "des", "des", "cnyn", "cnyn", "flwr" },
                { "cnyn", "grs", "grs", "wat", "flwr", "flwr", "des", "des", "cnyn", "cnyn" },
                { "grs", "grs", "tiG", "flwr", "wat", "flwr", "wat", "des", "des", "cnyn" },
                { "grs", "grs", "grs", "for", "flwr", "flwr", "wat", "wat", "des", "des" },
                { "grs", "grs", "for", "for", "mt", "wat", "wat", "wat", "des", "wat" },
                { "grs", "mt", "for", "for", "wat", "wat", "wat", "wat", "wat", "wat" },
                { "for", "for", "for", "wat", "wat", "wat", "wat", "wat", "wat", "wat" } };

        String[][] hexMapHorse = { { "cnyn", "cnyn", "cnyn", "des", "des", "wat", "des", "des", "des", "des" },
                { "mt", "mt", "cnyn", "des", "des", "wat", "des", "des", "des", "des" },
                { "mt", "mt", "cnyn", "mt", "mt", "wat", "des", "des", "tiH", "flwr" },
                { "mt", "cnyn", "mt", "mt", "wat", "mt", "des", "flwr", "flwr", "flwr" },
                { "cnyn", "cnyn", "for", "for", "wat", "mt", "mt", "cnyn", "flwr", "flwr" },
                { "cnyn", "for", "for", "wat", "cnyn", "cnyn", "cnyn", "mt", "flwr", "flwr" },
                { "cnyn", "tiH", "for", "for", "wat", "flwr", "flwr", "flwr", "flwr", "flwr" },
                { "grs", "grs", "for", "wat", "grs", "cas", "grs", "flwr", "grs", "for" },
                { "grs", "grs", "for", "for", "wat", "grs", "grs", "grs", "grs", "for" },
                { "grs", "grs", "for", "for", "wat", "grs", "grs", "grs", "for", "for" } };

        String[][] hexMapOracle = { { "grs", "grs", "grs", "for", "for", "wat", "grs", "for", "for", "for" },
                { "grs", "grs", "grs", "cas", "for", "wat", "grs", "for", "for", "for" },
                { "grs", "flwr", "flwr", "grs", "for", "for", "wat", "grs", "grs", "for" },
                { "flwr", "flwr", "cnyn", "grs", "for", "wat", "flwr", "tiO", "for", "for" },
                { "flwr", "flwr", "flwr", "cnyn", "cnyn", "wat", "flwr", "flwr", "wat", "wat" },
                { "mt", "mt", "cnyn", "grs", "grs", "wat", "wat", "wat", "des", "des" },
                { "cnyn", "cnyn", "cnyn", "mt", "grs", "flwr", "flwr", "flwr", "des", "des" },
                { "cnyn", "cnyn", "cas", "des", "mt", "des", "flwr", "flwr", "cnyn", "cnyn" },
                { "wat", "wat", "wat", "des", "des", "des", "des", "mt", "cnyn", "cnyn" },
                { "wat", "wat", "wat", "wat", "des", "des", "des", "des", "des", "cnyn" } };

        ArrayList<BoardImage> tempList = imgs;
        for (int i = 0; i < 4; i++) {
            BoardImage tempImage = tempList.remove(0);
            if (tempImage.type == 0) { // boat
                boards.add(new Section(tempImage, hexMapBoat, i));
            } else if (tempImage.type == 1) { // grass
                boards.add(new Section(tempImage, hexMapGrass, i));
            } else if (tempImage.type == 2) { // horse
                boards.add(new Section(tempImage, hexMapHorse, i));
            } else if (tempImage.type == 3) { // oracle
                boards.add(new Section(tempImage, hexMapOracle, i));
            }
        }

        bb.connect(boards);
    }

    public Player getPlayer(int n) {
        return players.get(n);
    }

    public int getNumPly() {
        return players.size();
    }

    public void setPly(int num) {
        currPlayer = num;
    }

    public int getDeckSize(boolean notDisc) { // the size of each deck, true is reg, false is discarded deck
        if (notDisc)
            return deck.size();
        else
            return discard.size();
    }

    public void discard(Card c) {
        discard.add(players.get(currPlayer).getChosen());
        players.get(currPlayer).setChosen(drawDeck());
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public void replaceDeck() {
        deck.addAll(discard);
        discard.clear();
        shuffleDeck();
    }

    public Card drawDeck() {
        if (deck.size() < 1) {
            replaceDeck();
        }
        return deck.remove(0);

    }

    public void nextTurn() {
        for (int i = 0; i < players.get(currPlayer).getAllTiles().size(); i++) { // set tiles to unused
            players.get(currPlayer).getAllTiles().get(i).statUnused();
        }
        discard(players.get(currPlayer).getChosen()); // discard + draw
        placed = 0;
        // update settlements
        if (currPlayer < players.size() - 1) { // change curr player
            currPlayer++;
        } else {
            currPlayer = 0;
        }
        updateAvaliable(true);
    }

    public void boatT(Player p, Hex exist, Hex water, int num) {// int is index of chosen token
        if (exist.getFree() == players.indexOf(p) && water.getFree() == -1
                && p.getTile(num).getType().equals("boat") && p.getTile(num).getStat() == 1
                && water.getType().equals("wat")) {
            // check adjacency of chosen water hex!!!
            water.setOcc(players.indexOf(p));
            exist.setOcc(-1);
            exist.setpNum(-1);
            water.setpNum(players.indexOf(p));
            p.getTile(num).statUsed();
            System.out.println("player" + players.indexOf(p) + "used boatT");
        }
    }// get existing settlement and move to water

    public void paddockT(Player p, Hex exist, Hex next, int num) { // check if next is 2 hexes away in a straight
                                                                   // line(c:4, -4, -2, +2, 0 & r: 0, -2, +2)
        if (((exist.getCol() - next.getCol() == 4 || exist.getCol() - next.getCol() == -4)
                && exist.getRow() - next.getRow() == 0)
                || ((exist.getCol() - next.getCol() == 2 || exist.getCol() - next.getCol() == -2)
                        && exist.getRow() - next.getRow() == -2)
                || ((exist.getCol() - next.getCol() == 2 || exist.getCol() - next.getCol() == -2)
                        && exist.getRow() - next.getRow() == 2)) {
            if (exist.getFree() == players.indexOf(p) && next.getFree() == -1
                    && p.getTile(num).getType().equals("paddock") && p.getTile(num).getStat() == 1
                    && !next.getType().equals("wat") && !next.getType().equals("mt")) {
                exist.setOcc(-1);
                exist.setpNum(-1);
                next.setOcc(players.indexOf(p));
                next.setpNum(players.indexOf(p));
                p.getTile(num).statUsed();
                System.out.println("player" + players.indexOf(p) + "used paddockT");
            }
        }
    }// get existing settlement and jump 2 hexes straight line

    public void oracleT(Player p, Hex next, int num) {
        if (next.getFree() == -1 && p.getSettlements() > 0 && p.getType() == next.getType()
                && p.getTile(num).getType().equals("oracle") && p.getTile(num).getStat() == 1) {
            // check adjacency of chosen hex!!!
            next.setOcc(players.indexOf(p));
            next.setpNum(players.indexOf(p));
            p.useSettlement();
            p.getTile(num).statUsed();
            System.out.println("player" + players.indexOf(p) + "used oracleT");
        }
    }// place new settlement on terrain as current card

    public void farmT(Player p, Hex grass, int num) {
        if (grass.getFree() == -1 && p.getSettlements() > 0 && "grs" == grass.getType()
                && p.getTile(num).getType().equals("farm") && p.getTile(num).getStat() == 1) {
            // check adjacency of chosen grass hex!!!
            grass.setOcc(players.indexOf(p));
            grass.setpNum(players.indexOf(p));
            p.useSettlement();
            p.getTile(num).statUsed();
            System.out.println("player" + players.indexOf(p) + "used farmT");
        }
    }// place new settlement on grass hex

    public void collectTile(Hex adj, Hex t) {
        if (checkAdj(adj, t)) {
            if (adj.getAmount() > 0 && t.getFree() > -1 && (t.getType().equals("tiH") || t.getType().equals("tiO")
                    || t.getType().equals("tiG") || t.getType().equals("tiB"))) {
                players.get(t.getFree()).addTile(adj);
                adj.minusAmount();
                System.out.println("player " + t.getFree() + "collected tile:" + adj.getType());
            }
        }
    }

    public boolean checkAdj(Hex a, Hex b) {
        if (a.getCol() - b.getCol() > -3 && a.getCol() - b.getCol() < 3 && a.getRow() - b.getRow() > -2
                && a.getRow() - b.getRow() < 2) {
            return true;
        }
        return false;
    }
}