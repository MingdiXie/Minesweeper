package csc207.gamecentre.slidingtiles;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import csc207.gamecentre.general.Board;

/**
 * The sliding tiles board.
 */
class STBoard extends Board implements Serializable {

    /**
     * A new board of tiles.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param tiles the tiles for the board
     */
    STBoard(List<STTile> tiles, int sideLength) {
        super(sideLength, sideLength);
        Iterator<STTile> iter = tiles.iterator();

        for (int row = 0; row != this.getNumRows(); row++) {
            for (int col = 0; col != this.getNumCols(); col++) {
                setTile(row, col, iter.next());
            }
        }
    }

    /**
     * A board that is a copy of the copyBoard
     *
     * @param copyBoard the board to copy
     */
    STBoard(STBoard copyBoard) {
        super(copyBoard.getNumRows(), copyBoard.getNumCols());

        // Creates a copy of the copyboard tiles
        for (int row = 0; row != this.getNumRows(); row++) {
            for (int col = 0; col != this.getNumCols(); col++) {
                setTile(row, col, copyBoard.getTile(row, col));
            }
        }
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        STTile swap1 = (STTile) getTile(row1, col1);
        STTile swap2 = (STTile) getTile(row2, col2);
        setTile(row1, col1, swap2);
        setTile(row2, col2, swap1);

        setChanged();
        notifyObservers();
    }

    /**
     * Updates the tiles of the board to be the same as newBoard
     *
     * @param newBoard The board whose tiles we want the current board to emulate
     */
    void updateTiles(STBoard newBoard) {
        for (int row = 0; row != getNumRows(); row++) {
            for (int col = 0; col != getNumCols(); col++) {
                setTile(row, col, newBoard.getTile(row, col));
            }
        }

        setChanged();
        notifyObservers();
    }
}
