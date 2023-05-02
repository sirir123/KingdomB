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
        // System.out.println("connect");
        for (Section sec : secs) {
            // System.out.println("SECTION: " + sec.getHexes().toString());
            // System.out.println();
            for (Hex hx : sec.getHexes()) {
                // System.out.println("Hex: " + hx.getSec());
                // System.out.println();
                if (hx.getType().equals("tiH") || hx.getType().equals("tiO") || hx.getType().equals("tiG")
                        || hx.getType().equals("tiB")) {
                    fullTiles.add(hx);
                    System.out.println("IDK PLEASE WORK: " + fullTiles.get(0).getAmount());
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
    }

    public void updateHex(int r, int c, int ply) {
        for (Hex hx : fullBoard) {
            if (hx.getRow() == r && hx.getCol() == c) {
                hx.setpNum(ply);
            }
        }
    }

}