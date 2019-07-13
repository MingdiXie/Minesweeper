package csc207.gamecentre.memorypuzzle;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the Memory Puzzle BoardManager
 */
public class MPBoardManagerTest {

    /**
     * The board manager being tested
     */
    private MPBoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     */
    @Before
    public void setUp() {
        List<MPTile> tiles = new ArrayList<>();
        final int numUniqueTiles = 18;
        for (int tileNum = 0; tileNum != numUniqueTiles; tileNum++) {
            tiles.add(new MPTile(tileNum + 1));
            tiles.add(new MPTile(tileNum + 1));
        }
        MPBoard board = new MPBoard(tiles);
        boardManager = new MPBoardManager(board);
    }

    /**
     * Test if isValidTap works on an unflipped tile
     */
    @Test
    public void testIsValidTap() {
        boolean validTap = boardManager.isValidTap(0);
        assertTrue(validTap);
    }

    /**
     * Test if isValidTap works on a flipped tile
     */
    @Test
    public void testIsValidTapFalse() {
        boardManager.touchMove(0);
        boardManager.resetMove();
        boolean validTap = boardManager.isValidTap(0);
        assertFalse(validTap);
    }

    /**
     * Tests if touchMove works
     */
    @Test
    public void testTouchMoveNoMatch() {
        // test after touching two unmatching that they switch back over
        boardManager.touchMove(2);
        boardManager.resetMove();
        boardManager.touchMove(4);
        boardManager.resetMove();
        boolean validTap = boardManager.isValidTap(2);
        assertTrue(validTap);
    }

    /**
     * Tests if touchMove works
     */
    @Test
    public void testTouchMoveMatch() {
        // test after touching two matching that they don't switch back over
        boardManager.touchMove(2);
        boardManager.resetMove();
        boardManager.touchMove(3);
        boardManager.resetMove();
        boolean validTap = boardManager.isValidTap(2);
        assertFalse(validTap);
    }

    /**
     * Tests resetMove works
     */
    @Test
    public void testResetMove() {
        boardManager.currMove[0][0] = 0;
        boardManager.currMove[0][1] = 0;
        boardManager.currMove[1][0] = 1;
        boardManager.currMove[1][1] = 1;
        boardManager.resetMove();
        MPTile one = (MPTile) boardManager.getBoard().getTile(0, 0);
        MPTile two = (MPTile) boardManager.getBoard().getTile(1, 1);
        assertTrue(one.isFlippedUp());
        assertTrue(two.isFlippedUp());
    }

    /**
     * Tests if puzzleSolved works on  new unflipped board
     */
    @Test
    public void testPuzzleSolvedAllUnflipped() {
        boolean isSolved = boardManager.puzzleSolved();
        assertFalse(isSolved);
    }

    /**
     * Tests if puzzleSolved works on a board half unflipped
     */
    @Test
    public void testPuzzleSolvedHalfUnflipped() {
        for (int i = 0; i < 18; i++) {
            boardManager.touchMove(i);
        }
        boolean isSolved = boardManager.puzzleSolved();
        assertFalse(isSolved);
    }

    /**
     * Tests if puzzleSolved works on an entirely unflipped board
     */
    @Test
    public void testPuzzleSolvedAllFlipped() {
        for (int i = 0; i < 36; i++) {
            boardManager.touchMove(i);
        }
        boolean isSolved = boardManager.puzzleSolved();
        assertTrue(isSolved);
    }

    /**
     * Tests if getNumMoves works
     */
    @Test
    public void testGetNumMoves() {
        boardManager.touchMove(0);
        boardManager.resetMove();
        boardManager.touchMove(1);
        boardManager.resetMove();
        boardManager.touchMove(2);
        boardManager.resetMove();
        boardManager.touchMove(3);
        boardManager.resetMove();
        int numMoves = boardManager.getNumMoves();
        assertEquals(4, numMoves);
    }

    /**
     * Tests getScore works
     */
    @Test
    public void testGetScore() {
        boardManager = new MPBoardManager();
        boardManager.touchMove(0);
        boardManager.touchMove(1);
        int score = boardManager.getScore();
        assertEquals(59996, score);
    }

    /**
     * Tests undo works mid move
     */
    @Test
    public void testUndoMidMove() {
        boardManager.touchMove(0);
        boardManager.undo();
        MPTile tile = (MPTile) boardManager.getBoard().getTile(0, 0);
        boolean isFlipped = tile.isFlippedUp();
        assertFalse(isFlipped);
    }

    /**
     * Tests undo works on a matched pair
     */
    @Test
    public void testUndoMatchedPair() {
        boardManager.touchMove(0);
        boardManager.touchMove(1);
        boardManager.undo();
        MPTile tile2 = (MPTile) boardManager.getBoard().getTile(0, 1);
        boolean isFlipped2 = tile2.isFlippedUp();
        assertFalse(isFlipped2);
    }

    /**
     * Tests undo works on a not matched pair
     */
    @Test
    public void testUndoNotMatchedPair() {
        boardManager.touchMove(0);
        boardManager.touchMove(2);
        ((MPTile) boardManager.getBoard().getTile(0, 0)).flip();
        ((MPTile) boardManager.getBoard().getTile(0, 2)).flip();
        boardManager.undo();
        MPTile tile1 = (MPTile) boardManager.getBoard().getTile(0, 0);
        boolean isFlipped1 = tile1.isFlippedUp();
        assertTrue(isFlipped1);

        MPTile tile2 = (MPTile) boardManager.getBoard().getTile(0, 2);
        boolean isFlipped2 = tile2.isFlippedUp();
        assertFalse(isFlipped2);
    }
}