import java.util.*;
import java.util.ArrayList;

public class Game {
    private int currPlayer = 0;
    private ArrayList<Player> players;
    private ArrayList<Card> deck;
    private ArrayList<Card> discard;
    private int discTiles;
    private Board bb;

    public Game(int amt) {
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

    }

    public int getNumPly() {
        return players.size();
    }

    public void setPly(int num) {
        currPlayer = num;
    }

    public void discard(Card c) {
        discard.add(deck.get(c)); // issue w/ get
        deck.remove(c);
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public void replaceDeck() {
        deck.addAll(discard);
        discard.clear();
        deck.shuffleDeck(); // doesn't exist
    }

    public Card drawDeck() {
        return deck.get(0);
    }

    public void nextTurn() {
        for (int i = 0; i < players.get(currPlayer).getAllTiles().size(); i++) {
            players.get(currPlayer).getAllTiles().get(i).statUnused();
        }
        players.get(currPlayer).discard(players.get(currPlayer).getChosen()); // doesn't exist
        players.get(currPlayer).setChosen(drawDeck());
        if (currPlayer < players.size()) {
            currPlayer++;
        } else {
            currPlayer = 0;
        }

    }
}