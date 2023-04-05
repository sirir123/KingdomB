public class ScoreCard{
    private String type;

    public ScoreCard(){
        
    }

    public int score(Player p){
        int gold=0;
        gold+=lords(p);
        gold+=workers(p);
        gold+=discoverers(p);
        gold+=castle(p);
        return gold;
    }

    public int lords(Player p){

    }

    public int workers(Player p){

    }

    public int discoverers(Player p){

    }

    public int castle(Player p){

    }
}