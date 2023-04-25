import java.util.Set;
import java.util.HashSet;
import java.util.*;
import static java.lang.System.*;

public class Board {
    Section a, b, c, d;
    Set<Hex> fullBoard;

    public Board() {
        fullBoard = new HashSet<Hex>();

    }

    public Board getBoard() {
        return null;
    }

    // public ArrayList<Section> shuffle() { // dunno if necessary
    // return null;
    // }

    public Set<Hex> getHxs() {
        return fullBoard;
    }

    public Board connect(ArrayList<Section> s) {
        System.out.println("connect");

        for (int i = 0; i < s.size(); i++) {
            for (Hex hx : s.get(i).getHexes()) {
                switch (i) {
                    case 0:
                        fullBoard.add(hx);
                        break;
                    case 1:
                        hx.multiply(2, 1);
                        fullBoard.add(hx);
                        break;
                    case 2:
                        hx.multiply(1, 2);
                        fullBoard.add(hx);
                        break;
                    case 3:
                        hx.multiply(2, 2);
                        fullBoard.add(hx);
                        break;
                }
            }
        }

        for (Section sec : s) {
            // System.out.println("SEC: " + sec.getHexes());
            for (Hex hx : sec.getHexes()) {
                // System.out.println("SEC: ");
                switch (hx.getSec()) {
                    case 0:
                        fullBoard.add(hx);
                        break;
                    case 1:
                        hx.multiply(18, 0);
                        fullBoard.add(hx);
                        break;
                    case 2:
                        hx.multiply(0, 9);
                        fullBoard.add(hx);
                        break;
                    case 3:
                        hx.multiply(18, 9);
                        fullBoard.add(hx);
                        break;
                }
            }
        }
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

    public int score(Player p) {
        return 0;
    }

    public int lords(Player p) {
        return 0;
    }

    public int workers(Player p) {
        return 0;
    }

    public int discoverers(Player p) {
        return 0;
    }

    public int castle(Player p) {
        return 0;
    }

}