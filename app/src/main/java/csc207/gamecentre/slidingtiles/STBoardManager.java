package csc207.gamecentre.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import csc207.gamecentre.general.BoardManager;
import csc207.gamecentre.general.Tile;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class STBoardManager extends BoardManager implements Serializable {

    /**
     * The list of previous board states
     */
    private ArrayList<STBoard> prevBoards;

    /**
     * Number of undo
     */
    private int numUndo;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    STBoardManager(STBoard board) {
        super(board);
        this.prevBoards = new ArrayList<>();
    }

    /**
     * Manage a new shuffled board.
     */
    STBoardManager(int sideLength) {
        super();
        List<STTile> tiles = new ArrayList<>();
        final int numTiles = sideLength * sideLength;
        for (int tileNum = 0; tileNum != numTiles - 1; tileNum++) {
            tiles.add(new STTile(tileNum + 1, tileNum + 1));
        }
        tiles.add(new STTile(numTiles, 25));
        Collections.shuffle(tiles);

        boolean isSolvable = isSolvable(tiles);
        while (!isSolvable) {
            Collections.shuffle(tiles);
            isSolvable = isSolvable(tiles);
        }

        this.currBoard = new STBoard(tiles, sideLength);
        this.prevBoards = new ArrayList<>();
    }


    /**
     * Returns if the tiles in its current order is solvable
     *
     * @param tiles the list of tiles
     * @return if a board made from tiles is solvable
     */
    private boolean isSolvable(List<STTile> tiles) {
        int numCols = (int) Math.sqrt(tiles.size());
        int numInversions = numInversions(tiles);
        if (numCols % 2 == 0) {
            int blankRow = blankRow(tiles);
            if (blankRow % 2 == 0) {
                // if blank on even row counting from bottom, number of inversions is odd
                return numInversions % 2 != 0;
            } else {
                // if blank on odd row counting from bottom, then number inversions is even
                return numInversions % 2 == 0;
            }
        } else {
            // number of inversions is even
            return numInversions % 2 == 0;
        }
    }

    /**
     * Returns the row number from the bottom the blank tile is on
     *
     * @param tiles list of tiles in order
     * @return row number from bottom blank tile is on
     */
    private int blankRow(List<STTile> tiles) {
        int numRows = (int) Math.sqrt(tiles.size());
        int blankTilePosition = 0;
        for (int i = 0; i < tiles.size(); i++) {
            STTile currTile = tiles.get(i);
            if (currTile.getId() == tiles.size()) {
                blankTilePosition = i;
            }
        }
        int row = blankTilePosition / numRows;
        return numRows - row;
    }

    /**
     * Returns the number of inversions in a list of tiles
     *
     * @param tiles list of Sliding Tiles tiles
     * @return number of inversions
     */
    private int numInversions(List<STTile> tiles) {
        STTile currTile;
        int numTiles = tiles.size();
        int totalInversions = 0;
        // check every tile for number of inversions
        for (int i = 0; i < numTiles; i++) {
            currTile = tiles.get(i);
            STTile prevTile;
            int inversions = currTile.getId() - 1;
            // check all tiles before currTile if id < currtile id
            for (int j = 0; j < i; j++) {
                prevTile = tiles.get(j);
                // if prevtile < curr tile, then correct,
                if (prevTile.getId() < currTile.getId()) {
                    inversions = inversions - 1;
                }
                // if blank tile, don't count inversions
                if (currTile.getId() == tiles.size()) {
                    inversions = 0;
                }
            }
            totalInversions = totalInversions + inversions;
        }
        return totalInversions;
    }

    /**
     * Return the number of moves taken.
     *
     * @return number of moves taken
     */
    public int getNumMoves() {
        return prevBoards.size();
    }

    /**
     * set the number of undo
     */
    void setNumUndo(int num) {
        numUndo = num;
    }

    /**
     * return the number of undo
     *
     * @return number of undo left
     */
    private int getNumUndo() {
        return numUndo;
    }

    @Override
    public STBoard getBoard() {
        return (STBoard) this.currBoard;
    }

    /**
     * Returns the score based on moves and time taken.
     *
     * @return the score
     */
    public int getScore() {
        return currBoard.getNumRows() * 10000 - (int) getTileElapsed() / 1000 - getNumMoves();
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    @Override
    public boolean puzzleSolved() {
        boolean solved = true;
        Iterator<Tile> iter = this.getBoard().iterator();
        STTile curr = (STTile) iter.next();
        int correct = 1;
        while (iter.hasNext() && solved) {
            int tileNum = curr.getId();
            if (correct != tileNum) {
                solved = false;
            }
            curr = (STTile) iter.next();
            correct++;
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    @Override
    public boolean isValidTap(int position) {

        int row = position / currBoard.getNumCols();
        int col = position % currBoard.getNumCols();
        int blankId = currBoard.numTiles();

        STTile above = row == 0 ? null : (STTile) currBoard.getTile(row - 1, col);
        STTile below = row == currBoard.getNumRows() - 1 ? null : (STTile) currBoard.getTile(row + 1, col);
        STTile left = col == 0 ? null : (STTile) currBoard.getTile(row, col - 1);
        STTile right = col == currBoard.getNumCols() - 1 ? null : (STTile) currBoard.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    @Override
    public void touchMove(int position) {
        int row = position / currBoard.getNumRows();
        int col = position % currBoard.getNumCols();
        int blankId = currBoard.numTiles();

        if (isValidTap(position)) {
            Integer[] blankLocation = currBoard.getTileLocation(blankId);
            STBoard prevBoard = new STBoard((STBoard) currBoard);
            this.prevBoards.add(prevBoard);
            ((STBoard) currBoard).swapTiles(row, col, blankLocation[0], blankLocation[1]);
        }
    }

    /**
     * Returns if a previous state of the board exists
     *
     * @param prevState the number of states ago
     * @return if there exists a state from prevState many moves ago
     */
    private boolean existsPrevState(int prevState) {
        return this.prevBoards.size() - prevState >= 0;
    }

    /**
     * Undoes the board to moves states ago if it exists
     *
     * @param moves the number of moves to undo
     */
    void undo(int moves) {
        if (existsPrevState(moves)) {
            if (getNumUndo() > 0) {
                STBoard newCurrBoard = this.prevBoards.get(this.prevBoards.size() - moves);
                STBoard newBoard = new STBoard(newCurrBoard);
                ((STBoard) currBoard).updateTiles(newBoard);

                int remaining = moves;
                while (remaining > 0) {
                    prevBoards.remove(this.prevBoards.size() - 1);
                    remaining = remaining - 1;
                }
                setNumUndo(getNumUndo() - 1);
            }
        }
    }
}
