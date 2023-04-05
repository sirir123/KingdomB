public class Game{
     private int currPlayer;
    private ArrayList<Player>() players;
    private ArrayList<Card>() deck;
    private ArrayList<Card>() discard;
    private int discTiles;
    private Board bb;
    private ScoreCard sc;

    public Game (int amt){
        bb = new Board();
        deck = new ArrayList<Card>();
        for (int i = 0; i < 5; i++){
            for (int x = 0; x < 5; x++){
                if (i = 0) { deck.add(new Card("des"))}
                if (i = 1) { deck.add(new Card("for"))}
                if (i = 2) { deck.add(new Card("flwr"))}
                if (i = 3) { deck.add(new Card("cnyn"))}
                if (i = 4) { deck.add(new Card ("grs"))}
            }
        }
    }
}