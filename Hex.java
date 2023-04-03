public class Hex{
    private String type;
    private int col,  row, sec;
    protected int pNum;
    private boolean free;
    private ArrayList<Hex>() neighbors;

    public Hex(String t, int c, int r, int s){
        type = t;
        col = c;
        row = r;
        sec = s;
        free = true;
        neighbors = new ArrayList<Hex>();
        // instantiate neighbors
    }


    public void setpNum(int p){ }

    public Hex getHex(){ }

    public Hex neighbor (int dir, int num){ }

    public int getCol(){ }

    public int getRow(){ }

    public int getFree(){ }

    public int getSec(){ }
}