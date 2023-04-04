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


    public void setpNum(int p){ 
        pNum = p;
    }

    public Hex getHex(){ }

    public int getCol(){
        return col;
    }

    public int getRow(){ 
        return row;
    }

    public int getFree(){ 
        return free;
    }

    public int getSec(){ }
}