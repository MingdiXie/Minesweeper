package csc207.gamecentre.slidingtiles;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for STBoardManager
 */
@RunWith(JUnit4.class)
public class STBoardManagerTest {

    /**
     * The board manager for testing.
     */
    private STBoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<STTile> makeTiles(int n) {
        List<STTile> tiles = new ArrayList<>();
        final int numTiles = n * n;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new STTile(tileNum + 1, tileNum));
        }
        return tiles;
    }

    /**
     * Make a solved STBoard.
     */
    private void setUpCorrect(int n) {
        List<STTile> tiles = makeTiles(n);
        STBoard board = new STBoard(tiles, n);
        boardManager = new STBoardManager(board);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect(4);
        assertTrue(boardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertFalse(boardManager.puzzleSolved());
    }

    /**
     * Tests that a shuffled board is unsolved
     *
     * Note: random shuffle means its very unlikely but the generated board might already be solved
     */
    @Test
    public void testIsSolvedShuffled() {
        boardManager = new STBoardManager(3);
        assertFalse(boardManager.puzzleSolved());

        boardManager = new STBoardManager(4);
        assertFalse(boardManager.puzzleSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect(4);
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect(4);
        assertEquals(15, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        boardManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(16, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, boardManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidTap works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect(4);
        assertTrue(boardManager.isValidTap(11));
        assertTrue(boardManager.isValidTap(14));
        assertFalse(boardManager.isValidTap(10));
    }

    /**
     * Test whether undo works
     */
    @Test
    public void testUndoOneMove() {
        setUpCorrect(5);
        boardManager.setNumUndo(1);
        int originalTile = boardManager.getBoard().getTile(4, 3).getId();
        boardManager.undo(1);
        int checkTile = boardManager.getBoard().getTile(4, 3).getId();
        assertEquals(originalTile, checkTile);

        boardManager.touchMove(23);
        checkTile = boardManager.getBoard().getTile(4, 3).getId();
        assertNotEquals(originalTile, checkTile);

        boardManager.undo(1);
        checkTile = boardManager.getBoard().getTile(4, 3).getId();
        assertEquals(originalTile, checkTile);
    }

    /**
     * Test for GetScore
     */
    @Test
    public void testGetScore() {
        setUpCorrect(4);
        int finalScore = boardManager.getScore();
        assertEquals(40000, finalScore);
    }

    /**
     * A test for the timing in sliding tiles
     */
    @Test
    public void testTimeMethods() {
        setUpCorrect(4);
        boardManager.startClock();
        boardManager.logClock();
        String time = boardManager.getFormattedTime();
        assertNotNull(time);
    }
}

