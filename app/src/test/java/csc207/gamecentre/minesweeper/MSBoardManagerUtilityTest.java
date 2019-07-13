package csc207.gamecentre.minesweeper;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for the class MSBoardManagerUtilityTest
 */
public class MSBoardManagerUtilityTest {


    /**
     * Testing getSurroundingCoor for the top left corner
     */
    @Test
    public void testGetSurroundingCoor1() {
        ArrayList<int[]> testArray = new ArrayList<>();
        int[] right = {0, 1};
        int[] bottom = {1, 0};
        int[] bottomRight = {1, 1};
        testArray.add(right);
        testArray.add(bottom);
        testArray.add(bottomRight);

        assertArrayEquals(testArray.toArray(),
                MSBoardManagerUtility.getSurroundingCoordinates(0, 0, 3, 3).toArray());

    }

    /**
     * Test whether getNumSurroundingBombs returns the right number of bombs.
     */
    @Test
    public void testGetNumSurroundingBombs() {
        int[][] A = new int[4][4];
        A[0][0] = 9;
        A[2][1] = 9;
        A[0][1] = 9;
        A[1][1] = 9;

        assertEquals(2, MSBoardManagerUtility.getNumSurroundingBombs(0, 0, A));
        assertEquals(3, MSBoardManagerUtility.getNumSurroundingBombs(1, 1, A));
    }

    /**
     * Testing whether the getSurroundingCoordinates returns the right coordinates.
     */
    @Test
    public void testGetSurroundingCoordinates2() {
        ArrayList<int[]> coors = MSBoardManagerUtility.getSurroundingCoordinates(0, 0, 3, 3);
        ArrayList<int[]> expected = new ArrayList<>();
        expected.add(new int[]{0, 1});
        expected.add(new int[]{1, 0});
        expected.add(new int[]{1, 1});
        assertEquals(expected.size(), coors.size());
        assertTrue(Arrays.equals(expected.get(0), coors.get(0)));
        assertTrue(Arrays.equals(expected.get(1), coors.get(1)));
        assertTrue(Arrays.equals(expected.get(2), coors.get(2)));
    }

    /**
     * Tests if placeBombs places the right number of bombs in a board.
     */
    @Test
    public void testPlaceBombs() {
        int[][] board = new int[10][8];
        MSBoardManagerUtility.placeBombs(10, board);
        int numBombs = 0;

        for (int i = 0; i < 10; i++) {
            for (int b = 0; b < 8; b++) {
                if (board[i][b] == 9) {
                    numBombs++;
                }
            }
        }
        assertEquals(10, numBombs);
    }

}
