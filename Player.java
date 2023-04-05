public class Player{
    private int settlements=40;
    private int num;
    private String color;
    private String type;
    private ArrayList<TileHex> tiles= new ArrayList<TileHex>();
    private ArrayList<Integer> points= new ArrayList<Integer>();

    public int getSett(){
        return settlements;
    }

    public ArrayList<TileHex> getAllTiles(){
        return tiles;
    }

    public TileHex getTile(int num){
        return tiles.get(num);
    }

    public void addTile(TileHex t){
        tiles.add(t);
    }

    public void addPoints(int num){
        points.add(num);
    }

    public ArrayList<Integer> getAllPoints(){
        return points;
    }

    public Integer getPoint(int num){
        return points.get(num);
    }

    public void setChosen(Card c){
        type=c.getTerr();
    }


    

}