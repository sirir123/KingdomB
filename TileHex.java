public class TileHex extends Hex {
    private int amount, status;

    public TileHex(String t, int c, int r, int s) {
        super(t, c, r, s);
        amount = 2;
        setpNum(-1);
        status = 0;
        occ = 4;
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

    public int getAmount() {
        return amount;
    }
}