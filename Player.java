import java.util.ArrayList;

public class Player {
    private int settlements = 40;
    private Card chosen;
    private ArrayList<Hex> tiles;
    private ArrayList<Integer> points;
    private ArrayList<Hex> placed;
    public int sec1, sec2, sec3, sec4;

    public Player() {
        tiles = new ArrayList<Hex>();
        placed = new ArrayList<>();
        points = new ArrayList<Integer>();
        sec1=0;
        sec2=0;
        sec3=0;
        sec4=0;

    }

    public int getS1(){
        return sec1;
    }

    public int getS2(){
        return sec2;
    }

    public int getS3(){
        return sec3;
    }

    public int getS4(){
        return sec4;
    }

    public int getS(int num){
        if(num==0){
            return sec1;
        }
        if(num==1){
            return sec2;
        }
        if(num==2){
            return sec3;
        }
        if(num==3){
            return sec4;
        }
        else{
            return -1;
        }
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
        t.statStill();
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
