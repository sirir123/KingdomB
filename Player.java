import java.util.ArrayList;

public class Player {
    private int settlements = 40;
    private Card chosen;
    private ArrayList<TileHex> tiles;
    private ArrayList<Integer> points;

    public Player() {
        tiles = new ArrayList<TileHex>();

        points = new ArrayList<Integer>();
    }

    public int getSettlements () { return settlements;}

    public void setSettlements(int x) { settlements = x;}

    public ArrayList<TileHex> getAllTiles() { return tiles; }

    public TileHex getTile(int num) { return tiles.get(num); }

    public void addTile(TileHex t) { tiles.add(t); }

    public void addPoints(int num) { points.add(num); }

    public ArrayList<Integer> getAllPoints() { return points;}

    public Integer getPoint(int num) { return points.get(num); }

    public void setChosen(Card c) { chosen = c; }

    public Card getChosen() { return chosen; }

    public String getType() {return "" + chosen + ""; }

    public void useSettlement(){ settlements--; }//use one settlement

}
