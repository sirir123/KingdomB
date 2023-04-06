import java.util.ArrayList;

public class Hex {
    private String type;
    private int col, row, sec;
    protected int pNum;
    private int occ; // int for who is occupying -1 empty, 0-3 player
    private ArrayList<Hex> neighbors;

    public Hex(String t, int c, int r, int s) {
        type = t;
        col = c;
        row = r;
        sec = s;
        // free = true;
        neighbors = new ArrayList<Hex>();
        // instantiate neighbors
    }

    public void setpNum(int p) {
        pNum = p;
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
}