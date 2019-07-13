package csc207.gamecentre.memorypuzzle;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import csc207.gamecentre.general.Board;

/**
 * The board for Memory Puzzle
 */
public class MPBoard extends Board implements Serializable {

    /**
     * Creates a Memory Puzzle Board using the tiles
     *
     * @param tiles A list of MPTiles for the board
     */
    public MPBoard(List<MPTile> tiles) {
        super(6, 6);
        Iterator<MPTile> iter = tiles.iterator();
        for (int row = 0; row != this.getNumRows(); row++) {
            for (int col = 0; col != this.getNumCols(); col++) {
                setTile(row, col, iter.next());
            }
        }
    }

    /**
     * Flips the Tile at row row and col col
     *
     * @param row the Tile's row
     * @param col the Tile's column
     */
    void flipTile(int row, int col) {
        MPTile mpTile = (MPTile) getTile(row, col);
        mpTile.flip();

        setChanged();
        notifyObservers();
    }
}
