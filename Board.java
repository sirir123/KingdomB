import java.util.Set;
import java.util.HashSet;
import java.util.*;
import static java.lang.System.*;

public class Board {
    Section a, b, c, d;
    HashSet<Hex> fullBoard = new HashSet<Hex>();

    public Board() {
    }

    public Board getBoard() {
        return null;
    }

    public ArrayList<Section> shuffle() {
        return null;
    }

    public Board connect(ArrayList<Section> s) {
        return null;
    }

    public Hex neighbor(Hex h, int dir, int num) {
        if (dir == 0) {
            if (h.getRow() - num > -1 && h.getCol() + num <= 10) {
                // return hex w/ col + num and row - num
            }
        }
        if (dir == 1) {
            if (h.getCol() + num * 2 <= 10) {
                // return hex w/ col + (num * 2)
            }
        }
        if (dir == 2) {
            if (h.getRow() + num <= 10 && h.getCol() + num <= 10) {
                // return hex w/ row + num and col + num
            }
        }
        if (dir == 3) {
            if (h.getRow() + num <= 10 && h.getCol() - num > -1) {
                // return hex w/ row + num and col - num
            }
        }
        if (dir == 4) {
            if (h.getCol() - num * 2 > -1) {
                // return hex w/ col - (num *2)
            }
        }
        if (dir == 5) {
            if (h.getRow() - num > -1 && h.getCol() - num > -1) {
                // return hex w/ row - num and col - num
            }
        }
        return null;
    }

    public int score (Player p){ return 0; }
    public int lords (Player p){ return 0; }
    public int workers (Player p){ return 0;}
    public int discoverers (Player p){return 0; }
    public int castle (Player p){return 0; }
    
    public void boatT(Player p, Hex exist, Hex water){ }
    public void paddockT(Player p, Hex exist, Hex next){ }
    public void oracleT(Player p, Hex next){ }
    public void farmT(Player p, Hex grass){ }

}