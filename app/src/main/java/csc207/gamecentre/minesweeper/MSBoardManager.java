package csc207.gamecentre.minesweeper;

import java.util.ArrayList;

import csc207.gamecentre.general.BoardManager;
import csc207.gamecentre.general.Tile;

/**
 * A minesweeper board manager that extends board manager
 */
public class MSBoardManager extends BoardManager {

    /**
     * the number of rows in the board
     */
    private int numRows;

    /**
     * the number of columns in the board.
     */
    private int numCols;

    /**
     * the number of bombs to be allocated
     */
    private int numBombs = 10;

    /**
     * The number of undo presses left
     */
    private int numUndo;

    /**
     * The list of previous moves made
     */
    private ArrayList<int[]> listMoves = new ArrayList<>();

    /**
     * Initializes the board manager with a rows x cols board.
     *
     * @param rows the number of rows in the board
     * @param cols the number of columns in the board
     */
    MSBoardManager(int rows, int cols) {
        this.numRows = rows;
        this.numCols = cols;
        int[][] board = boardGenerator();
        ArrayList<MSTile> tileBoard = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            for (int b = 0; b < numCols; b++) {
                int value = board[i][b];
                MSTile mst = new MSTile(value);
                tileBoard.add(mst);
            }
        }
        this.currBoard = new MSBoard(tileBoard, rows, cols);
    }

    /**
     * Initializes the board manager with a board from the provided ids.
     *
     * @param ids the tile ids
     */
    MSBoardManager(int[][] ids) {
        this.numRows = ids.length;
        this.numCols = ids[0].length;
        ArrayList<MSTile> tileBoard = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            for (int b = 0; b < numCols; b++) {
                int value = ids[i][b];
                MSTile mst = new MSTile(value);
                tileBoard.add(mst);
            }
        }
        this.currBoard = new MSBoard(tileBoard, numRows, numCols);
    }

    /**
     * Returns number of undo moves left
     *
     * @return number of undo moves left
     */
    int getNumUndo() {
        return numUndo;
    }

    /**
     * Sets number of undo moves left
     *
     * @param numUndo new number of undo moves left
     */
    void setNumUndo(int numUndo) {
        this.numUndo = numUndo;
    }

    /**
     * Generating a minesweeper board
     *
     * @return a minesweeper board represented as an int[][]
     */
    int[][] boardGenerator() {
        int[][] board = new int[numRows][numCols];
        MSBoardManagerUtility.placeBombs(numBombs, board);

        for (int i = 0; i < numRows; i++) {
            for (int b = 0; b < numCols; b++) {
                if (board[i][b] == 0) {
                    int surrBombs = MSBoardManagerUtility.getNumSurroundingBombs(i, b, board);
                    board[i][b] = surrBombs;
                }
            }
        }
        return board;
    }

    @Override
    public MSBoard getBoard() {
        return (MSBoard) this.currBoard;
    }

    /**
     * Checks if the tap is legal or not
     *
     * @param position the position of the tap
     * @return whether or not the tap is valid
     */
    public boolean isValidTap(int position) {
        int row = position / currBoard.getNumCols();
        int col = position % currBoard.getNumCols();
        MSTile tile = (MSTile) currBoard.getTile(row, col);
        return tile.isCovered() && !tile.isFlag() && !puzzleLost();
    }

    /**
     * Checks if the tile is covered
     *
     * @param position the position of the tap
     * @return true if it the tile is covered and the puzzle is not lost
     */
    boolean isValidLongTap(int position) {
        int row = position / currBoard.getNumCols();
        int col = position % currBoard.getNumCols();
        MSTile tile = (MSTile) currBoard.getTile(row, col);
        return tile.isCovered() && !puzzleLost();
    }

    /**
     * Returns if all tiles other than mines have been opened
     *
     * @return whether the minesweeper game has been solved or not
     */
    @Override
    public boolean puzzleSolved() {
        for (Tile t : currBoard) {
            MSTile mst = (MSTile) t;
            if (mst.isCovered() && !mst.isBomb()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns if a mine has been opened
     *
     * @return whether you lost the game or not
     */
    boolean puzzleLost() {
        for (Tile t : currBoard) {
            MSTile mst = (MSTile) t;
            if (!mst.isCovered() && mst.isBomb()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Flag tile at position
     *
     * @param position of tile to be flagged
     */
    void flagTile(int position) {
        int row = position / numRows;
        int col = position % numCols;
        if (isValidLongTap(position)) {
            ((MSBoard) currBoard).toggleFlag(row, col);
        }
    }

    /**
     * Reveal the tile at that position
     *
     * @param position the position of the tap
     */
    public void touchMove(int position) {
        int row = position / currBoard.getNumCols();
        int col = position % currBoard.getNumCols();
        if (isValidTap(position)) {
            openTile(row, col);
            int[] move = {row, col};
            listMoves.add(move);
        }
    }

    /**
     * Returns the number of moves made so far
     *
     * @return the number of moves made
     */
    @Override
    public int getNumMoves() {
        return listMoves.size();
    }

    /**
     * Returns the final score of the game
     *
     * @return the final score based on moves and time
     */
    @Override
    public int getScore() {
        return currBoard.getNumRows() * 10000 - (int) getTileElapsed() / 1000 - getNumMoves();
    }

    /**
     * Returns the number of bombs minus the number of tiles flagged
     *
     * @return the current number of bombs minus flagged tiles
     */
    int getNumBombsRemaining() {
        int currCount = numBombs;
        for (Tile tile : currBoard) {
            if (((MSTile) tile).isFlag()) {
                currCount = currCount - 1;
            }
        }
        return currCount;
    }

    /**
     * Opens tiles until it reaches a non-empty tile
     *
     * @param row the row of the tile
     * @param col the column of the tile
     */
    private void openTile(int row, int col) {
        ((MSBoard) currBoard).reveal(row, col);

        MSTile tile = (MSTile) currBoard.getTile(row, col);
        if (tile.getId() == 0) {
            ArrayList<int[]> coordinates =
                    MSBoardManagerUtility.getSurroundingCoordinates(row, col, numRows, numCols);
            for (int[] surroundingTile : coordinates) {
                MSTile currTile = (MSTile) currBoard.getTile(surroundingTile[0], surroundingTile[1]);
                if (currTile.isCovered() && currTile.getId() != 9) {
                    openTile(surroundingTile[0], surroundingTile[1]);
                }
            }
        }
    }

    /**
     * Close tiles until a non-empty tile is reached
     *
     * @param row the row of the tile
     * @param col the column of the tile
     */
    private void closeTile(int row, int col) {
        MSTile tile = (MSTile) currBoard.getTile(row, col);
        ((MSBoard) currBoard).cover(row, col);

        if (tile.getId() == 0) {
            ArrayList<int[]> coordinates =
                    MSBoardManagerUtility.getSurroundingCoordinates(row, col, numRows, numCols);
            for (int[] surroundingTile : coordinates) {
                MSTile currTile = (MSTile) currBoard.getTile(surroundingTile[0], surroundingTile[1]);
                if (!currTile.isCovered() && currTile.getId() != 9) {
                    closeTile(surroundingTile[0], surroundingTile[1]);
                }
            }
        }
    }

    /**
     * Undo's the board by the last move made
     */
    public void undo() {
        if (numUndo > 0 && getNumMoves() != 0) {
            int[] move = listMoves.remove(listMoves.size() - 1);
            closeTile(move[0], move[1]);
            numUndo = numUndo - 1;
        }
    }
}
