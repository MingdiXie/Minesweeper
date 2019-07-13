package csc207.gamecentre.minesweeper;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for MSBoard.
 */
public class MSBoardTest {


    /**
     * The board for testing.
     */
    private MSBoard board;

    /**
     * Make a 10x8 MSBoardManager.
     */
    @Before
    public void setUp() {
        MSBoardManager boardManager = new MSBoardManager(10, 8);
        board = boardManager.getBoard();
    }

    /**
     * Test whether the tiles are correctly placed and retrieved.
     */
    @Test
    public void testBoardConstructor() {
        MSTile tile1 = new MSTile(0);
        MSTile tile2 = new MSTile(1);
        MSTile tile3 = new MSTile(9);

        ArrayList<MSTile> tiles = new ArrayList<>();
        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        MSBoard testBoard = new MSBoard(tiles, 1, 3);

        assertEquals(tile1, testBoard.getTile(0, 0));
        assertEquals(tile2, testBoard.getTile(0, 1));
        assertEquals(tile3, testBoard.getTile(0, 2));
    }

    /**
     * Test whether the board has the specified dimensions.
     */
    @Test
    public void testDimensions() {
        assertEquals(board.getNumCols(), 8);
        assertEquals(board.getNumRows(), 10);
        assertEquals(board.numTiles(), 80);
    }

    /**
     * Test whether the board properly reveals tiles.
     */
    @Test
    public void testReveal() {
        MSTile tile = (MSTile) board.getTile(2, 2);
        assertTrue(tile.isCovered());
        board.reveal(2, 2);
        assertFalse(tile.isCovered());
        assertEquals(tile.getSurface(), tile.getBackground());
    }

    /**
     * Test whether the board properly covers tiles.
     */
    @Test
    public void testCover() {
        MSTile tile = (MSTile) board.getTile(2, 2);
        board.reveal(2, 2);
        assertFalse(tile.isCovered());
        board.cover(2, 2);
        assertTrue(tile.isCovered());
    }

    /**
     * Test whether the board correctly places and removes flags.
     */
    @Test
    public void testFlag() {
        MSTile tile = (MSTile) board.getTile(4, 4);
        board.toggleFlag(4, 4);
        assertEquals("flag", tile.getSurface());
        board.toggleFlag(4, 4);
        assertEquals("empty", tile.getSurface());
    }
}
