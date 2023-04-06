import java.util.ArrayList;

public class Player {
    private int settlements = 40; // dont need color or num
    private static int pNums = 0; // what does this do?
    private Card chosen;
    private ArrayList<TileHex> tiles = new ArrayList<TileHex>();
    private ArrayList<Integer> points = new ArrayList<Integer>();

    public Player() {

    }

    public int getSett() {
        return settlements;
    }

    public ArrayList<TileHex> getAllTiles() {
        return tiles;
    }

    public TileHex getTile(int num) {
        return tiles.get(num);
    }

    public void addTile(TileHex t) {
        tiles.add(t);
    }

    public void addPoints(int num) {
        points.add(num);
    }

    public ArrayList<Integer> getAllPoints() {
        return points;
    }

    public Integer getPoint(int num) {
        return points.get(num);
    }

    public void setChosen(Card c) {
        chosen = c;
    }

    public Card getChosen() {
        return chosen;
    }

}