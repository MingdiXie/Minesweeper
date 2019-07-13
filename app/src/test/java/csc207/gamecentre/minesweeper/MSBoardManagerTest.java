package csc207.gamecentre.minesweeper;

import org.junit.Test;

import csc207.gamecentre.general.Tile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for MSBoardManager.
 */
public class MSBoardManagerTest {

    /**
     * The board manager for testing.
     */
    private MSBoardManager boardManager;

    /**
     * The board for testing.
     */
    private MSBoard board;

    /**
     * Make a 3x3 MSBoardManager with a bomb at (0,0).
     */
    private void setUpFixedBoard() {
        boardManager = new MSBoardManager(new int[][]{{9, 1, 0}, {1, 1, 0}, {0, 0, 0}});
        board = boardManager.getBoard();
    }

    /**
     * Making a randomized 10x8 board
     */
    private void setUpNormalBoard() {
        boardManager = new MSBoardManager(10, 8);
        board = boardManager.getBoard();
    }

    /**
     * Testing the bomb generator to see if there are 10 bombs present
     */
    @Test
    public void testGenBoard() {
        setUpNormalBoard();
        int[][] B = boardManager.boardGenerator();
        int numBombs = 0;

        for (int i = 0; i < 10; i++) {
            for (int b = 0; b < 8; b++) {
                if (B[i][b] == 9) {
                    numBombs++;
                }
            }
        }
        assertEquals(10, numBombs);

    }

    /**
     * Testing the boardManager initialization
     */
    @Test
    public void testBoardManager() {
        setUpNormalBoard();
        int[][] B = boardManager.boardGenerator();
        int numBombs = 0;

        for (int i = 0; i < 10; i++) {
            for (int b = 0; b < 8; b++) {
                if (B[i][b] == 9) {
                    numBombs++;
                }
            }
        }
        assertEquals(10, numBombs);

    }

    /**
     * Testing isValidTap when there are still moves
     */
    @Test
    public void testIsValidTap() {
        setUpNormalBoard();
        int row = 22 / 10;
        int col = 22 % 8;
        MSTile tile = (MSTile) board.getTile(row, col);
        assertTrue(boardManager.isValidTap(22));
        tile.setFlag();
        assertFalse(boardManager.isValidTap(22));
        tile.setEmpty();
        tile.reveal();
        assertFalse(boardManager.isValidTap(22));
    }

    /**
     * Testing isValidTap when the game is lost
     */
    @Test
    public void testIsValidTap2() {
        setUpFixedBoard();
        MSTile bombTile = (MSTile) board.getTile(0, 0);
        assertTrue(boardManager.isValidTap(0));
        bombTile.reveal();  // lost game
        assertFalse(boardManager.isValidTap(0));
    }

    /**
     * Testing isValidLongTap when there are still moves
     */
    @Test
    public void testIsValidLongTap() {
        setUpNormalBoard();
        int row = 22 / 10;
        int col = 22 % 8;
        MSTile tile = (MSTile) board.getTile(row, col);
        assertTrue(boardManager.isValidLongTap(22));
        tile.reveal();
        assertFalse(boardManager.isValidLongTap(22));
    }

    /**
     * Testing isValidLongTap when the game is lost
     */
    @Test
    public void testIsValidLongTap2() {
        setUpFixedBoard();
        MSTile bombTile = (MSTile) board.getTile(0, 0);
        assertTrue(boardManager.isValidLongTap(0));
        bombTile.reveal();  // lost game
        assertFalse(boardManager.isValidLongTap(0));
    }

    /**
     * Testing puzzleSolved when the game is won
     */
    @Test
    public void testPuzzleSolved() {
        setUpNormalBoard();
        assertFalse(boardManager.puzzleSolved());
        for (Tile t : board) {
            MSTile tile = (MSTile) t;
            if (tile.getId() != 9) {
                tile.reveal();
            }
        }
        assertTrue(boardManager.puzzleSolved());
    }

    /**
     * Testing puzzleLost when the game is lost
     */
    @Test
    public void testPuzzleLost() {
        setUpNormalBoard();
        assertFalse(boardManager.puzzleLost());
        for (Tile t : board) {
            MSTile tile = (MSTile) t;
            if (tile.getId() == 9) {
                tile.reveal();
                break;
            }
        }
        assertTrue(boardManager.puzzleLost());

    }

    /**
     * Testing flagTile to check if there is a flag when it is called
     */
    @Test
    public void testFlagTile() {
        setUpNormalBoard();
        boardManager.flagTile(22);
        MSTile tile = (MSTile) board.getTile(22 / 10, 22 % 8);
        assertTrue(tile.isFlag());
    }

    /**
     * Testing touchMove to see if the tile reveals the background
     */
    @Test
    public void testTouchMove1() {
        setUpNormalBoard();
        boardManager.touchMove(22);
        MSTile tile = (MSTile) board.getTile(22 / 10, 22 % 8);
        assertFalse(tile.isCovered());
    }

    /**
     * Testing touchMove when openTile is called
     */
    @Test
    public void testTouchMove2() {
        setUpFixedBoard();
        boardManager.touchMove(2);
        boolean isOpen = true;
        for (int i = 1; i < 9; i++) {
            MSTile tile1 = (MSTile) board.getTile(i / 3, i % 3);
            if (tile1.isCovered()) {
                isOpen = false;
            }
        }
        assertTrue(isOpen);
    }

    /**
     * Testing the getter for num moves
     */
    @Test
    public void testGetNumMoves() {
        setUpFixedBoard();
        boardManager.touchMove(2);
        boardManager.touchMove(0);
        assertEquals(2, boardManager.getNumMoves());
    }

    /**
     * Testing getNumBombsRemaining when a file is set and then removed
     */
    @Test
    public void testBombCounterPerMove() {
        setUpNormalBoard();
        MSTile tile = (MSTile) board.getTile(22 / 10, 22 % 8);
        tile.setFlag();
        assertEquals(9, boardManager.getNumBombsRemaining());
        tile.setEmpty();
        assertEquals(10, boardManager.getNumBombsRemaining());
    }

    /**
     * Testing undo when closeTile is called
     */
    @Test
    public void testUndo() {
        setUpFixedBoard();
        boardManager.touchMove(2);
//        test for touchMove has been taken care of in testTouchMove2
        boardManager.setNumUndo(1);
        assertEquals(1, boardManager.getNumUndo());
        boardManager.undo();
        boolean isCovered = true;
        for (int i = 0; i < 9; i++) {
            MSTile tile1 = (MSTile) board.getTile(i / 3, i % 3);
            if (!(tile1.isCovered())) {
                isCovered = false;
            }
        }
        assertTrue(isCovered);
    }
}
