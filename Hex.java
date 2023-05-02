import java.util.ArrayList;

public class Hex {
    private String type;
    private int col, row, sec;
    protected int pNum;// -1 is none, 0-3 players
    protected int occ; // int for who is occupying -1 empty, 0-3 player, tileHex 4

    private int amount, status;//status 0-3

    private ArrayList<Hex> neighbors;

    public Hex(String t, int c, int r, int s) {
        type = t;
        col = c;
        row = r;
        sec = s;
        occ = -1;
        pNum = -1;
        neighbors = new ArrayList<Hex>();

        amount = -1;
        status = -1;

        if (t.equals("tiH") || t.equals("tiO") || t.equals("tiG") || t.equals("tiB")) {
            amount = 2;
            status = 0;
            occ = 4;
        }
    }

    public Hex(String t, int c, int r) {
        type = t;
        col = c;
        row = r;
        occ = -1;
        pNum = -1;
        neighbors = new ArrayList<Hex>();
    }

    public void setCoords(int c, int r) {
        col = c;
        row = r;
    }

    public void setpNum(int p) {
        pNum = p;
    }

    public int getpNum() {
        return pNum;
    }

    public void setNeighbors(int dir, Hex h) {
        neighbors.set(dir, h);
    }

    public void setOcc(int num) {
        if (5 > num && num > -2) {// makes sure occupying -1 empty, 0-3 player, tileHex 4
            occ = num;
        }
    }

    public void update(int x, int y) {
        setCoords(col + x, row + y);

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
        return sec;
    }

    public String getType() {
        return type;
    }

    public void statStill() {
        status = 0;
    }

    public void statUnused() {
        status = 1;
    }

    public void statUsed() {
        status = 2;
    }

    public void statDiscard() {
        status = 3;
    }

    public int getStat() {
        return status;
    }

    public void minusAmount() {
        if (amount > 0) {
            amount--;
        }
    }

    public int getAmount() {
        return amount;
    }

    public String toString() {
        return type + ": (" + row + ", " + col + ") " + sec;
    }
}