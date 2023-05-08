import java.awt.image.BufferedImage;
import java.util.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Game {
    private int num;
    private int currPlayer;
    public int startPlayer;// should be random
    public int prevPlayer;

    public int state; // 0 terrain card, 1 tiles
    public HashSet<Hex> avaliable;
    public int placed;
    public int stat; //for boat and horse, -1 not used, 0 tile picked, 1 org picked, then back to -1
    public Hex org; //storing org tile picked when boat or horse

    private ArrayList<Player> players;
    public ArrayList<Player> plyOrder;
    private ArrayList<Card> deck;
    private ArrayList<Card> discard;
    public int discTiles;
    public Board bb;
    private ArrayList<Section> boards;

    public boolean end = false;

    public Game(int amt) {
        num = amt;
        bb = new Board();
        currPlayer = (int) (Math.random() * amt);
        startPlayer = currPlayer;

        if(startPlayer == 0){
            prevPlayer = amt-1;
        }else{
            prevPlayer = startPlayer -1;
        }
        players = new ArrayList<>();
        bb = new Board(); // constructor
        deck = new ArrayList<Card>();
        discard = new ArrayList<Card>();
        placed = 0;
        stat = -1;
        //org = new Hex();
        for (int i = 0; i < 5; i++) {
            for (int x = 0; x < 5; x++) {
                if (i == 0) {
                    deck.add(new Card("des"));
                }
                if (i == 1) {
                    deck.add(new Card("for"));
                }
                if (i == 2) {
                    deck.add(new Card("flwr"));
                }
                if (i == 3) {
                    deck.add(new Card("cnyn"));
                }
                if (i == 4) {
                    deck.add(new Card("grs"));
                }
            }
        }
        for (int i = 0; i < amt; i++) {
            players.add(new Player());
        }
        // setting up the boards
        boards = new ArrayList<>();

    }

    public boolean tilesAvaliable(){ //whether any of curr players tiles can be used
        if(players.get(currPlayer).getSettlements() == 0){
            for(Hex ti: players.get(currPlayer).getAllTiles()){
                if(ti!= null && ti.getStat() == 1 && (ti.getType().equals("tiH")||ti.getType().equals("tiB")))return true;
            }
        }else if(placed == 0 || placed == 3){
            for(Hex ti: players.get(currPlayer).getAllTiles()){
                if(ti!= null && ti.getStat() == 1)return true;
            }
        }
        
        return false;
    }
    
    public void updateAvaliable(boolean terrain) {

        avaliable = new HashSet<Hex>();
        if (terrain) {

            if (players.get(currPlayer).getPlaced().size() >= 1) {

                for (Hex hx : players.get(currPlayer).getPlaced()) {

                    for (Hex h : hx.getNeighbors()) {
                        if (h != null && h.getpNum() == -1
                                && h.getType().equals(players.get(currPlayer).getChosen().getTerr())) {
                            avaliable.add(h);
                        }
                    }
                }
            }

            if (avaliable.size() < 1) {
                for (Hex hx : bb.getHexes()) {
                    if (hx.getType().equals(players.get(currPlayer).getChosen().getTerr()) && hx.getpNum() == -1) {
                        avaliable.add(hx);
                    }
                }
            }
        }else{
            avaliable = new HashSet<Hex>();
        }   
    }

    public void updateAvaliable(String type, Hex tile, Hex org){
        System.out.println("update: " + type);
        avaliable = new HashSet<Hex>();
        if(tile.getStat() == 1){
                if(type == "tiH"){
                System.out.println("HORSY"  );
                if(org ==  null){
                    for(Hex hx: players.get(currPlayer).getPlaced()){
                        avaliable.add(hx);
                    }    
                }else{
                    System.out.println("horse shit");
                    for(Hex hx: bb.getHexes()){
                        if(hx.getpNum() == -1 && !hx.getType().equals("mt") && !hx.getType().equals("wat") && !hx.getType().equals("tiG")&& !hx.getType().equals("tiH")&& !hx.getType().equals("tiO")&& !hx.getType().equals("tiB")&& hx.getCol() == (org.getCol() - 2) && hx.getRow() == (org.getRow()-2)){
                            avaliable.add(hx);
                        }else if (hx.getpNum() == -1 && !hx.getType().equals("mt") && !hx.getType().equals("wat") && !hx.getType().equals("tiG")&& !hx.getType().equals("tiH")&& !hx.getType().equals("tiO")&& !hx.getType().equals("tiB") && hx.getCol() == (org.getCol() + 2) && hx.getRow() == (org.getRow() - 2)){
                            avaliable.add(hx);
                        }else if (hx.getpNum() == -1 && !hx.getType().equals("mt") && !hx.getType().equals("wat") && !hx.getType().equals("tiG")&& !hx.getType().equals("tiH")&& !hx.getType().equals("tiO")&& !hx.getType().equals("tiB") && hx.getCol() == (org.getCol() + 4) && hx.getRow() == org.getRow()){
                            avaliable.add(hx);
                        }else if (hx.getpNum() == -1 && !hx.getType().equals("mt") && !hx.getType().equals("wat")  && !hx.getType().equals("tiG")&& !hx.getType().equals("tiH")&& !hx.getType().equals("tiO")&& !hx.getType().equals("tiB")&& hx.getCol() == (org.getCol() + 2) && hx.getRow() == (org.getRow() + 2)){
                            avaliable.add(hx);
                        }else if (hx.getpNum() == -1 && !hx.getType().equals("mt") && !hx.getType().equals("wat")  && !hx.getType().equals("tiG")&& !hx.getType().equals("tiH")&& !hx.getType().equals("tiO")&& !hx.getType().equals("tiB")&& hx.getCol() == (org.getCol() - 2) && hx.getRow() == (org.getRow() + 2)){
                            avaliable.add(hx);
                        }else if (hx.getpNum() == -1 && !hx.getType().equals("mt") && !hx.getType().equals("wat")  && !hx.getType().equals("tiG")&& !hx.getType().equals("tiH")&& !hx.getType().equals("tiO")&& !hx.getType().equals("tiB")&& hx.getCol() == (org.getCol()-4) && hx.getRow() == (org.getRow())){
                            avaliable.add(hx);
                        }
                    }
                    System.out.println("AVALIABLE: " + avaliable.toString());
                }
                
                

            }else if(type == "tiO" && players.get(currPlayer).getSettlements() > 0){
                System.out.println("OraclY");
                // if (terrain) {

                if (players.get(currPlayer).getPlaced().size() >= 1) {

                    for (Hex hx : players.get(currPlayer).getPlaced()) {

                        for (Hex h : hx.getNeighbors()) {
                            if (h != null && h.getpNum() == -1
                                    && h.getType().equals(players.get(currPlayer).getChosen().getTerr())) {
                                avaliable.add(h);
                            }
                        }
                    }
                }

                if (avaliable.size() < 1) {
                    for (Hex hx : bb.getHexes()) {
                        if (hx.getType().equals(players.get(currPlayer).getChosen().getTerr()) && hx.getpNum() == -1) {
                            avaliable.add(hx);
                        }
                    }
                }
                System.out.println("AVA: " + avaliable.toString());
            }else if(type == "tiG" && players.get(currPlayer).getSettlements() > 0){
                // if (terrain) {
                    System.out.println("grasY");
                if (players.get(currPlayer).getPlaced().size() >= 1) {

                    for (Hex hx : players.get(currPlayer).getPlaced()) {

                        for (Hex h : hx.getNeighbors()) {
                            if (h != null && h.getpNum() == -1
                                    && h.getType().equals("grs")) {
                                avaliable.add(h);
                            }
                        }
                    }
                }

                if (avaliable.size() < 1) {
                    for (Hex hx : bb.getHexes()) {
                        if (hx.getType().equals("grs") && hx.getpNum() == -1) {
                            avaliable.add(hx);
                        }
                    }
                }
                System.out.println("AVA: " + avaliable.toString());
            }else if(type == "tiB"){
                System.out.println("Boaty");
                if(org ==  null){
                    for(Hex hx: players.get(currPlayer).getPlaced()){
                        avaliable.add(hx);
                    }    
                }else{
                    System.out.println("boat shit");
                    if (players.get(currPlayer).getPlaced().size() >= 1) {

                        for (Hex hx : players.get(currPlayer).getPlaced()) {
    
                            for (Hex h : hx.getNeighbors()) {
                                if (hx != org && h != null && h.getpNum() == -1
                                        && h.getType().equals("wat")) {
                                    avaliable.add(h);
                                }
                            }
                        }
                    }
    
                    if (avaliable.size() < 1) {
                        for (Hex hx : bb.getHexes()) {
                            if(hx != org && hx.getType().equals("wat") && hx.getpNum() == -1) {
                                avaliable.add(hx);
                            }
                        }
                    }
                    System.out.println("AVALIABLE: " + avaliable.toString());
                }
            }
        }
        
    }

    public void updateAvaliable() {
        HashSet<Hex> tempAvaliable = new HashSet<Hex>();
        for (Hex hx : avaliable) {
            if (hx.getpNum() == -1) {
                tempAvaliable.add(hx);
            }
        }
        avaliable = tempAvaliable;
    }

    public boolean avaliable(int r, int c) {
        
        if (avaliable != null) {
            for (Hex hx : avaliable) {
                if (hx.getRow() == r && hx.getCol() == c) {
                    bb.updateHex(r, c, currPlayer);
                    players.get(currPlayer).addPlaced(hx);
                    players.get(currPlayer).setSettlements(players.get(currPlayer).getSettlements() - 1);
                    if(players.get(currPlayer).getSettlements() == 0){
                        updateAvaliable(false);
                    }
                    
                    return true;
                }
            }
        }
        if(players.get(currPlayer).getSettlements() == 0){
            for(Hex hx: players.get(currPlayer).getAllTiles()){
                if(hx.getType().equals("tiO") || hx.getType().equals("tiG")){
                    hx.statUsed();
                }
            }
        }
        // updateAvaliable(true);
        return false;
    }

    public boolean avaliable(int r, int c, Hex tile) {
        if (avaliable != null) {
            for (Hex hx : avaliable) {
                if ((stat == -1 || stat == 1 ) && hx.getRow() == r && hx.getCol() == c && tile.getStat() == 1) {
                    
                    bb.updateHex(r, c, currPlayer);
                    players.get(currPlayer).addPlaced(hx);
                    if(stat == -1)
                        players.get(currPlayer).setSettlements(players.get(currPlayer).getSettlements() - 1);
                    collectTile();
                    tile.statUsed();
                    
                    if(org!= null){
                        org.setpNum(-1);
                        players.get(currPlayer).removePlaced(org);
                        org.setpNum(-1);
                        org = null;
                        checkTile();
                    }
                    return true;
                }else if(stat == 0 && hx.getRow() == r && hx.getCol() == c && tile.getStat() == 1){
                    System.out.println("org done");
                    org = hx;
                    return true;
                }
            }
        }

        
        return false;
    }

    public ArrayList<Section> getBoards() {
        return boards;
    }

    public int getCurrPlayer() {
        return currPlayer;
    }

    public void setBoards(ArrayList<BoardImage> imgs) {
        String[][] hexMapBoat = { { "grs", "grs", "for", "for", "for", "wat", "grs", "for", "for", "flwr" },
                { "grs", "flwr", "for", "for", "wat", "grs", "for", "for", "flwr", "flwr" },
                { "grs", "flwr", "flwr", "for", "wat", "grs", "grs", "flwr", "flwr", "flwr" },
                { "flwr", "flwr", "for", "for", "wat", "grs", "mt", "flwr", "des", "des" },
                { "cnyn", "flwr", "cas", "for", "wat", "grs", "des", "des", "des", "des" },
                { "cnyn", "cnyn", "for", "wat", "grs", "grs", "mt", "mt", "des", "des" },
                { "cnyn", "cnyn", "wat", "wat", "wat", "grs", "des", "des", "des", "cnyn" },
                { "wat", "wat", "grs", "grs", "wat", "wat", "tiB", "cnyn", "mt", "cnyn" },
                { "wat", "des", "cas", "grs", "wat", "mt", "wat", "cnyn", "cnyn", "cnyn" },
                { "wat", "des", "des", "wat", "wat", "wat", "wat", "cnyn", "cnyn", "cnyn" } };

        String[][] hexMapGrass = { { "des", "des", "cnyn", "wat", "wat", "for", "for", "for", "grs", "grs" },
                { "des", "cas", "cnyn", "wat", "for", "for", "for", "tiG", "grs", "grs" },
                { "cnyn", "cnyn", "cnyn", "flwr", "flwr", "flwr", "for", "cnyn", "flwr", "flwr" },
                { "cnyn", "cnyn", "flwr", "flwr", "wat", "des", "des", "cnyn", "cnyn", "flwr" },
                { "cnyn", "grs", "grs", "wat", "flwr", "flwr", "des", "des", "cnyn", "cnyn" },
                { "grs", "grs", "tiG", "flwr", "wat", "flwr", "wat", "des", "des", "cnyn" },
                { "grs", "grs", "grs", "for", "flwr", "flwr", "wat", "wat", "des", "des" },
                { "grs", "grs", "for", "for", "mt", "wat", "wat", "wat", "des", "wat" },
                { "grs", "mt", "for", "for", "wat", "wat", "wat", "wat", "wat", "wat" },
                { "for", "for", "for", "wat", "wat", "wat", "wat", "wat", "wat", "wat" } };

        String[][] hexMapHorse = { { "cnyn", "cnyn", "cnyn", "des", "des", "wat", "des", "des", "des", "des" },
                { "mt", "mt", "cnyn", "des", "des", "wat", "des", "des", "des", "des" },
                { "mt", "mt", "cnyn", "mt", "mt", "wat", "des", "des", "tiH", "flwr" },
                { "mt", "cnyn", "mt", "mt", "wat", "mt", "des", "flwr", "flwr", "flwr" },
                { "cnyn", "cnyn", "for", "for", "wat", "mt", "mt", "cnyn", "flwr", "flwr" },
                { "cnyn", "for", "for", "wat", "cnyn", "cnyn", "cnyn", "mt", "flwr", "flwr" },
                { "cnyn", "tiH", "for", "for", "wat", "flwr", "flwr", "flwr", "flwr", "flwr" },
                { "grs", "grs", "for", "wat", "grs", "cas", "grs", "flwr", "grs", "for" },
                { "grs", "grs", "for", "for", "wat", "grs", "grs", "grs", "grs", "for" },
                { "grs", "grs", "for", "for", "wat", "grs", "grs", "grs", "for", "for" } };

        String[][] hexMapOracle = { { "grs", "grs", "grs", "for", "for", "wat", "grs", "for", "for", "for" },
                { "grs", "grs", "grs", "cas", "for", "wat", "grs", "for", "for", "for" },
                { "grs", "flwr", "flwr", "grs", "for", "for", "wat", "grs", "grs", "for" },
                { "flwr", "flwr", "cnyn", "grs", "for", "wat", "flwr", "tiO", "for", "for" },
                { "flwr", "flwr", "flwr", "cnyn", "cnyn", "wat", "flwr", "flwr", "wat", "wat" },
                { "mt", "mt", "cnyn", "grs", "grs", "wat", "wat", "wat", "des", "des" },
                { "cnyn", "cnyn", "cnyn", "mt", "grs", "flwr", "flwr", "flwr", "des", "des" },
                { "cnyn", "cnyn", "cas", "des", "mt", "des", "flwr", "flwr", "cnyn", "cnyn" },
                { "wat", "wat", "wat", "des", "des", "des", "des", "mt", "cnyn", "cnyn" },
                { "wat", "wat", "wat", "wat", "des", "des", "des", "des", "des", "cnyn" } };

        ArrayList<BoardImage> tempList = imgs;
        for (int i = 0; i < 4; i++) {
            BoardImage tempImage = tempList.remove(0);
            if (tempImage.type == 0) { // boat
                boards.add(new Section(tempImage, hexMapBoat, i));
            } else if (tempImage.type == 1) { // grass
                boards.add(new Section(tempImage, hexMapGrass, i));
            } else if (tempImage.type == 2) { // horse
                boards.add(new Section(tempImage, hexMapHorse, i));
            } else if (tempImage.type == 3) { // oracle
                boards.add(new Section(tempImage, hexMapOracle, i));
            }
        }

        bb.connect(boards);
    }

    public Player getPlayer(int n) {
        return players.get(n);
    }

    public int getNumPly() {
        return players.size();
    }

    public void setPly(int num) {
        currPlayer = num;
    }

    public int getDeckSize(boolean notDisc) { // the size of each deck, true is reg, false is discarded deck
        if (notDisc)
            return deck.size();
        else
            return discard.size();
    }

    public void discard(Card c) {
        discard.add(players.get(currPlayer).getChosen());
        players.get(currPlayer).setChosen(drawDeck());
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public void replaceDeck() {
        deck.addAll(discard);
        discard.clear();
        shuffleDeck();
    }

    public Card drawDeck() {
        if (deck.size() < 1) {
            replaceDeck();
        }
        return deck.remove(0);

    }

    public ArrayList<Player> getAllPlayers(){
        return players;
    }

    public void nextTurn() {
        if(players.get(currPlayer).getSettlements() == 0){
            //
            if(currPlayer == prevPlayer){
                end = true;
                score();
            }else{
                System.out.println("hiya");
                for (int i = 0; i < players.get(currPlayer).getAllTiles().size(); i++) { // set tiles to unused
                    players.get(currPlayer).getAllTiles().get(i).statUnused();
                }
                discard(players.get(currPlayer).getChosen()); // discard + draw
                placed = 0;
                

                if (currPlayer < players.size() - 1) { // change curr player
                    currPlayer++;
                } else {
                    currPlayer = 0;
                }
                updateAvaliable(true);
            }

        }else{
            for (int i = 0; i < players.get(currPlayer).getAllTiles().size(); i++) { // set tiles to unused
                players.get(currPlayer).getAllTiles().get(i).statUnused();
            }
            discard(players.get(currPlayer).getChosen()); // discard + draw
            placed = 0;
            // update settlements
            if (currPlayer < players.size() - 1) { // change curr player
                currPlayer++;
            } else {
                currPlayer = 0;
            }
            updateAvaliable(true);
        }

        
    }

    public void collectTile() {
        for (int i = 0; i < bb.fullTiles.size(); i++) {
            for (int p = 0; p < bb.fullTiles.get(i).getNeighbors().size(); p++) {
                if (bb.fullTiles.get(i).getNeighbors().get(p).getpNum() > -1) {
                    Hex t = bb.fullTiles.get(i).getNeighbors().get(p);
                    Hex adj = bb.fullTiles.get(i);
                    if (players.get(adj.getNeighbors().get(p).getpNum()).getAllTiles().size() != 0) {
                        for (int b = 0; b < players.get(adj.getNeighbors().get(p).getpNum()).getAllTiles()
                                .size(); b++) {
                            // System.out.println("IM GONNA END IT ALL");
                            if (adj.getAmount() > 0 && players.get(adj.getNeighbors().get(p).getpNum()).getAllTiles()
                                    .indexOf(adj) != -1) {
                                int q = players.get(t.getpNum()).getAllTiles().indexOf(adj);// index of multiple tile
                                if (adj.getCol() != players.get(t.getpNum()).getAllTiles().get(q).getCol()
                                        && adj.getRow() != players.get(t.getpNum()).getAllTiles().get(q).getRow()) {
                                    // System.out.println("!"+ p);
                                    players.get(t.getpNum()).addTile(adj);
                                    adj.minusAmount();
                                    System.out.println("player " + t.getpNum() + " collected tile: " + adj.getType());
                                }
                            }
                        }

                    }

                    if (players.get(t.getpNum()).getAllTiles().size() == 0
                            || players.get(adj.getNeighbors().get(p).getpNum()).getAllTiles().indexOf(adj) == -1) {
                        if (adj.getAmount() > 0) {
                            players.get(t.getpNum()).addTile(adj);
                            adj.minusAmount();
                            System.out.println("player " + t.getpNum() + " collected tile: " + adj.getType());
                        }
                    }
                }
            }
        }
    }

    

    public void checkTile() {
        System.out.println("START");
        boolean nextTo = false;
        for(Hex ti: players.get(currPlayer).getAllTiles()){
            nextTo = false;
            for(Hex hx: ti.getNeighbors()){
                if(hx.getpNum() == currPlayer){
                    nextTo = true;
                    break;
                } 
            }
            if(!nextTo){
                System.out.println("Missed: " + ti.toString());
                players.get(currPlayer).removeTile(ti);
                discTiles ++;
                break;
            }
            //     
            //     
                
            //     

            // }
        }
        System.out.println(players.get(currPlayer).getAllTiles().toString());
        System.out.println("END");



        //Im so sorry the code just wasn't working with how the tiles worked :(((((

    }
   
   //scoring
   
public void score(){
    for(int i = 0; i < players.size(); i ++){
        players.get(i).addPoints(lords(i));
        players.get(i).addPoints(workers(i));
        players.get(i).addPoints(discoverer(i));

       // 
        
        players.get(i).addPoints(cas(i));
        players.get(i).addPoints(players.get(i).getPoint(0) + players.get(i).getPoint(1) + players.get(i).getPoint(2) + players.get(i).getPoint(3));
        System.out.println(i + " -- " + players.get(i).getPoint(4));
    }


    //need to order
    plyOrder = new ArrayList<>();
    for(int j = 0; j < num; j++){
        System.out.println(players.size());
        int greatest = -1;
        Player tempPlayer = null;
        for(int i = players.size()-1; i>= 0; i--){
            if(players.get(i).getPoint(4) >= greatest){
                
                greatest = players.get(i).getPoint(4);
                tempPlayer = players.get(i);
                
            }
           
        }
        //System.out.println(tempPlayer.toString() + " - " + greatest);
        plyOrder.add(tempPlayer);
        players.remove(tempPlayer);
        
    }
    System.out.println("FUCK " + plyOrder.get(0).getAllPoints().toString());
    System.out.println("FUCK " + plyOrder.get(1).getAllPoints().toString());
    //System.out.println("FUCK " + plyOrder.get(2).getAllPoints().toString());
    //System.out.println("FUCK " + plyOrder.get(3).getAllPoints().toString());






    // ArrayList<Player> tempPlayers = new ArrayList<>();
    // for(int i = 0; i < players.size(); i++){
    //     tempPlayers.add(players.get(i));
    // }
    // 
    //     System.out.println("go");
    //     int greatest = -1;
    //     Player tempPlayer = null;
    //     for(int j = tempPlayers.size()-1; j > 0; j--){
    //             if(tempPlayers.get(j).getAllPoints().get(4) > greatest){
    //                 tempPlayer = tempPlayers.get(j);
    //                 greatest = tempPlayer.getAllPoints().get(4);
    //                 tempPlayers.remove(j);
    //                 break;
    //             }
            
    //     }
    //     System.out.println("SLEEP???? " + tempPlayer.getAllPoints().toString());
    //     plyOrder.add(tempPlayer);
    // }
}

public int lords(int curr){
    int points = 0;
    int[][] totals = new int[4][players.size()];
    for(int i = 0; i < boards.size(); i++){
        for(int j = 0; j < players.size(); j++){
            totals[i][j] = 0;
            for(Hex hx: boards.get(i).getHexes()){
                if(hx.pNum == j){
                    totals[i][j]++;
                }
            }
        }
    }

    for(int i = 0; i < totals.length; i++){
            for(int j = 0; j < totals[i].length; j++){
                System.out.print(totals[i][j] + ", ");
            }
            System.out.println();
    }
    for(int i = 0; i < totals.length; i ++){
        int greatest = 0;
        
        int second = 0;
        for(int j = 0; j < totals[i].length; j++){
            if(totals[i][j] > greatest) greatest = totals[i][j];
            if(totals[i][j] < greatest && totals[i][j] > second) second = totals[i][j];
        }
        System.out.println(greatest + " vs. " + second);
            if(totals[i][curr] == greatest) points += 12;
            else if(totals[i][curr] == second)points += 6;
        
    }
    
    return points;
}

public int workers(int curr){
    int points = 0;
    for (Hex hx : players.get(curr).getPlaced()) {

        for (Hex h : hx.getNeighbors()) {
            if (h != null && (h.getType().equals("tiO") || h.getType().equals("tiB") || h.getType().equals("tiH") || h.getType().equals("tiG") || h.getType().equals("cas") )) {
                points++;
                break;
            }
        }
    }
    return points;
}

public int discoverer(int curr){
    int points = 0;
    boolean[] rows = new boolean[20];
    for(Hex hx: players.get(curr).getPlaced()){
        rows[hx.getRow()] = true;
    }

    for(int i = 0; i < rows.length; i ++){
        if(rows[i])points ++;
    }

    return points;
}

public int cas(int curr){
    int points = 0;
    for (Hex hx : bb.getHexes()) {
        if(hx.getType().equals("cas")){
            for (Hex h : hx.getNeighbors()) {
                if(h.getpNum()== curr){
                    points += 3;
                    break;
            }
            }
        }
        
    }    


    return points;
}


    // public void scCas(Player p){
    //     int pts=0;
    //     ArrayList<Hex> castles= new ArrayList<Hex>();
    //     for (Hex hx : bb.fullBoard) {//all of castles on board
    //             if (hx.getType().equals("cas")) {
    //                 castles.add(hx);
    //             }}
    //             for(int q=0; q<castles.size();q++){
    //                 if(castles.get(q).hasNeighP(players.indexOf(p))){
    //                     pts=pts+3;
    //                 }
    //                 }
    //                 p.getAllPoints().add(3, pts);
    //                 System.out.println("player " + players.indexOf(p) + " gained " + pts + " from Castles");

    // }

}