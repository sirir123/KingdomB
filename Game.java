import java.awt.image.BufferedImage;
import java.util.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Game {
    private int currPlayer = 0;
    private ArrayList<Player> players;
    private ArrayList<Card> deck;
    private ArrayList<Card> discard;
    public int discTiles;
    private Board bb;
    private ArrayList<Section> boards;

    public Game(int amt) {
        players = new ArrayList<>();
        bb = new Board(); // constructor
        deck = new ArrayList<Card>();
        discard = new ArrayList<Card>();
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
    }

    public void setBoards(ArrayList<BoardImage> imgs) {
        ArrayList<BoardImage> tempList = imgs;
        for (int i = 0; i < boards.size(); i++) {
            BoardImage tempImage = tempList.remove(0);
            if (tempImage.type == 0) { // boat

            } else if (tempImage.type == 0) { // grass

            } else if (tempImage.type == 0) { // horse

            } else if (tempImage.type == 0) { // oracle

            }
        }
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
            deck = discard;
            shuffleDeck();
            discard = new ArrayList<Card>();
        }
        return deck.get(0);
    }

    public void nextTurn() {
        for (int i = 0; i < players.get(currPlayer).getAllTiles().size(); i++) { // set tiles to unused
            players.get(currPlayer).getAllTiles().get(i).statUnused();
        }
        discard(players.get(currPlayer).getChosen()); // discard + draw
        players.get(currPlayer).setSettlements(players.get(currPlayer).getSettlements() - 3); // update settlements
        if (currPlayer < players.size() - 1) { // change curr player
            currPlayer++;
        } else {
            currPlayer = 0;
        }
    }
}