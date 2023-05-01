import java.util.Set;
import java.util.HashSet;
import java.util.*;
import static java.lang.System.*;

import java.awt.image.BufferedImage;

public class Section {
    private Set<Hex> hexes;
    private Set<Hex> tileHexes;
    private Hex[][] hexMap;
    private BoardImage IMG;

    Section(BoardImage i, String[][] hx, int s) { // give list of numbers, create tiles
        hexes = new HashSet<>();
        hexMap = new Hex[hx.length][hx[0].length];
        tileHexes = new HashSet<>();
        // w/ correct nums and
        // neighbors
        // blah blah blah
        IMG = i;
        int tempC;
        for (int r = 0; r < hx.length; r++) {
            if (r % 2 == 0)
                tempC = 0;
            else
                tempC = 1;

            for (int j = 0; j < hx[0].length; j++) {
                Hex tempHex = new Hex(hx[r][j], tempC, r, s);

                hexes.add(tempHex);
                hexMap[r][j] = tempHex;

                if (hx[r][j].equals("tiH") || hx[r][j].equals("tiO") || hx[r][j].equals("tiG")
                        || hx[r][j].equals("tiB")) {
                    tileHexes.add(tempHex);
                }

                tempC += 2;
            }

        }
        // System.out.println();
        // System.out.println();
        // for (Hex[] hxs : hexMap) {
        // for (Hex h : hxs) {
        // System.out.print(h.toString() + ", ");
        // }
        // System.out.println();
        // }
        // System.out.println();
        // System.out.println();
        // System.out.println("Hex Map: " + hexMap.toString());
    }

    public BufferedImage getImage() {
        return IMG.img;
    }

    public Set<Hex> getHexes() {
        return hexes;
    }

}

// spaces: des, for, flwr, cnyn, grs, mt, wat, ti(H,O,G,B), cas

// board1:
// cnyn, cnyn, cnyn, des, des, wat, des, des, des, des
// mt, mt, cnyn, des, des, wat, des, des, des, des
// mt, mt, cnyn, mt, mt, wat, des, des, tiH, flwr
// mt, cnyn, mt, mt, wat, mt, des, flwr, flwr, flwr
// cnyn, cnyn, for, for, wat, mt, mt, cnyn, flwr, flwr
// cnyn, for, for, wat, cnyn, cnyn, cnyn, mt, flwr, flwr
// cnyn, tiH, for, for, wat, flwr, flwr, flwr, flwr, flwr
// grs, grs, for, wat, grs, cas, grs, flwr, grs, for
// grs, grs, for, for, wat, grs, grs, grs, grs, for
// grs, grs, for, for, wat, grs, grs, grs, for, for

// board2:
// grs, grs, grs, for, for, wat, grs, for, for, for
// grs, grs, grs, cas, for, wat, grs, for, for, for
// grs, flwr, flwr, grs, for, for, wat, grs, grs, for
// flwr, flwr, cnyn, grs, for, wat, flwr, tiO, for, for
// flwr, flwr, flwr, cnyn, cnyn, wat, flwr, flwr, wat, wat
// mt, mt, cnyn, grs, grs, wat, wat, wat, des, des
// cnyn, cnyn, cnyn, mt, grs, flwr, flwr, flwr, des, des
// cnyn, cnyn, cas, des, mt, des, flwr, flwr, cnyn, cnyn
// wat, wat, wat, des, des, des, des, mt, cnyn, cnyn
// wat, wat, wat, wat, des, des, des, des, des, cnyn

// board3:
// des, des, cnyn, wat, wat, for, for, for, grs, grs
// des, cas, cnyn, wat, for, for, for tiG, grs, grs
// cnyn, cnyn, cnyn, flwr, flwr, flwr, for, cnyn, flwr, flwr
// cnyn, cnyn, flwr, flwr, wat, des, des, cnyn, cnyn, flwr
// cnyn, grs, grs, wat, flwr, flwr, des, des, cnyn, cnyn
// grs, grs, tiG, flwr, wat, flwr, wat, des, des, cnyn
// grs, grs, grs, for, flwr, flwr, wat, wat, des, des
// grs, grs, for, for, mt, wat, wat, wat, des, wat
// grs, mt, for, for, wat, wat, wat, wat, wat, wat
// for, for, for, wat, wat, wat, wat, wat, wat, wat

// board4:
// grs, grs, for, for, for, wat, grs, for, for, flwr
// grs, flwr, for, for, wat, grs, for, for, flwr, flwr
// grs, flwr, flwr, for, wat, grs, grs, flwr, flwr, flwr
// flwr, flwr, for, for, wat, grs, mt, flwr, des, des
// cnyn, flwr, cas, for, wat, grs, des, des, des, des
// cnyn, cnyn, for, wat, grs, grs, mt, mt, des, des
// cnyn, cnyn, wat, wat, wat, grs, des, des, des, cnyn
// wat, wat, grs, grs, wat, wat, tiB, cnyn, mt, cnyn
// wat, des, cas, grs, wat, mt, wat, cnyn, cnyn, cnyn
// wat, des, des, wat, wat, wat, wat, cnyn, cnyn, cnyn