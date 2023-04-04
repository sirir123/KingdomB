public class Board{

       public Hex neighbor (Hex h, int dir, int num){ // get hex?? in the board class?
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