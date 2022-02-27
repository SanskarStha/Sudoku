
import java.io.File;
import java.util.Scanner;

public class Sudoku {
    public static void main(String[] argv) {
        new Sudoku().runOnce();
    }


    int[][] arrayTo2D(int [][][] array) {

        int[][] twoDArray = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++)
                /* array[(row/3) * 3 + col/3][row % 3][col % 3] accesses the 3D array 'array'
                   in the order that fits into the 9x9 2D array 'twoDArray' with the rows
                   scanned from top to bottom and the columns scanned from left to right */
                twoDArray[row][col] = array[(row/3) * 3 + col/3][row % 3][col % 3];
        }
        return twoDArray;
    }


    boolean checkBoxLogic(int[][] cellsOfBox) {

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int i = row;
                int j = col + 1;

                while (i < 3) { // Continue loop until the 3rd row of the array 'cellsOfBox'
                    if (cellsOfBox[row][col] != 0 && j != 3) { // j != 3 prevents ArrayIndexOutOfBoundsException
                        if (cellsOfBox[row][col] == cellsOfBox[i][j])
                            return false;
                    }
                    j += 1;
                    if (j >= 3) {
                        // Go to 0th column of next row
                        j = 0;
                        i += 1;
                    }
                }
            }
        }
        return true;
    }

    boolean checkLineLogic(int[] cellsOfLine) {

        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (cellsOfLine[i] != 0) {
                    if (cellsOfLine[i] == cellsOfLine[j])
                        return false;
                }
            }
        }
        return true;
    }

    boolean isValid(int[][][] cells) {

        int[][] twoDArray = arrayTo2D(cells);
        int[][] flip2DArray = new int[9][9];/* flipping twoDArray so that each column can be checked for duplicate
                                               values by passing the array through checkLineLogic method */
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                flip2DArray[col][row] = twoDArray[row][col];
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!checkBoxLogic(cells[i]) || !checkLineLogic(twoDArray[i]) || !checkLineLogic(flip2DArray[i]))
                return false;
        }
        return true;
    }

    boolean checkWin(int[][][] cells) {
        //TODO
        int[][] twoDArray = arrayTo2D(cells);
        for (int row = 0; row < twoDArray.length; row++) {
            for (int col = 0; col < twoDArray[row].length; col++) {
                if (twoDArray[row][col] == 0 || !isValid(cells))
                    return false;
            }
        }
        return true;
    }


    String filePicking() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a filename: ");
        String filename = scanner.next();
        return filename;
    }

    void printHelpMenu() {

        System.out.println("Help Menu:");
        System.out.println("------------------");
        System.out.println("q\t\tQuit the program");
        System.out.println("a\t\tMove the highlighted cell towards left");
        System.out.println("d\t\tMove the highlighted cell towards right");
        System.out.println("s\t\tMove the highlighted cell towards bottom");
        System.out.println("w\t\tMove the highlighted cell towards top");
        System.out.println(".\t\tRemove the digit of the highlighted cell");
        System.out.println("c\t\tShow the current status of the game");

    }


    void advancePrint(int[][][] cells, int row, int col) {

        int[][] twoDArray = arrayTo2D(cells);
        int x = 0, y = 0; // x indicates row and y indicates column for twoDArray

        for (int i = 0; i < 13; i++) {
            if (i % 4 == 0) {
                //Prints the line with cross-points and horizontal borders
                for (int j = 0; j < 13; j++) {
                    if (j % 4 == 0)
                        System.out.print('\u253c');
                    else
                        System.out.print('\u2500');
                    System.out.print(' ');
                }
            } else {
                //Prints the numbers and vertical borders
                for (int j = 0; j < 13; j++){
                    if (j % 4 == 0)
                        System.out.print('\u2502');
                    else {
                        /* Prints the value of the highlighted cell if the row and column of the cell
                           matches with the highlighted cell */
                        if (x == row && y == col) {
                            switch (twoDArray[row][col]) {
                                case 0:
                                    System.out.print('\u25aa');break;
                                case 1:
                                    System.out.print('\u2081');break;
                                case 2:
                                    System.out.print('\u2082');break;
                                case 3:
                                    System.out.print('\u2083');break;
                                case 4:
                                    System.out.print('\u2084');break;
                                case 5:
                                    System.out.print('\u2085');break;
                                case 6:
                                    System.out.print('\u2086');break;
                                case 7:
                                    System.out.print('\u2087');break;
                                case 8:
                                    System.out.print('\u2088');break;
                                case 9:
                                    System.out.print('\u2089');break;
                            }
                        } else if (twoDArray[x][y] == 0)
                            System.out.print(' ');
                        else
                            //Prints the numbers except for the highlighted cell
                            System.out.print(twoDArray[x][y]);
                        y += 1;
                        if (y == 9) {
                            // Go to 0th column of next row
                            y = 0;
                            x += 1;
                        }
                    }
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }

    void mark(int row, int col, int[][][] cells, char s) {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i == row && j == col) {
                    /* cells[(i/3) * 3 + j/3][i % 3][j % 3] accesses the 3D array 'cells'
                       in the order of a 9x9 2D array with the rows scanned from
                       top to bottom and the columns scanned from left to right */
                    cells[(i/3) * 3 + j/3][i % 3][j % 3] = s - '0';
                }
            }
        }
    }

    boolean same(int[][][]cells, int[][][]originals) {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if (originals[i][j][k] != 0) { /* Not needed to check the value of cells with the value of originals
                                                      if the value of originals is 0 because 0 means the cell of
                                                      originals is empty which is supposed to be filled by the player */
                        if (cells[i][j][k] != originals[i][j][k]) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    void simplePrint(int[][][] array) {
        int[][] twoDArray = arrayTo2D(array);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++)
                System.out.print(twoDArray[row][col] == 0 ? ' ' : (char) (twoDArray[row][col] + '0'));
            System.out.println();
        }

    }

    boolean loadCells(int[][][] cells, String filename) {
        try (Scanner s = new Scanner(new File(filename))) {
            int line = 0;
            while (s.hasNextLine()) {
                //suppose there are 9 lines
                if (line >= 9)
                    throw new Exception("Incorrect number of lines");
                String txt = s.next();
                if (txt.length() != 9)
                    throw new Exception("Incorrect number of characters");
                for (int i = 0; i < 9; i++)
                    cells[(line / 3) * 3 + i / 3][line % 3][i % 3] = txt.charAt(i) - '0';
                line++;
            }
        } catch (Exception e) {
            System.out.println("Error in reading file: " + e);
            return false;
        }
        return true;
    }

    void runOnce() {
        int[][][] cells = new int[9][3][3];

        if (loadCells(cells, filePicking()) == false) {
            System.out.println("The file is not loaded successfully. Check your filePicking method " +
                    "or see if the file is really placed properly in the project directory.");
            cells[0] = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
            cells[4] = new int[][]{{1, 0, 3}, {4, 5, 6}, {0, 8, 9}};
            cells[8] = new int[][]{{4, 0, 3}, {1, 5, 6}, {0, 8, 2}};
        }

        //backup the originalCells
        int[][][] originalCells = new int[9][3][3];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 3; j++)
                for (int k = 0; k < 3; k++)
                    originalCells[i][j][k] = cells[i][j][k];

        simplePrint(cells);
        Scanner scanner = new Scanner(System.in);

        int row = 0, col = 0;
        boolean quit = false;
        advancePrint(cells, row, col);
        for (char s = scanner.next().charAt(0); !quit ;) {

            switch (s) {
                case 'a': col = (col + 8) % 9; break;
                case 's': row = (row + 1) % 9; break;
                case 'w': row = (row + 8) % 9; break;
                case 'd': col = (col + 1) % 9; break;
                case '.': mark(row, col, cells, '0'); break;
                case 'c':
                    if (!isValid(cells))
                        System.out.println("The puzzle is invalid!");
                    else if (!same(cells, originalCells))
                        System.out.println("This is not the same as the original");
                    else
                        System.out.println("So far so good!");
                    break;
                case 'q':
                    quit = true;
                    System.out.println("Quit");
                    continue;
                case 'h':
                    //print help menu
                    printHelpMenu();
                    break;
                default:
                    if (s >= '0' && s <= '9') {
                        mark(row, col, cells, s);
                    }
            }
            advancePrint(cells, row, col);
            if (checkWin(cells) && same(cells, originalCells)) {
                System.out.println("Yeah! you have solved the puzzle!");
                break;
            }
            s = scanner.next().charAt(0);
        }

    }
}
