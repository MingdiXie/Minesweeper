package csc207.gamecentre.memorypuzzle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import csc207.gamecentre.general.BoardManager;
import csc207.gamecentre.general.Tile;

/**
 * A Board Manager refined for Memory Puzzle that extends BoardManager
 */
public class MPBoardManager extends BoardManager implements Serializable {

    /**
     * The current move in progress
     */
    Integer[][] currMove = new Integer[2][2];

    /**
     * The previous move made
     */
    private Integer[][] prevMove = new Integer[2][2];

    /**
     * The number of moves made
     */
    private int numMoves;

    /**
     * The number of undo moves allowed left
     */
    private int numUndo;

    /**
     * Creates a newly shuffled Memory Puzzle Board Manager for a 6 by 6 board
     */
    MPBoardManager() {
        super();
        List<MPTile> tiles = new ArrayList<>();
        int numUniqueTiles = 18;
        for (int tileNum = 0; tileNum < numUniqueTiles; tileNum++) {
            tiles.add(new MPTile(tileNum + 1));
            tiles.add(new MPTile(tileNum + 1));
        }
        Collections.shuffle(tiles);

        this.currBoard = new MPBoard(tiles);
        numMoves = 0;
        numUndo = 1;
    }

    /**
     * Creates a Memory Puzzle Board Manager for board
     *
     * @param board the Memory Puzzle board to be managed
     */
    public MPBoardManager(MPBoard board) {
        super();
        this.currBoard = board;
        numMoves = 0;
        numUndo = 1;
    }

    /**
     * A getter for the board
     *
     * @return the current board
     */
    @Override
    public MPBoard getBoard() {
        return (MPBoard) this.currBoard;
    }

    /**
     * Returns if the current tile tapped is unflipped
     *
     * @param position the position of the tile tapped
     * @return if the tile is unflipped
     */
    @Override
    public boolean isValidTap(int position) {
        int row = position / currBoard.getNumCols();
        int col = position % currBoard.getNumCols();
        MPTile curr = (MPTile) currBoard.getTile(row, col);
        return !curr.isFlippedUp();
    }

    /**
     * Flips the tile at position
     *
     * @param position the position of the Tile
     */
    @Override
    public void touchMove(int position) {
        int row = position / currBoard.getNumCols();
        int col = position % currBoard.getNumCols();
        ((MPBoard) currBoard).flipTile(row, col);
        Integer[] thisMove = {row, col};

        // Add to current move
        if (currMove[0][0] == null) {
            currMove[0] = thisMove;
        } else {
            currMove[1] = thisMove;
        }
        numMoves++;
    }

    /**
     * Resets the move if curr move is full and the two tiles didn't match
     * <p>
     * Important: must be called after every touchMove
     */
    void resetMove() {
        // If curr move now full then decide if keep or reflip
        if (currMove[1][0] != null) {
            // clear current move
            prevMove = currMove;
            currMove = new Integer[2][2];
            int firstTile = currBoard.getTile(prevMove[0][0], prevMove[0][1]).getId();
            int secondTile = currBoard.getTile(prevMove[1][0], prevMove[1][1]).getId();
            // if they don't match then reflip
            if (firstTile != secondTile) {
                ((MPBoard) currBoard).flipTile(prevMove[0][0], prevMove[0][1]);
                ((MPBoard) currBoard).flipTile(prevMove[1][0], prevMove[1][1]);
            }
        }
    }

    /**
     * Returns if all tiles in the puzzle are unflipped
     *
     * @return if all tiles are unflipped
     */
    @Override
    public boolean puzzleSolved() {
        boolean solved = true;
        Iterator<Tile> iter = this.getBoard().iterator();
        MPTile curr = (MPTile) iter.next();
        while (iter.hasNext() && solved) {
            if (!curr.isFlippedUp()) {
                solved = false;
            }
            curr = (MPTile) iter.next();
        }
        return solved;
    }

    /**
     * Returns the number of moves made in the game
     *
     * @return the number of moves made
     */
    @Override
    public int getNumMoves() {
        return numMoves;
    }

    /**
     * Returns the score based on moves and time taken.
     *
     * @return the score
     */
    @Override
    public int getScore() {
        return currBoard.getNumRows() * 10000 - (int) getTileElapsed() / 1000 - 2 * this.getNumMoves();
    }

    /**
     * Undoes the current board by one state
     */
    public void undo() {
        if (numUndo > 0 && numMoves > 0) {
            // undo last flip
            if (currMove[1][0] == null) {
                // currently in the middle of a move
                ((MPBoard) currBoard).flipTile(currMove[0][0], currMove[0][1]);
                currMove = new Integer[2][2];
            } else {
                // completed a move
                int firstTile = currBoard.getTile(currMove[0][0], currMove[0][1]).getId();
                int secondTile = currBoard.getTile(currMove[1][0], currMove[1][1]).getId();
                // if they don't match reflip first tile
                if (firstTile != secondTile) {
                    ((MPBoard) currBoard).flipTile(currMove[0][0], currMove[0][1]);
                } else {
                    // if they do match reflip second tile
                    ((MPBoard) currBoard).flipTile(currMove[1][0], currMove[1][1]);
                }
                currMove = prevMove;
                currMove[1] = new Integer[2];
            }
            numUndo = numUndo - 1;
            numMoves = numMoves - 1;
        }
    }
}