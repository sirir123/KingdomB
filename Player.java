import java.util.ArrayList;

public class Player {
    public int settlements = 40;
    private Card chosen;
    private ArrayList<TileHex> tiles = new ArrayList<TileHex>();
    private ArrayList<Integer> points = new ArrayList<Integer>();

    public Player() {
        ArrayList<TileHex> tiles = new ArrayList<TileHex>();
        ArrayList<Integer> points = new ArrayList<Integer>();
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