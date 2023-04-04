public class Player{
    private int settlements=40;
    private int num;
    private String color;
    private String type;// or Card chosen?
    private ArrayList<TileHex> tiles= new ArrayList<TileHex>();
    private ArrayList<Integer> points= new ArrayList<Integer>();

    public int getSett(){
        return settlements;
    }

    public ArrayList<TileHex> getTiles(){
        return tiles;
    }

    public void addTile(TileHex t){
        tiles.add(t);
    }

    public void addPoints(int num){
        points.add(num);
    }

    public void setChosen(Card c){
        type=c.getTerr();
    }


    

}