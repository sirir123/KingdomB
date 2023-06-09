import java.util.Set;
import java.util.HashSet;
import java.util.*;
import static java.lang.System.*;

public class Board {
    public Section a, b, c, d;
    public Set<Hex> fullBoard;
    public ArrayList<Hex> fullTiles;

    public Board() {
        fullBoard = new HashSet<Hex>();
        fullTiles = new ArrayList<>();
    }

    public Set<Hex> getHexes() {
        return fullBoard;
    }

    public void connect(ArrayList<Section> secs) {
        for (Section sec : secs) {
            for (Hex hx : sec.getHexes()) {
                if (hx.getType().equals("tiH") || hx.getType().equals("tiO") || hx.getType().equals("tiG")
                        || hx.getType().equals("tiB")) {
                    fullTiles.add(hx);
                }
                switch (hx.getSec()) {
                    case 0:
                        fullBoard.add(hx);
                        break;
                    case 1:
                        hx.update(20, 0);
                        fullBoard.add(hx);
                        break;
                    case 2:
                        hx.update(0, 10);
                        fullBoard.add(hx);
                        break;
                    case 3:
                        hx.update(20, 10);
                        fullBoard.add(hx);
                        break;

                }
            }
        }
        setNeighbors();
    }

    public void updateHex(int r, int c, int ply) {
        for (Hex hx : fullBoard) {
            if (hx.getRow() == r && hx.getCol() == c) {
                hx.setpNum(ply);
            }
        }
    }

    public void setNeighbors() {
        for (Hex hx : fullBoard) {
            for (Hex h : fullBoard) {
                if (hx.getCol() - 1 == h.getCol()) {
                    if (hx.getRow() - 1 == h.getRow()) {
                        hx.setNeighbors(0, h);
                    } else if (hx.getRow() + 1 == h.getRow()) {
                        hx.setNeighbors(4, h);
                    }
                } else if (hx.getCol() + 1 == h.getCol()) {
                    if (hx.getRow() - 1 == h.getRow()) {
                        hx.setNeighbors(1, h);
                    } else if (hx.getRow() + 1 == h.getRow()) {
                        hx.setNeighbors(3, h);
                    }
                } else if (hx.getCol() - 2 == h.getCol() && hx.getRow() == h.getRow()) {
                    hx.setNeighbors(5, h);
                } else if (hx.getCol() + 2 == h.getCol() && hx.getRow() == h.getRow()) {
                    hx.setNeighbors(2, h);
                }
            }
        }
    }

}