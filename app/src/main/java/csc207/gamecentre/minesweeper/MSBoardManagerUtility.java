package csc207.gamecentre.minesweeper;

import java.util.ArrayList;
import java.util.Random;

/**
 * A Minesweeper board manager utility with extra utility methods to help
 */
class MSBoardManagerUtility {

    /**
     * An empty constructor
     */
    private MSBoardManagerUtility() {
    }

    /**
     * Randomly assign numBombs bomb IDs in the 2D array board
     *
     * @param numBombs the number of bombs to place in the board
     * @param board    the board to be filled with bombs
     */
    static void placeBombs(int numBombs, int[][] board) {
        int numRows = board.length;
        int numCols = board[0].length;
        int numTiles = numRows * numCols;
        Random random = new Random();

        for (int i = 0; i < numBombs; i++) {
            int value = random.nextInt(numTiles);
            int row = value / numRows;
            int col = value % numCols;
            if (board[row][col] == 9) {
                numBombs++;
            } else {
                board[row][col] = 9;
            }
        }
    }

    /**
     * Returns a list of coordinates of tiles that border the tile at row and col.
     *
     * @param row       the row of the tile
     * @param col       the column of the tile
     * @param boardRows the number of rows of the board
     * @param boardCols the number of rows of the board
     * @return the surrounding coordinates of the tile
     */
    static ArrayList<int[]> getSurroundingCoordinates(int row, int col, int boardRows, int boardCols) {
        ArrayList<int[]> surCoordinates = new ArrayList<>();
        int[] rows = {row - 1, row, row + 1};
        int[] cols = {col - 1, col, col + 1};

        for (int r : rows) {
            for (int c : cols) {
                if (!(r == boardRows || r == -1 || c == boardCols || c == -1 || (r == row && c == col))) {
                    int[] singleCoor = {r, c};
                    surCoordinates.add(singleCoor);
                }
            }
        }
        return surCoordinates;
    }

    /**
     * Returns the number of bombs surrounding the tile at row and col in board
     *
     * @param row   the row of the tile
     * @param col   the column of the tile
     * @param board the board containing the tile
     * @return the number of bombs surrounding the tile
     */
    static int getNumSurroundingBombs(int row, int col, int[][] board) {
        int numRows = board.length;
        int numCols = board[0].length;
        int numBombs = 0;
        ArrayList<int[]> surrounding = getSurroundingCoordinates(row, col, numRows, numCols);

        for (int[] c : surrounding) {
            if (board[c[0]][c[1]] == 9) {
                numBombs++;
            }
        }
        return numBombs;
    }
}
