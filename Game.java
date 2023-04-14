import java.util.*;
import java.util.ArrayList;

public class Game {
    private int currPlayer = 0;
    private ArrayList<Player> players;
    private ArrayList<Card> deck;
    private ArrayList<Card> discard;
    private int discTiles;
    private Board bb;
    private Section board1;
    private Section board2;
    private Section board3;
    private Section board4;

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

    public Player getPlayer(int n) {
        return players.get(n);
    }

    public int getNumPly() {
        return players.size();
    }

    public void setPly(int num) {
        currPlayer = num;
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
            Collections.shuffle(deck);
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