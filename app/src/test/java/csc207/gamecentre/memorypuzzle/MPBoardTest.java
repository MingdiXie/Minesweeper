package csc207.gamecentre.memorypuzzle;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the Memory Puzzle Board class
 */
public class MPBoardTest {

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<MPTile> makeTiles() {
        List<MPTile> tiles = new ArrayList<>();
        final int numUniqueTiles = 18;
        for (int tileNum = 0; tileNum < numUniqueTiles; tileNum++) {
            tiles.add(new MPTile(tileNum + 1));
            tiles.add(new MPTile(tileNum + 1));
        }
        return tiles;
    }

    /**
     * Tests if flipTile works
     */
    @Test
    public void testFlipTile() {
        List<MPTile> tiles = makeTiles();
        MPBoard testBoard = new MPBoard(tiles);

        boolean isFlipped = ((MPTile) testBoard.getTile(1, 1)).isFlippedUp();
        assertFalse(isFlipped);

        testBoard.flipTile(1, 1);
        testBoard.flipTile(2, 2);
        testBoard.flipTile(1, 1);

        boolean isFlipped1 = ((MPTile) testBoard.getTile(1, 1)).isFlippedUp();
        assertFalse(isFlipped1);

        boolean isFlipped2 = ((MPTile) testBoard.getTile(2, 2)).isFlippedUp();
        assertTrue(isFlipped2);
    }
}