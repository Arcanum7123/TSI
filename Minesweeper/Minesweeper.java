import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class Minesweeper {
    public static class Board { //16x30=480
        int [][] fullBoard = new int[16][30];
        byte minesRemain;

        public int getValue(int x, int y) {
            return fullBoard[x][y];
        }

        public void setValue(int x, int y, int z) {
            fullBoard[x][y] = z;
        }
    } //end of Board
/////////////////////////////////////////////////////////////////////////////////

    public static Vector<Integer> places(byte mines) {
        Vector<Integer> locs = new Vector<>();
        do {
            double z;
            int n;
            boolean used = false;
            z = (Math.random()*479)+1;
            n = (int) z;

            for (int j = 0; j < locs.size(); j++) { //Checks if unique
                if (n == locs.get(j)) {
                    used = true;
                    j = locs.size();
                }
            }

            if (!used) locs.add(n); //Adds unique values

        } while (locs.size() != mines);
        return locs;
    } //End of places
///////////////////////////////////////////////////////////////////////////////

    public static Board valueMatrix(Board locs) {
        Board values = new Board();
        int curValue = 0;

        for (int i = 0; i <= 15; i++) {
            for (int j = 0; j <= 29; j++){
                switch (i) {
                    case 0:
                        if (j == 0) {
                            curValue = locs.getValue(i+1, j) + locs.getValue(i+1, j+1) + locs.getValue(i, j+1);
                        } else if (j == 29) {
                            curValue = locs.getValue(i+1, j) + locs.getValue(i+1, j-1) + locs.getValue(i, j-1);
                        } else {
                            curValue = locs.getValue(i, j-1) + locs.getValue(i+1, j-1) + locs.getValue(i+1, j) + locs.getValue(i+1, j+1) + locs.getValue(i, j+1);
                        }
                        break;
                    case 15:
                        if (j == 0) {
                            curValue = locs.getValue(i-1, j) + locs.getValue(i-1, j+1) + locs.getValue(i, j+1);
                        } else if (j == 29) {
                            curValue = locs.getValue(i, j-1) + locs.getValue(i-1, j-1) + locs.getValue(i-1, j);
                        } else {
                            curValue = locs.getValue(i, j-1) + locs.getValue(i-1, j-1) + locs.getValue(i-1, j) + locs.getValue(i-1, j+1) + locs.getValue(i, j+1);
                        }
                        break;
                    default:
                        if (j == 0){
                            curValue = locs.getValue(i-1, j) + locs.getValue(i-1, j+1) + locs.getValue(i, j+1) + locs.getValue(i+1, j+1) + locs.getValue(i+1, j);
                        } else if (j ==29) {
                            curValue = locs.getValue(i-1, j) + locs.getValue(i-1, j-1) + locs.getValue(i, j-1) + locs.getValue(i+1, j-1) + locs.getValue(i+1, j);
                        } else {
                            curValue = locs.getValue(i-1, j-1) + locs.getValue(i-1, j) + locs.getValue(i-1, j+1) + locs.getValue(i, j-1) + locs.getValue(i, j+1) + locs.getValue(i+1, j-1) + locs.getValue(i+1, j) + locs.getValue(i+1, j+1);
                        }
                }
                values.setValue(i, j, curValue);
            } //Internal for
        } //External for

        return values;
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    public static void showPlayer(Board pView) { //Codes: 0-8 = number of adj mines; 9 = mine; 10 = flagged; 11 = unknown
        char[] alpha = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'};
        System.out.println("  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30");
        for (int i = 0; i<=15; i++) {
            System.out.print(alpha[i] + " ");
            char output;
            for (int j = 0; j <= 29; j++){
                if (pView.getValue(i, j) == 11) {
                    System.out.print("?  ");
                } else if (pView.getValue(i, j) == 10) {
                    System.out.print("F  ");
                } else if ((1 <= pView.getValue(i, j)) && (pView.getValue(i, j) <= 8)) {
                    System.out.print(pView.getValue(i, j) + "  ");
                } else if (pView.getValue(i, j) == 0) {
                    System.out.print("   ");
                } else if (pView.getValue(i, j) == 9) {
                    System.out.print("*  ");
                }
            }
            System.out.println();
        }
    }
    //////////////////////////////////////////////////////////////////////////////////

    public static Board recalcPlayerView(Board pView, Board mineLocations, Board valueMatrix, int x, int y) {
        Board newPlayerView = pView;

        if (mineLocations.getValue(x, y) == 1) { //If it is a mine, set to be a mine, deal with game over later
            newPlayerView.setValue(x, y, 9);
        } else if ((1<=valueMatrix.getValue(x, y)) && (valueMatrix.getValue(x, y)<=8)) { //If it is not a mine and not a 0, fill the value
            newPlayerView.setValue(x, y, valueMatrix.getValue(x, y));
        } else if (pView.getValue(x, y) == 11) { //If it is a 0, need to check all adjacent and fill values
            newPlayerView.setValue(x, y, 0);
            if (x == 0 && y ==0) { //Check top left corner
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y+1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y+1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y);
            } else if (x == 0 && y == 29) { //Check top right corner
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y-1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y-1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y);
            } else if (x == 15 && y == 0) { //Check bottom left corner
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y+1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y+1);
            } else if (x == 15 && y == 29) { //Check bottom right corner
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y-1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y-1);
            } else if (y == 0 && x != 0 && x != 15) { //Check left edge
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y+1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y+1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y+1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y);
            } else if (y == 29 && x != 0 && x != 15) { //Check right edge
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y-1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y-1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y-1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y);
            } else if (x == 0 && y != 0 && y != 29) { //Check top edge
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y-1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y-1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y+1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y+1);
            } else if (x == 15 && y != 0 && y != 29) { //Check bottom edge
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y-1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y-1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y+1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y+1);
            } else {
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y-1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y+1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y-1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y+1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y-1);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y+1);
            }
        }

        return newPlayerView;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public static int getIndex(String [] arrayToSearch, String input) {
        int index;
        int i=0;

        while (i < arrayToSearch.length) {
            if (arrayToSearch[i].equals(input)){
                return i;
            } else {
                i = i + 1;
            }
        }
        System.out.println("getIndex array: " + Arrays.toString(arrayToSearch));
        System.out.println("I= " + i);

        return i;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////

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


        //Create board with mine locations
        Board mineLocations = new Board();
        mineLocations.minesRemain = mineAmount;

        //Generate mine location numbers
        Vector<Integer> locationNums = new Vector<>();
        locationNums = places(mineAmount);
        //System.out.println(locationNums);

        int x = 0; //vertical coord 0-15
        int y = 0; //horizontal coord 0-29
        int z; //current location 1-480
        for (byte i = 0; i <= (mineAmount-1); i++) {
            z = locationNums.get(i); //get mine location

            x = (z-(z%30))/30;
            y = (z%30);

            mineLocations.setValue(x, y, 1);
        }

        //Value matrix (no. of adj mines)
        Board values = valueMatrix(mineLocations);

        //Player view initial set up
        //Codes: 0-8 = number of adj mines; 9 = mine; 10 = flagged; 11 = unknown
        Board playerPerspective = new Board();

        for (int i = 0; i <= 15; i++){
            for (int j = 0; j <= 29; j++) {
                playerPerspective.setValue(i, j, 11);
            }
        }

        //Actual gameplay
        boolean gOver = false;
        boolean gWin = false;
        char action = 'S';
        do {
            showPlayer(playerPerspective);
            System.out.println();
            System.out.println(mineLocations.minesRemain + " mines remaining.");

            //Test line
            //showPlayer(values);

            //Select player action
            System.out.println("Do you want to select a cell (\"S\"), flag a cell as a mine (\"F\"), or unflag a cell (\"U\")?");
            do { //Get valid input
                valid = true;
                while (!reader.hasNextLine()){
                    System.out.println("Please type \"S\", \"F\", or \"U\".");
                    reader.next();
                }
                String inputAction = reader.nextLine().toUpperCase();
                if ((!inputAction.equals("S")) && (!inputAction.equals("F")) && (!inputAction.equals("U"))) {
                    valid = false;
                    System.out.println("Please type \"S\", \"F\", or \"U\".");
                } else {
                    action = inputAction.toCharArray()[0];
                }
            } while (!valid);

            //Select cell
            int row = 0;
            int column = 0;

            //Get column
            System.out.println("Select column (1-30).");
            do {
                String [] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
                valid = true;

                while (!reader.hasNextLine()) {
                    System.out.println("Please enter a number from 1 to 30 (inclusive).");
                    reader.next();
                }
                String inputColumn = reader.nextLine();
                if (!Arrays.asList(numbers).contains(inputColumn)) {
                    valid = false;
                    System.out.println("Please enter a number from 1 to 30 (inclusive).");
                } else {
                    column = getIndex(numbers, inputColumn);
                }
            } while (!valid);

            //Get row
            System.out.println("Select row (A-P).");
            do { //Get valid input
                String [] alpha = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P"};
                valid = true;

                while (!reader.hasNextLine()){
                    System.out.println("Please type a letter from A to P (inclusive).");
                    reader.next();
                }
                String inputRow = reader.nextLine().toUpperCase();
                if (!Arrays.asList(alpha).contains(inputRow)) {
                    valid = false;
                    System.out.println("Please type a letter from A to P (inclusive).");
                } else {
                    row = getIndex(alpha, inputRow);
                }
            } while (!valid);

            //Reveal relevant cells
            switch (action) {
                case 'S':
                    recalcPlayerView(playerPerspective, mineLocations, values, row, column);
                    if (playerPerspective.getValue(row, column) == 9) {
                        gOver = true;
                    }
                    break;
                case 'F':
                    playerPerspective.setValue(row, column, 10);
                    mineLocations.minesRemain = (byte) (mineLocations.minesRemain - 1);
                    break;
                case 'U':
                    playerPerspective.setValue(row, column, 11);
                    mineLocations.minesRemain = (byte) (mineLocations.minesRemain + 1);
            }

            if (!gOver) {
                int spaces = 0;
                for (int i = 0; i <= 15; i++) {
                    for (int j = 0; j <= 29; j++) {
                        if ((playerPerspective.getValue(i, j) == 10) || (playerPerspective.getValue(i, j) == 11)) {
                            spaces = spaces + 1;
                        }
                    }
                }
                if (spaces == mineAmount){
                    gWin = true;
                }
            }

        } while (!gOver && !gWin); //End of game loop

        //Show player full board
        playerPerspective = values;
        for (int i = 0; i <= 15; i++) {
            for (int j = 0; j <= 29; j++) {
                if (mineLocations.getValue(i, j) == 1) {
                    playerPerspective.setValue(i, j, 9);
                }
            }
        }
        showPlayer(playerPerspective);
        System.out.println();

        //Result message
        if (gOver){
            System.out.println("Ha, loser.");
        } else {
            System.out.println("woo you won...");
        }

    } //end of main
}