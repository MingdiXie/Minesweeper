package csc207.gamecentre.minesweeper;

import java.util.ArrayList;
import java.util.Iterator;

import csc207.gamecentre.general.Board;

/**
 * A minesweeper board.
 */
class MSBoard extends Board {
    /**
     * Creates a new board of tiles.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param tiles the tiles that make up the board
     * @param rows  the number of rows in the board
     * @param cols  the number of columns in the board
     */
    MSBoard(ArrayList<MSTile> tiles, int rows, int cols) {
        super(rows, cols);
        Iterator<MSTile> iter = tiles.iterator();

        for (int row = 0; row != this.getNumRows(); row++) {
            for (int col = 0; col != this.getNumCols(); col++) {
                setTile(row, col, iter.next());
            }
        }
    }

    /**
     * Reveals the background of the tile at row and col.
     *
     * @param row the row of the chosen tile
     * @param col the column of the chosen tile
     */
    void reveal(int row, int col) {
        MSTile tile = (MSTile) getTile(row, col);
        tile.reveal();

        setChanged();
        notifyObservers();
    }

    /**
     * Sets the surface of the tile at row and col to the empty surface.
     *
     * @param row the row of the chosen tile
     * @param col the column of the chosen tile
     */
    void cover(int row, int col) {
        MSTile tile = (MSTile) getTile(row, col);
        tile.setEmpty();

        setChanged();
        notifyObservers();
    }

    /**
     * Sets the surface of the tile at row and col to a flag if its surface is empty, or to an empty
     * surface if it's already a flag.
     *
     * @param row the row of the chosen tile
     * @param col the column of the chosen tile
     */
    void toggleFlag(int row, int col) {
        MSTile tile = (MSTile) getTile(row, col);
        if (tile.isFlag()) {
            tile.setEmpty();
        } else {
            tile.setFlag();
        }
        setChanged();
        notifyObservers();
    }
}
