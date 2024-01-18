import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class Minesweeper {
    public static class Board { //16x30=480
        char [][] board;
        byte minesRemain;
    } //end of Board
/////////////////////////////////////////////////////////////////////////////////
    public static Vector<Integer> Places(byte mines) {
        Vector<Integer> locs = new Vector<>();
        for (int i = 1; i <= mines; i++) {
            double z;
            int n;
            boolean used = false;
            z = (Math.random()*481)+1; //May not be right numbers
            n = (int) z;

            for (int j = 0; j < locs.size(); j++) {
                if (n == locs.get(j)) {
                    used = true;
                    j = locs.size();
                }
            }
            if (!used) {
                locs.add(n);
            } else {
                i = i--;
            }
        } //End of for loop
        return locs;
    } //End of places
///////////////////////////////////////////////////////////////////////////////
        public static void main(String[] args) {
            Scanner reader = new Scanner(System.in);

            //Ask number of mines to play with
            byte mineAmount = 0;
            boolean valid;
            System.out.println("MINESWEEPER");
            System.out.println("How many mines do you want to play with? 60, 80, or 100");
            do {
                valid = true;
                while (!reader.hasNextLine()){
                    System.out.println("Please type one of the three options for number of mines.");
                    reader.next();
                }
                String inputNum = reader.nextLine();
                if ((!inputNum.equals("60")) && (!inputNum.equals("80")) && (!inputNum.equals("100"))) {
                    valid = false;
                    System.out.println("Please type one of the three options for number of mines.");
                } else {
                    mineAmount = Byte.parseByte(inputNum);
                }
            } while (!valid);

            Board gameBoard = new Board();
            gameBoard.minesRemain = mineAmount;

            //Generate mine location numbers
            Vector<Integer> locationNums;
            locationNums = Places(mineAmount);
            System.out.println(locationNums);

            int x = 0; //horizontal coord
            int y = 0; //vertical coord
            int z; //current location
            for (byte i=1; i<=480; i++) {
                z = locationNums.get(i); //get mine location

                x = (z%30)-1; //set column
                y = (z-(z%30))/30; //set row

                gameBoard.board = 'M'; //Set array size before using, then "get" and "set" fns in Board to use to fill in individual places
            }

        } //end of main
}