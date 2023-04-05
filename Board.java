import java.util.Set;
import java.util.HashSet;
import java.util.*;
import static java.lang.System.*;

public class Board{
    Section a, b, c, d;
    HashSet <Hex> fullBoard = new HashSet <Hex>();

    public Board(){ }

    public board getBoard(){ }

    public ArrayList<Section>() shuffle (){ }

    public Board connect (ArrayList<Section> s){ }

    public Hex neighbor (Hex h, int dir, int num){ 
        if (dir = 0) {
            if (h.getRow() - num >-1 && h.getCol() + num <= 10) {
                // return hex w/ col + num and row - num
            }
        }
        if (dir = 1) {
            if (h.getCol() + num*2 <= 10){
                // return hex w/ col + (num * 2)
            }
        }
        if (dir = 2){
            if (h.getRow() + num <= 10 && h.getCol() + num <= 10){
                // return hex w/ row + num and col + num
            }
        }
        if (dir = 3){
            if (h.getRow() + num <= 10 && h.getCol() - num >-1){
               // return hex w/ row + num and col - num
            }
        }
        if (dir = 4){
            if (h.getCol() - num*2 >-1){
                // return hex w/ col - (num *2)
            }
        }
        if (dir = 5){
            if (h.getRow() - num > -1 && h.getCol() - num > -1){
                // return hex w/  row - num and col - num
            }
        }
        return null;
    }

}