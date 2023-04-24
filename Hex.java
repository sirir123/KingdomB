import java.util.ArrayList;

public class Hex {
    private String type;
    private int col, row, sec;
    protected int pNum;//-1 is none, 0-3 players
    protected int occ; // int for who is occupying -1 empty, 0-3 player, tileHex 4
    private ArrayList<Hex> neighbors;

    public Hex(String t, int c, int r, int s) {
        type = t;
        col = c;
        row = r;
        sec = s;
        occ = -1;
        neighbors = new ArrayList<Hex>();
    }

    public Hex(String t, int c, int r) {
        type = t;
        col = c;
        row = r;
        occ = -1;
        neighbors = new ArrayList<Hex>();
    }

    public void setCoords(int c, int r) {
        col = c;
        row = r;
    }

    public void setpNum(int p) {
        pNum = p;
    }

    public void setNeighbors(int dir, Hex h) {
        neighbors.set(dir, h);
    }

    public void setOcc(int num){
        if(5>num && num>-2){//makes sure occupying -1 empty, 0-3 player, tileHex 4
        occ=num;
        }
    }

    public void multiply(int x, int y) {
        setCoords(col * x, row * y);

    }

    public Hex getHex() {
        return null;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getFree() {
        return occ;
    }

    public int getSec() {
        return -1;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return type + ": (" + row + ", " + col + ")";
    }
}