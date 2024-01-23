package org.example;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class MinesweeperMaven {
    public static class Board { //16x30=480
        int [][] fullBoard;
        public Board(int rows, int columns) {
            this.fullBoard = new int[rows][columns];
        }
        byte minesRemain;

        public int getValue(int x, int y) {
            return fullBoard[x][y];
        }

        public void setValue(int x, int y, int z) {
            fullBoard[x][y] = z;
        }
    } //end of Board
    /////////////////////////////////////////////////////////////////////////////////

    public static Vector<Integer> places(byte mines, int rows, int cols) {
        Vector<Integer> locs = new Vector<>();
        do {
            double z;
            int n;
            boolean used = false;
            z = (Math.random()*((rows * cols) - 1)) + 1;
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
    }
    ///////////////////////////////////////////////////////////////////////////////

    public static Board valueMatrix(Board locs, int rows, int cols) {
        Board values = new Board(rows, cols);
        int curValue = 0;

        for (int i = 0; i <= (rows - 1); i++) {
            for (int j = 0; j <= (cols - 1); j++){
                if (i == 0) {
                    if (j == 0) {
                        curValue = locs.getValue(i+1, j) + locs.getValue(i+1, j+1) + locs.getValue(i, j+1);
                    } else if (j == (cols - 1)) {
                        curValue = locs.getValue(i+1, j) + locs.getValue(i+1, j-1) + locs.getValue(i, j-1);
                    } else {
                        curValue = locs.getValue(i, j-1) + locs.getValue(i+1, j-1) + locs.getValue(i+1, j) + locs.getValue(i+1, j+1) + locs.getValue(i, j+1);
                    }
                } else if (i == (rows - 1)) {
                    if (j == 0) {
                        curValue = locs.getValue(i-1, j) + locs.getValue(i-1, j+1) + locs.getValue(i, j+1);
                    } else if (j == (cols - 1)) {
                        curValue = locs.getValue(i, j-1) + locs.getValue(i-1, j-1) + locs.getValue(i-1, j);
                    } else {
                        curValue = locs.getValue(i, j-1) + locs.getValue(i-1, j-1) + locs.getValue(i-1, j) + locs.getValue(i-1, j+1) + locs.getValue(i, j+1);
                    }
                } else {
                    if (j == 0){
                        curValue = locs.getValue(i-1, j) + locs.getValue(i-1, j+1) + locs.getValue(i, j+1) + locs.getValue(i+1, j+1) + locs.getValue(i+1, j);
                    } else if (j == (cols - 1)) {
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

    public static void showPlayer(Board pView, int rows, int cols) { //Codes: 0-8 = number of adj mines; 9 = mine; 10 = flagged; 11 = unknown
        char[] alpha = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T'};
        int count = 1;
        String colour;

        System.out.println();
        System.out.print("\u001B[37m" + "  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 ");
        for (int i = 21; i <= cols; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i<=(rows - 1); i++) {
            if (count > 0) {
                colour = "\u001B[34m";
            } else {
                colour = "\u001B[38m";
            }
            System.out.print(colour + alpha[i] + " ");

            for (int j = 0; j <= (cols - 1); j++){
                if (pView.getValue(i, j) == 11) {
                    System.out.print("█  ");
                } else if (pView.getValue(i, j) == 10) {
                    System.out.print("¶  ");
                } else if ((1 <= pView.getValue(i, j)) && (pView.getValue(i, j) <= 8)) {
                    System.out.print(pView.getValue(i, j) + "  ");
                } else if (pView.getValue(i, j) == 0) {
                    System.out.print("   ");
                } else if (pView.getValue(i, j) == 9) {
                    System.out.print("\u001B[31m" + "*  " + colour);
                }
            }
            count = count * -1;
            System.out.print(alpha[i] + " ");
            System.out.println();
        }

        System.out.print("\u001B[37m" + "  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 ");
        for (int i = 21; i <= cols; i++) {
            System.out.print(i + " ");
        }

        System.out.println("\u001B[38m");
    }
    //////////////////////////////////////////////////////////////////////////////////

    public static Board recalcPlayerView(Board pView, Board mineLocations, Board valueMatrix, int x, int y, int rows, int cols) {
        Board newPlayerView = pView;

        if (mineLocations.getValue(x, y) == 1) { //If it is a mine, set to be a mine, deal with game over later
            newPlayerView.setValue(x, y, 9);
        } else if ((1<=valueMatrix.getValue(x, y)) && (valueMatrix.getValue(x, y)<=8)) { //If it is not a mine and not a 0, fill the value
            newPlayerView.setValue(x, y, valueMatrix.getValue(x, y));
        } else if (pView.getValue(x, y) == 11) { //If it is a 0, need to check all adjacent and fill values
            newPlayerView.setValue(x, y, 0);
            if (x == 0 && y ==0) { //Check top left corner
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y+1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y+1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y, rows, cols);
            } else if (x == 0 && y == (cols - 1)) { //Check top right corner
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y-1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y-1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y, rows, cols);
            } else if (x == (rows - 1) && y == 0) { //Check bottom left corner
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y+1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y+1, rows, cols);
            } else if (x == (rows - 1) && y == (cols - 1)) { //Check bottom right corner
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y-1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y-1, rows, cols);
            } else if (y == 0 && x != 0 && x != (rows - 1)) { //Check left edge
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y+1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y+1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y+1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y, rows, cols);
            } else if (y == (cols - 1) && x != 0 && x != (rows - 1)) { //Check right edge
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y-1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y-1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y-1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y, rows, cols);
            } else if (x == 0 && y != 0 && y != (cols - 1)) { //Check top edge
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y-1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y-1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y+1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y+1, rows, cols);
            } else if (x == (rows - 1) && y != 0 && y != (cols - 1)) { //Check bottom edge
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y-1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y-1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y+1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y+1, rows, cols);
            } else {
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y-1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x-1, y+1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y-1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x, y+1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y-1, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y, rows, cols);
                newPlayerView = recalcPlayerView(newPlayerView, mineLocations, valueMatrix, x+1, y+1, rows, cols);
            }
        }

        return newPlayerView;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////

    public static int getIndex(String [] arrayToSearch, String input) {
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
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static Board revealAdjacent(Board pView, Board mineLocations, Board valueMatrix, int x, int y, int rows, int cols) {
        if ((x - 1) >= 0) {
            if ((y - 1) >= 0) {
                if (pView.getValue(x - 1, y - 1) == 11) {
                    pView = recalcPlayerView(pView, mineLocations, valueMatrix, x - 1, y - 1, rows, cols);
                }
            }
            if ((y + 1) <= (cols - 1)) {
                if (pView.getValue(x - 1, y + 1) == 11) {
                    pView = recalcPlayerView(pView, mineLocations, valueMatrix, x - 1, y + 1, rows, cols);
                }
            }
            if (pView.getValue(x - 1, y) == 11) {
                pView = recalcPlayerView(pView, mineLocations, valueMatrix, x - 1, y, rows, cols);
            }
        }
        if ((x + 1) <= (rows - 1)) {
            if ((y - 1) >= 0) {
                if (pView.getValue(x + 1, y - 1) == 11) {
                    pView = recalcPlayerView(pView, mineLocations, valueMatrix, x + 1, y - 1, rows, cols);
                }
            }
            if ((y + 1) <= (cols - 1)) {
                if (pView.getValue(x + 1, y + 1) == 11) {
                    pView = recalcPlayerView(pView, mineLocations, valueMatrix, x + 1, y + 1, rows, cols);
                }
            }
            if (pView.getValue(x + 1, y) == 11) {
                pView = recalcPlayerView(pView, mineLocations, valueMatrix, x + 1, y, rows, cols);
            }
        }
        if ((y - 1) >= 0) {
            if (pView.getValue(x, y - 1) == 11) {
                pView = recalcPlayerView(pView, mineLocations, valueMatrix, x, y - 1, rows, cols);
            }
        }
        if ((y + 1) <= (cols - 1)) {
            if (pView.getValue(x, y + 1) == 11) {
                pView = recalcPlayerView(pView, mineLocations, valueMatrix, x, y + 1, rows, cols);
            }
        }
        return pView;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    public static byte getNumberOfMines() {
        Scanner reader = new Scanner(System.in);
        byte mineAmount = 0;
        boolean valid;
        System.out.println("MINESWEEPER");
        System.out.println("How many mines do you want to play with? 60, 80, or 100");
        do {
            valid = true;
            while (!reader.hasNextLine()) {
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
        return mineAmount;
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    public static char getBoardSize() {
        Scanner reader = new Scanner(System.in);
        boolean valid;
        char gameSize = 'M';
        System.out.println();
        System.out.println("Do you want to play on a small (\"S\"), medium (\"M\"), or large (\"L\") board?");
        do {
            valid = true;
            while (!reader.hasNextLine()) {
                System.out.println("Please enter \"S\", \"M\", or \"L\" for your board size.");
                reader.next();
            }
            String inputSize = reader.nextLine().toUpperCase();
            if ((!inputSize.equals("S")) && (!inputSize.equals("M")) && (!inputSize.equals("L"))) {
                valid = false;
                System.out.println("Please enter \"S\", \"M\", or \"L\" for your board size.");
            } else {
                gameSize = inputSize.toCharArray()[0];
            }
        } while (!valid);
        return gameSize;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static int[] setBoardDims(char gameSize) {
        int [] boardDims = {1, 1};
        switch (gameSize) {
            case 'S':
                boardDims = new int[]{12, 20};
                break;
            case 'M':
                boardDims = new int[]{16, 30};
                break;
            case 'L':
                boardDims = new int[]{20, 40};
                break;
        }
        return boardDims;
    }
    /////////////////////////////////////////////////////////////////////////////////

    public static Board placeMinesInGrid(Vector<Integer> locNumbers, int mineCount, int rows, int cols) {
        Board mineLocations = new Board(rows, cols);
        int x; //vertical coord 0-15
        int y; //horizontal coord 0-29
        int z; //current location 1-480

        for (byte i = 0; i <= (mineCount - 1); i++) {
            z = locNumbers.get(i); //get mine location

           /* if (i == (mineCount - 1)) {
                z=1;
            }*/

            x = (z - (z % cols)) / cols; //set row - Wrong?
            y = (z % cols); //set column - Wrong?

            mineLocations.setValue(x, y, 1);
        }

        return mineLocations;
    }

    public static void main(String[] args) {
        boolean playAgain;
        do {
            Scanner reader = new Scanner(System.in);
            boolean valid;

            //Get number of mines to play with
            byte mineAmount = getNumberOfMines();

            //Set game board size/dimensions
            char gameSize = getBoardSize();
            int [] boardDims = setBoardDims(gameSize); //boardsDims[0] = number of rows; boardDims[1]= number of columns

            //Generate mine location numbers (1-number of cells)
            Vector<Integer> locationNums = places(mineAmount, boardDims[0], boardDims[1]);

            //Create board with mine locations
            Board mineLocations = placeMinesInGrid(locationNums, mineAmount, boardDims[0], boardDims[1]);
            mineLocations.minesRemain = mineAmount;





            //Value matrix (no. of adj mines)
            Board values = valueMatrix(mineLocations, boardDims[0], boardDims[1]);

            //Player view initial set up
            //Codes: 0-8 = number of adj mines; 9 = mine; 10 = flagged; 11 = unknown
            Board playerPerspective = new Board(boardDims[0], boardDims[1]);

            for (int i = 0; i <= (boardDims[0] - 1); i++) {
                for (int j = 0; j <= (boardDims[1] - 1); j++) {
                    playerPerspective.setValue(i, j, 11);
                }
            }

            //Actual gameplay
            boolean gOver = false;
            boolean gWin = false;

            char action = 'S';
            int row = 0;
            int column = 0;
            do {
                showPlayer(playerPerspective, boardDims[0], boardDims[1]);
                System.out.println();
                System.out.println(mineLocations.minesRemain + " mines remaining.");

                String inputColumn = "X";
                String inputRow = "X";

                do {
                    //Select player action
                    System.out.println("Do you want to select a cell (\"S\"), flag a cell as a mine (\"F\"), or unflag a cell (\"U\")?");
                    do { //Get valid input
                        valid = true;
                        while (!reader.hasNextLine()) {
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
                    //Get column
                    System.out.println("Select column (1-" + boardDims[1] + "), or enter \"X\" to undo.");
                    do {
                        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40"};
                        valid = true;

                        while (!reader.hasNextLine()) {
                            System.out.println("Please enter a number from 1 to " + boardDims[1] + " (inclusive), or enter \"X\" to undo.");
                            reader.next();
                        }
                        inputColumn = reader.nextLine().toUpperCase();
                        if (!Arrays.asList(numbers).contains(inputColumn) && !inputColumn.equals("X")) {
                            valid = false;
                            System.out.println("Please enter a number from 1 to " + boardDims[1] + " (inclusive), or enter \"X\" to undo.");
                        } else if (!inputColumn.equals("X")) {
                            column = getIndex(numbers, inputColumn);
                        }
                    } while (!valid);

                    //Get row
                    if (!inputColumn.equals("X")) {
                        String[] alpha = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"};
                        System.out.println("Select row (A-" + alpha[boardDims[0] - 1] + "), or enter \"X\" to undo.");
                        do { //Get valid input
                            valid = true;

                            while (!reader.hasNextLine()) {
                                System.out.println("Please type a letter from A to " + alpha[boardDims[0] - 1] + " (inclusive), or enter \"X\" to undo.");
                                reader.next();
                            }
                            inputRow = reader.nextLine().toUpperCase();
                            if (!Arrays.asList(alpha).contains(inputRow) && !inputRow.equals("X")) {
                                valid = false;
                                System.out.println("Please type a letter from A to " + alpha[boardDims[0] - 1] + " (inclusive), or enter \"X\" to undo.");
                            } else if (!inputRow.equals("X")) {
                                row = getIndex(alpha, inputRow);
                            }
                        } while (!valid);
                    }
                } while (inputColumn.equals("X") || inputRow.equals("X"));

                //Reveal relevant cells
                switch (action) {
                    case 'S':
                        //Need to lose when revealAdj hits a mine
                        if ((playerPerspective.getValue(row, column) >=1) && playerPerspective.getValue(row, column) <= 8) {
                            revealAdjacent(playerPerspective, mineLocations, values, row, column, boardDims[0], boardDims[1]);
                        } else {
                            recalcPlayerView(playerPerspective, mineLocations, values, row, column, boardDims[0], boardDims[1]);
                            if (playerPerspective.getValue(row, column) == 9) {
                                gOver = true;
                            }
                        }
                        break;
                    case 'F':
                        if (playerPerspective.getValue(row, column) == 11) {
                            playerPerspective.setValue(row, column, 10);
                            mineLocations.minesRemain = (byte) (mineLocations.minesRemain - 1);
                        }
                        break;
                    case 'U':
                        if (playerPerspective.getValue(row, column) == 10) {
                            playerPerspective.setValue(row, column, 11);
                            mineLocations.minesRemain = (byte) (mineLocations.minesRemain + 1);
                        }
                }

                if (!gOver) {
                    int spaces = 0;
                    for (int i = 0; i <= (boardDims[0] - 1) ; i++) {
                        for (int j = 0; j <= (boardDims[1] - 1); j++) {
                            if ((playerPerspective.getValue(i, j) == 10) || (playerPerspective.getValue(i, j) == 11)) {
                                spaces = spaces + 1;
                            }
                        }
                    }
                    if (spaces == mineAmount) {
                        gWin = true;
                    }
                }

            } while (!gOver && !gWin); //End of game loop

            //Show player full board
            playerPerspective = values;
            for (int i = 0; i <= (boardDims[0] - 1); i++) {
                for (int j = 0; j <= (boardDims[1] - 1); j++) {
                    if (mineLocations.getValue(i, j) == 1) {
                        playerPerspective.setValue(i, j, 9);
                    }
                }
            }
            showPlayer(playerPerspective, boardDims[0], boardDims[1]);
            System.out.println();

            //Result message
            if (gOver) {
                System.out.println("Ha, loser.");
            } else {
                System.out.println("woo you won...");
            }

            //Play again
            System.out.println();
            System.out.println("Play again? Y/N");
            playAgain = false;
            do {
                valid = true;
                while (!reader.hasNextLine()) {
                    System.out.println("Please type \"Y\" or \"N\".");
                    reader.next();
                }
                String inputAns = reader.nextLine().toUpperCase();
                if ((!inputAns.equals("Y")) && (!inputAns.equals("N"))) {
                    valid = false;
                    System.out.println("Please type \"Y\" or \"N\".");
                } else if (inputAns.equals("Y")) {
                    playAgain = true;
                }
            } while (!valid);

        } while (playAgain);
    } //end of main
}