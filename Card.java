public class Card{
    private String terr = ""; // grass, canyon, sand, flower, forest
    
    public Card(String t){
        terr = t;
    }

    public String getTerr() {
        return terr;
    }

    public void setTerr(String change) {
        terr = change;
    }

}