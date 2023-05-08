import java.awt.image.BufferedImage;
import java.util.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Game {
    private int currPlayer;
    public int startPlayer;// should be random

    public int state; // 0 terrain card, 1 tiles
    public HashSet<Hex> avaliable;
    public int placed;
    public int stat; //for boat and horse, -1 not used, 0 tile picked, 1 org picked, then back to -1
    public Hex org; //storing org tile picked when boat or horse

    private ArrayList<Player> players;
    private ArrayList<Card> deck;
    private ArrayList<Card> discard;
    public int discTiles;
    public Board bb;
    private ArrayList<Section> boards;

    public Game(int amt) {
        bb = new Board();
        currPlayer = (int) (Math.random() * amt);
        startPlayer = currPlayer;
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
        for(Hex ti: players.get(currPlayer).getAllTiles()){
            if(ti!= null && ti.getStat() == 1)return true;
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
                
                

            }else if(type == "tiO"){
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
            }else if(type == "tiG"){
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
                    // updateAvaliable(true);
                    return true;
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

    public void nextTurn() {
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

    public void checkTile(/*Hex hx, Hex next*/) {
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
                discTiles ++;
                players.get(currPlayer).removeTile(ti);
                
                ti.statDiscard();

            }
        }
        System.out.println("END");



        //Im so sorry the code just wasn't working with how the tiles worked :(((((

    }
   
   //scoring
   
    public void scLords(int numPly){
        //compare said ints to give out points
        //add points to index 0 of points arraylist for every player + keep in mind different # of players in a game
        for(int p=0; p<numPly; p++){
            for (Hex hx : bb.fullBoard) {
                    if (hx.getpNum() == p && hx.getSec()==1) {
                        players.get(p).sec1++;
                    }
                    if (hx.getpNum() == p && hx.getSec()==2) {
                        players.get(p).sec2++;
                    }
                    if (hx.getpNum() == p && hx.getSec()==3) {
                        players.get(p).sec3++;
                    }
                    if (hx.getpNum() == p && hx.getSec()==4) {
                        players.get(p).sec4++;
                    }
                    //num of setts in each sector
                }
                System.out.println("player " + p + ": " + players.get(p).sec1 + " , " + players.get(p).sec2 + " , " + players.get(p).sec3 + " , " + players.get(p).sec4);
            }
       if(numPly==2){
        for(int s=0; s<4; s++){//iterates through each sector
        if(players.get(0).getS(s)>players.get(1).getS(s)){
            //p0 gets 12 && p1 get 6
            players.get(0).getAllPoints().add(0,12);
            players.get(1).getAllPoints().add(0,6);
        }
        if(players.get(1).getS(s)>players.get(0).getS(s)){
            //p1 gets 12 && p0 get 6
            players.get(0).getAllPoints().add(0,6);
            players.get(1).getAllPoints().add(0,12);
        }
        if(players.get(0).getS(s)==players.get(1).getS(s)){
            //p0 & p1 get 12
            players.get(0).getAllPoints().add(0,12);
            players.get(1).getAllPoints().add(0,12);
        }
    }
       }

       if(numPly==3){
        for(int s=0; s<4; s++){//iterates through each sector
            if(players.get(0).getS(s)>players.get(1).getS(s) && players.get(0).getS(s)>players.get(2).getS(s)){
                //p0 gets 12
                players.get(0).getAllPoints().add(0,12);
                if(players.get(1).getS(s)>players.get(2).getS(s)){
                    //p1 gets 6
                    players.get(1).getAllPoints().add(0,6);
                }
                if(players.get(2).getS(s)>players.get(1).getS(s)){
                    //p2 gets 6
                    players.get(2).getAllPoints().add(0,6);
                }
                if(players.get(1).getS(s)==players.get(2).getS(s)){
                    //p1 & p2 gets 6
                    players.get(1).getAllPoints().add(0,6);
                    players.get(2).getAllPoints().add(0,6);
                }
            }
            else if(players.get(1).getS(s)>players.get(0).getS(s) && players.get(1).getS(s)>players.get(2).getS(s)){
                //p1 gets 12
                players.get(1).getAllPoints().add(0,12);
                if(players.get(2).getS(s)>players.get(0).getS(s)){
                    //p2 gets 6
                    players.get(2).getAllPoints().add(0,6);
                }
                if(players.get(0).getS(s)>players.get(2).getS(s)){
                    //p0 gets 6
                    players.get(0).getAllPoints().add(0,6);
                }
                if(players.get(2).getS(s)==players.get(0).getS(s)){
                    //p0 & p2 gets 6
                    players.get(0).getAllPoints().add(0,6);
                    players.get(2).getAllPoints().add(0,6);
                }
            }
            else if(players.get(2).getS(s)>players.get(0).getS(s) && players.get(2).getS(s)>players.get(1).getS(s)){
                //p2 gets 12
                players.get(2).getAllPoints().add(0,12);
                if(players.get(1).getS(s)>players.get(0).getS(s)){
                    //p1 gets 6
                    players.get(1).getAllPoints().add(0,6);
                }
                if(players.get(0).getS(s)>players.get(1).getS(s)){
                    //p0 gets 6
                    players.get(0).getAllPoints().add(0,6);
                }
                if(players.get(1).getS(s)==players.get(0).getS(s)){
                    //p0 & p1 gets 6
                    players.get(0).getAllPoints().add(0,6);
                    players.get(1).getAllPoints().add(0,6);
                }
            }
            else if(players.get(0).getS(s)==players.get(1).getS(s)){//0==1
                if(players.get(1).getS(s)>players.get(2).getS(s)){
                    //p1 & p0 gets 12
                    //p2 get 6
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,6);
                }
                if(players.get(1).getS(s)==players.get(2).getS(s)){
                    //p1 & p0 & p2 gets 12
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                }
            }
            else if(players.get(0).getS(s)==players.get(2).getS(s)){//0==2
                if(players.get(2).getS(s)>players.get(1).getS(s)){
                    //p2 & p0 gets 12
                    //p1 get 6
                    players.get(0).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,6);
                }
                if(players.get(2).getS(s)==players.get(1).getS(s)){
                    //p1 & p0 & p2 gets 12
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                }
            }
            else if(players.get(1).getS(s)==players.get(2).getS(s)){//1==2
                if(players.get(2).getS(s)>players.get(0).getS(s)){
                    //p2 & p1 gets 12
                    //p0 get 6
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(0).getAllPoints().add(0,6);
                }
                if(players.get(2).getS(s)==players.get(0).getS(s)){
                    //p1 & p0 & p2 gets 12
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                }
            }
        }
       }

       if(numPly==4){
        for(int s=0; s<4; s++){//iterates through each sector
            //if p0 biggest
            if(players.get(0).getS(s)>players.get(1).getS(s) && players.get(0).getS(s)>players.get(2).getS(s) && players.get(0).getS(s)>players.get(3).getS(s)){
                //p0 gets 12
                players.get(0).getAllPoints().add(0,12);
                if(players.get(1).getS(s)>players.get(2).getS(s) && players.get(1).getS(s)>players.get(3).getS(s)){
                    //p1 gets 6
                    players.get(1).getAllPoints().add(0,6);
                }
                if(players.get(2).getS(s)>players.get(1).getS(s) && players.get(2).getS(s)>players.get(3).getS(s)){
                    //p2 gets 6
                    players.get(2).getAllPoints().add(0,6);
                }
                if(players.get(3).getS(s)>players.get(2).getS(s) && players.get(3).getS(s)>players.get(1).getS(s)){
                    //p3 gets 6
                    players.get(3).getAllPoints().add(0,6);
                }
                if(players.get(1).getS(s)==players.get(2).getS(s)){//p1,p2
                    //p1 & p2 gets 6
                    if(players.get(1).getS(s)>players.get(3).getS(s)){
                    players.get(1).getAllPoints().add(0,6);
                    players.get(2).getAllPoints().add(0,6);
                    }
                    if(players.get(1).getS(s)==players.get(3).getS(s)){
                        //p3 get 6
                        players.get(3).getAllPoints().add(0,6);
                        }
                }
                if(players.get(2).getS(s)==players.get(3).getS(s)){//p2,p3
                    //p2 & p3 gets 6
                    if(players.get(3).getS(s)>players.get(1).getS(s)){
                    players.get(3).getAllPoints().add(0,6);
                    players.get(2).getAllPoints().add(0,6);
                    }
                    if(players.get(1).getS(s)==players.get(3).getS(s)){
                        //p1 get 6
                        players.get(1).getAllPoints().add(0,6);
                        }
                }
                if(players.get(1).getS(s)==players.get(3).getS(s)){//p1,p3
                    //p1 & p3 gets 6
                    if(players.get(3).getS(s)>players.get(2).getS(s)){
                    players.get(3).getAllPoints().add(0,6);
                    players.get(1).getAllPoints().add(0,6);
                    }
                    if(players.get(1).getS(s)==players.get(2).getS(s)){
                        //p2 get 6
                        players.get(2).getAllPoints().add(0,6);
                        }
                }
            }

            //if p1 biggest
            else if(players.get(1).getS(s)>players.get(0).getS(s) && players.get(1).getS(s)>players.get(2).getS(s) && players.get(1).getS(s)>players.get(3).getS(s)){
                //p1 gets 12
                players.get(1).getAllPoints().add(0,12);
                if(players.get(0).getS(s)>players.get(2).getS(s) && players.get(0).getS(s)>players.get(3).getS(s)){
                    //p0 gets 6
                    players.get(0).getAllPoints().add(0,6);
                }
                if(players.get(2).getS(s)>players.get(0).getS(s) && players.get(2).getS(s)>players.get(3).getS(s)){
                    //p2 gets 6
                    players.get(2).getAllPoints().add(0,6);
                }
                if(players.get(3).getS(s)>players.get(2).getS(s) && players.get(3).getS(s)>players.get(0).getS(s)){
                    //p3 gets 6
                    players.get(3).getAllPoints().add(0,6);
                }
                if(players.get(0).getS(s)==players.get(2).getS(s)){//p0,p2
                    //p0 & p2 gets 6
                    if(players.get(0).getS(s)>players.get(3).getS(s)){
                    players.get(0).getAllPoints().add(0,6);
                    players.get(2).getAllPoints().add(0,6);
                    }
                    if(players.get(0).getS(s)==players.get(3).getS(s)){
                        //p3 get 6
                        players.get(3).getAllPoints().add(0,6);
                        }
                }
                if(players.get(2).getS(s)==players.get(3).getS(s)){//p2,p3
                    //p2 & p3 gets 6
                    if(players.get(3).getS(s)>players.get(0).getS(s)){
                    players.get(3).getAllPoints().add(0,6);
                    players.get(2).getAllPoints().add(0,6);
                    }
                    if(players.get(0).getS(s)==players.get(3).getS(s)){
                        //p0 get 6
                        players.get(0).getAllPoints().add(0,6);
                        }
                }
                if(players.get(0).getS(s)==players.get(3).getS(s)){//p0,p3
                    //p0 & p3 gets 6
                    if(players.get(3).getS(s)>players.get(2).getS(s)){
                    players.get(3).getAllPoints().add(0,6);
                    players.get(0).getAllPoints().add(0,6);
                    }
                    if(players.get(0).getS(s)==players.get(2).getS(s)){
                        //p2 get 6
                        players.get(2).getAllPoints().add(0,6);
                        }
                }
            }

            //if p2 biggest
            else if(players.get(2).getS(s)>players.get(0).getS(s) && players.get(2).getS(s)>players.get(1).getS(s) && players.get(2).getS(s)>players.get(3).getS(s)){
                //p2 gets 12
                players.get(2).getAllPoints().add(0,12);
                if(players.get(1).getS(s)>players.get(0).getS(s) && players.get(1).getS(s)>players.get(3).getS(s)){
                    //p1 gets 6
                    players.get(1).getAllPoints().add(0,6);
                }
                if(players.get(0).getS(s)>players.get(1).getS(s) && players.get(0).getS(s)>players.get(3).getS(s)){
                    //p0 gets 6
                    players.get(0).getAllPoints().add(0,6);
                }
                if(players.get(3).getS(s)>players.get(0).getS(s) && players.get(3).getS(s)>players.get(1).getS(s)){
                    //p3 gets 6
                    players.get(3).getAllPoints().add(0,6);
                }
                if(players.get(1).getS(s)==players.get(0).getS(s)){//p1,p0
                    //p1 & p0 gets 6
                    if(players.get(1).getS(s)>players.get(3).getS(s)){
                    players.get(1).getAllPoints().add(0,6);
                    players.get(0).getAllPoints().add(0,6);
                    }
                    if(players.get(1).getS(s)==players.get(3).getS(s)){
                        //p3 get 6
                        players.get(3).getAllPoints().add(0,6);
                        }
                }
                if(players.get(0).getS(s)==players.get(3).getS(s)){//p0,p3
                    //p0 & p3 gets 6
                    if(players.get(3).getS(s)>players.get(1).getS(s)){
                    players.get(3).getAllPoints().add(0,6);
                    players.get(0).getAllPoints().add(0,6);
                    }
                    if(players.get(1).getS(s)==players.get(3).getS(s)){
                        //p1 get 6
                        players.get(1).getAllPoints().add(0,6);
                        }
                }
                if(players.get(1).getS(s)==players.get(3).getS(s)){//p1,p3
                    //p1 & p3 gets 6
                    if(players.get(3).getS(s)>players.get(0).getS(s)){
                    players.get(3).getAllPoints().add(0,6);
                    players.get(1).getAllPoints().add(0,6);
                    }
                    if(players.get(1).getS(s)==players.get(0).getS(s)){
                        //p2 get 6
                        players.get(0).getAllPoints().add(0,6);
                        }
                }
            }

            //if p3 biggest
            else if(players.get(3).getS(s)>players.get(0).getS(s) && players.get(3).getS(s)>players.get(1).getS(s) && players.get(3).getS(s)>players.get(2).getS(s)){
                //p3 gets 12
                players.get(3).getAllPoints().add(0,12);
                if(players.get(1).getS(s)>players.get(2).getS(s) && players.get(1).getS(s)>players.get(0).getS(s)){
                    //p1 gets 6
                    players.get(1).getAllPoints().add(0,6);
                }
                if(players.get(2).getS(s)>players.get(1).getS(s) && players.get(2).getS(s)>players.get(0).getS(s)){
                    //p2 gets 6
                    players.get(2).getAllPoints().add(0,6);
                }
                if(players.get(0).getS(s)>players.get(2).getS(s) && players.get(0).getS(s)>players.get(1).getS(s)){
                    //p0 gets 6
                    players.get(0).getAllPoints().add(0,6);
                }
                if(players.get(1).getS(s)==players.get(2).getS(s)){//p1,p2
                    //p1 & p2 gets 6
                    if(players.get(1).getS(s)>players.get(0).getS(s)){
                    players.get(1).getAllPoints().add(0,6);
                    players.get(2).getAllPoints().add(0,6);
                    }
                    if(players.get(1).getS(s)==players.get(0).getS(s)){
                        //p0 get 6
                        players.get(0).getAllPoints().add(0,6);
                        }
                }
                if(players.get(2).getS(s)==players.get(0).getS(s)){//p2,p0
                    //p2 & p0 gets 6
                    if(players.get(0).getS(s)>players.get(1).getS(s)){
                    players.get(0).getAllPoints().add(0,6);
                    players.get(2).getAllPoints().add(0,6);
                    }
                    if(players.get(1).getS(s)==players.get(0).getS(s)){
                        //p1 get 6
                        players.get(1).getAllPoints().add(0,6);
                        }
                }
                if(players.get(1).getS(s)==players.get(0).getS(s)){//p1,p0
                    //p1 & p0 gets 6
                    if(players.get(0).getS(s)>players.get(2).getS(s)){
                    players.get(0).getAllPoints().add(0,6);
                    players.get(1).getAllPoints().add(0,6);
                    }
                    if(players.get(1).getS(s)==players.get(2).getS(s)){
                        //p2 get 6
                        players.get(2).getAllPoints().add(0,6);
                        }
                }
            }
            //0==1
            else if(players.get(0).getS(s)==players.get(1).getS(s)){
                if(players.get(1).getS(s)>players.get(2).getS(s) && players.get(1).getS(s)>players.get(3).getS(s)){
                    //p1 & p0 gets 12
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    if(players.get(2).getS(s)>players.get(3).getS(s)){
                        //p2 get 6
                        players.get(2).getAllPoints().add(0,6);
                    }
                    if(players.get(3).getS(s)>players.get(2).getS(s)){
                        //p3 get 6
                        players.get(3).getAllPoints().add(0,6);
                    }
                    
                }
                if(players.get(1).getS(s)==players.get(2).getS(s) && players.get(1).getS(s)>players.get(3).getS(s)){
                    //p1 & p0 & p2 gets 12
                    //p3 get 6
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,6);
                }
                if(players.get(1).getS(s)==players.get(3).getS(s) && players.get(1).getS(s)>players.get(2).getS(s)){
                    //p1 & p0 & p3 gets 12
                    //p2 get 6
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,6);
                }
                if(players.get(1).getS(s)==players.get(2).getS(s) && players.get(1).getS(s)==players.get(3).getS(s)){
                    //p1 & p0 & p2 & p3 gets 12
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                }
            }

            //0==2
            else if(players.get(0).getS(s)==players.get(2).getS(s)){
                if(players.get(2).getS(s)>players.get(1).getS(s) && players.get(2).getS(s)>players.get(3).getS(s)){
                    //p2 & p0 gets 12
                    players.get(0).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    if(players.get(1).getS(s)>players.get(3).getS(s)){
                        //p1 get 6
                        players.get(1).getAllPoints().add(0,6);
                    }
                    if(players.get(3).getS(s)>players.get(1).getS(s)){
                        //p3 get 6
                        players.get(3).getAllPoints().add(0,6);
                    }
                    
                }
                if(players.get(2).getS(s)==players.get(1).getS(s) && players.get(2).getS(s)>players.get(3).getS(s)){
                    //p1 & p0 & p2 gets 12
                    //p3 get 6
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,6);
                }
                if(players.get(2).getS(s)==players.get(3).getS(s) && players.get(2).getS(s)>players.get(1).getS(s)){
                    //p2 & p0 & p3 gets 12
                    //p1 get 6
                    players.get(0).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,6);
                }
                if(players.get(2).getS(s)==players.get(1).getS(s) && players.get(2).getS(s)==players.get(3).getS(s)){
                    //p1 & p0 & p2 & p3 gets 12
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                }
            }

            //0==3
            else if(players.get(0).getS(s)==players.get(3).getS(s)){
                if(players.get(3).getS(s)>players.get(1).getS(s) && players.get(3).getS(s)>players.get(2).getS(s)){
                    //p3 & p0 gets 12
                    players.get(0).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                    if(players.get(1).getS(s)>players.get(2).getS(s)){
                        //p1 get 6
                        players.get(1).getAllPoints().add(0,6);
                    }
                    if(players.get(2).getS(s)>players.get(1).getS(s)){
                        //p2 get 6
                        players.get(2).getAllPoints().add(0,6);
                    }
                    
                }
                if(players.get(3).getS(s)==players.get(1).getS(s) && players.get(3).getS(s)>players.get(2).getS(s)){
                    //p1 & p0 & p3 gets 12
                    //p2 get 6
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,6);
                }
                if(players.get(3).getS(s)==players.get(2).getS(s) && players.get(2).getS(s)>players.get(1).getS(s)){
                    //p2 & p0 & p3 gets 12
                    //p1 get 6
                    players.get(0).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,6);
                }
                if(players.get(3).getS(s)==players.get(1).getS(s) && players.get(3).getS(s)==players.get(2).getS(s)){
                    //p1 & p0 & p2 & p3 gets 12
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                }
            }

            //1==2
            else if(players.get(1).getS(s)==players.get(2).getS(s)){
                if(players.get(2).getS(s)>players.get(0).getS(s) && players.get(2).getS(s)>players.get(3).getS(s)){
                    //p2 & p1 gets 12
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    if(players.get(0).getS(s)>players.get(3).getS(s)){
                        //p0 get 6
                        players.get(0).getAllPoints().add(0,6);
                    }
                    if(players.get(3).getS(s)>players.get(0).getS(s)){
                        //p3 get 6
                        players.get(3).getAllPoints().add(0,6);
                    }
                    
                }
                if(players.get(2).getS(s)==players.get(0).getS(s) && players.get(0).getS(s)>players.get(3).getS(s)){
                    //p1 & p0 & p2 gets 12
                    //p3 get 6
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,6);
                }
                if(players.get(2).getS(s)==players.get(3).getS(s) && players.get(3).getS(s)>players.get(0).getS(s)){
                    //p2 & p1 & p3 gets 12
                    //p0 get 6
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                    players.get(0).getAllPoints().add(0,6);
                }
                if(players.get(2).getS(s)==players.get(0).getS(s) && players.get(2).getS(s)==players.get(3).getS(s)){
                    //p1 & p0 & p2 & p3 gets 12
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                }
            }


            //1==3
            else if(players.get(1).getS(s)==players.get(3).getS(s)){
                if(players.get(3).getS(s)>players.get(0).getS(s) && players.get(3).getS(s)>players.get(2).getS(s)){
                    //p3 & p1 gets 12
                    players.get(1).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                    if(players.get(0).getS(s)>players.get(2).getS(s)){
                        //p0 get 6
                        players.get(0).getAllPoints().add(0,6);
                    }
                    if(players.get(2).getS(s)>players.get(0).getS(s)){
                        //p2 get 6
                        players.get(2).getAllPoints().add(0,6);
                    }
                    
                }
                if(players.get(1).getS(s)==players.get(2).getS(s) && players.get(2).getS(s)>players.get(0).getS(s)){
                    //p1 & p3 & p2 gets 12
                    //p0 get 6
                    players.get(3).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(0).getAllPoints().add(0,6);
                }
                if(players.get(1).getS(s)==players.get(0).getS(s) && players.get(0).getS(s)>players.get(2).getS(s)){
                    //p0 & p1 & p3 gets 12
                    //p2 get 6
                    players.get(1).getAllPoints().add(0,12);
                    players.get(0).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,6);
                }
                if(players.get(1).getS(s)==players.get(0).getS(s) && players.get(1).getS(s)==players.get(2).getS(s)){
                    //p1 & p0 & p2 & p3 gets 12
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                }
            }
            

            //2==3
            else if(players.get(2).getS(s)==players.get(3).getS(s)){
                if(players.get(3).getS(s)>players.get(0).getS(s) && players.get(3).getS(s)>players.get(1).getS(s)){
                    //p3 & p2 gets 12
                    players.get(2).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                    if(players.get(0).getS(s)>players.get(1).getS(s)){
                        //p0 get 6
                        players.get(0).getAllPoints().add(0,6);
                    }
                    if(players.get(1).getS(s)>players.get(0).getS(s)){
                        //p1 get 6
                        players.get(1).getAllPoints().add(0,6);
                    }
                    
                }
                if(players.get(1).getS(s)==players.get(2).getS(s) && players.get(2).getS(s)>players.get(0).getS(s)){
                    //p1 & p3 & p2 gets 12
                    //p0 get 6
                    players.get(3).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(0).getAllPoints().add(0,6);
                }
                if(players.get(3).getS(s)==players.get(0).getS(s) && players.get(0).getS(s)>players.get(1).getS(s)){
                    //p0 & p2 & p3 gets 12
                    //p1 get 6
                    players.get(2).getAllPoints().add(0,12);
                    players.get(0).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,6);
                }
                if(players.get(3).getS(s)==players.get(0).getS(s) && players.get(3).getS(s)==players.get(1).getS(s)){
                    //p1 & p0 & p2 & p3 gets 12
                    players.get(0).getAllPoints().add(0,12);
                    players.get(1).getAllPoints().add(0,12);
                    players.get(2).getAllPoints().add(0,12);
                    players.get(3).getAllPoints().add(0,12);
                }
            }

            }
        }

       }

    public void scWorkers(Player p){
        int pts=0;
        ArrayList<Hex> pSett= new ArrayList<Hex>();
        for (Hex hx : bb.fullBoard) {//all of player's settlements on board
                if (hx.getpNum() == players.indexOf(p)) {
                    pSett.add(hx);
                }}
                for(int q=0; q<pSett.size(); q++){
                    for(int e=0; e<pSett.get(q).getNeighbors().size(); e++){
                    if(pSett.get(q).getNeighbors().get(e).getType().equals("cas") || pSett.get(q).getNeighbors().get(e).getType().equals("tiH") 
                    || pSett.get(q).getNeighbors().get(e).getType().equals("tiG") || pSett.get(q).getNeighbors().get(e).getType().equals("tiB") 
                    || pSett.get(q).getNeighbors().get(e).getType().equals("tiO")){
                        pts++;
                    }

                    }}
                    p.getAllPoints().add(1, pts);
                    System.out.println("player " + players.indexOf(p) + " gained " + pts + " from Workers");
    }

    public void scCas(Player p){
        int pts=0;
        ArrayList<Hex> castles= new ArrayList<Hex>();
        for (Hex hx : bb.fullBoard) {//all of castles on board
                if (hx.getType().equals("cas")) {
                    castles.add(hx);
                }}
                for(int q=0; q<castles.size();q++){
                    if(castles.get(q).hasNeighP(players.indexOf(p))){
                        pts=pts+3;
                    }
                    }
                    p.getAllPoints().add(3, pts);
                    System.out.println("player " + players.indexOf(p) + " gained " + pts + " from Castles");

    }

}