import java.util.ArrayList;

public class Player {
    private int settlements = 40;
    private Card chosen;
    private ArrayList<Hex> tiles;
    private ArrayList<Integer> points;
    private ArrayList<Hex> placed;

    public Player() {
        tiles = new ArrayList<Hex>();
        placed = new ArrayList<>();
        points = new ArrayList<Integer>();
    }

    public int getSettlements() {
        return settlements;
    }

    public void setSettlements(int x) {
        settlements = x;
    }

    public ArrayList<Hex> getAllTiles() {
        return tiles;
    }

    public Hex getTile(int num) {
        return tiles.get(num);
    }

    public void addTile(Hex t) {
        t.statUsed();
        tiles.add(t);
    }

    public void addPlaced(Hex t) {
        placed.add(t);
    }

    public ArrayList<Hex> getPlaced() {
        return placed;
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

    public String getType() {
        return "" + chosen + "";
    }

    public void useSettlement() {
        settlements--;
    }// use one settlement

}
