public class ScoreCard{
    private String type;

    public ScoreCard(){
        type="lord, workers, discoverers";
        //doesn't matter much now bc we're only using 3 scoring cards anyways
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
        //check each section for top 2 amount of settlements
    }

    public int workers(Player p){
        //gain 1 gold for a settlement next to location or castle hex
    }

    public int discoverers(Player p){
        //gain 1 gold for every settlement on horizontal line
    }

    public int castle(Player p){
        //3 gold per castle hex
    }
}