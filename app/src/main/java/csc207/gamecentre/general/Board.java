package csc207.gamecentre.general;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Observable;

/**
 * A Board with Tiles on it
 */
public abstract class Board extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    private int numRows;

    /**
     * The number of rows.
     */
    private int numCols;

    /**
     * The tiles on the board.
     */
    private Tile[][] tiles;

    /**
     * A generic Board with numRows and numCols and a blank array of tiles
     *
     * @param numRows the number of rows
     * @param numCols the number of columns
     */
    public Board(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.tiles = new Tile[numRows][numCols];
    }

    /**
     * Returns the number of rows
     *
     * @return the number of rows
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Returns the number of columns
     *
     * @return the number of columns
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    public int numTiles() {
        return numCols * numRows;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Set the tile at (row, col) as tile
     *
     * @param row  the tile row
     * @param col  the tile column
     * @param tile the new tile
     */
    protected void setTile(int row, int col, Tile tile) {
        tiles[row][col] = tile;
    }

    /**
     * Returns the tiles
     *
     * @return the tiles
     */
    protected Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Returns the location of the tile with id id
     *
     * @param id id of the tile we want to find
     * @return integer array of the location of the blank tile with row at 0 and col at 1
     */
    public Integer[] getTileLocation(int id) {
        Integer[] location = new Integer[2];

        for (int row = 0; row != getNumRows(); row++) {
            for (int col = 0; col != getNumCols(); col++) {
                if (getTile(row, col).getId() == id) {
                    location[0] = row;
                    location[1] = col;
                    return location;
                }
            }
        }
        return location;
    }

    /**
     * Returns an iterator over the tiles on the board
     *
     * @return new iterator that iterates over tiles in board
     */
    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new TileIterator();
    }

    /**
     * Iterator that iterates over tiles in board
     */
    private class TileIterator implements Iterator<Tile> {

        /**
         * The row index of the next tile
         */
        private int nextRow = 0;

        /**
         * The column index of the next tile
         */
        private int nextCol = 0;

        /**
         * @return if there is a next tile
         */
        @Override
        public boolean hasNext() {
            return nextRow < getNumRows() && nextCol < getNumCols();
        }

        /**
         * @return the next tile
         */
        @Override
        public Tile next() {
            int curRow = nextRow;
            int curCol = nextCol;
            if (curCol < getNumCols() - 1) {
                nextCol++;
            } else {
                nextRow++;
                nextCol = 0;
            }
            return getTile(curRow, curCol);
        }
    }
}
