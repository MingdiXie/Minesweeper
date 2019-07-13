package csc207.gamecentre.general;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import csc207.gamecentre.memorypuzzle.MPBoard;
import csc207.gamecentre.memorypuzzle.MPBoardManager;
import csc207.gamecentre.memorypuzzle.MPTile;

import static org.mockito.Mockito.verify;

/**
 * Tests the Movement Controller
 */
@RunWith(MockitoJUnitRunner.class)
public class MovementControllerTest {

    /**
     * The mock context
     */
    @Mock
    private Context mockContext;

    /**
     * The mock ToastMaker
     */
    @Mock
    private ToastMaker mockToastMaker;

    /**
     * Sets up a memory puzzle board that is almost solved
     *
     * @return A memory puzzle board with all tiles unflipped other than the very last
     */
    private MPBoardManager setUpAlmostSolved() {
        List<MPTile> tiles = new ArrayList<>();
        final int numUniqueTiles = 18;
        for (int tileNum = 0; tileNum != numUniqueTiles; tileNum++) {
            tiles.add(new MPTile(tileNum + 1));
            tiles.add(new MPTile(tileNum + 1));
        }
        MPBoardManager boardManager = new MPBoardManager(new MPBoard(tiles));
        for (int i = 0; i < 34; i = i + 2) {
            boardManager.touchMove(i);
            boardManager.touchMove(i + 1);
        }
        boardManager.touchMove(34);
        return boardManager;
    }

    /**
     * Tests that a movement at a position will be processed and display a message
     */
    @Test
    public void testProcessTapMovement() {
        MovementController movementController = new MovementController();
        MPBoardManager boardManager = setUpAlmostSolved();
        movementController.setBoardManager(boardManager);
        movementController.setToastMaker(mockToastMaker);
        Mockito.doNothing().when(mockToastMaker).displayWinText(boardManager);
        Mockito.doNothing().when(mockToastMaker).displayInvalidTap();

        movementController.processTapMovement(mockContext, 0);
        verify(mockToastMaker).displayInvalidTap();

        movementController.processTapMovement(mockContext, 35);
        verify(mockToastMaker).displayWinText(boardManager);
    }
}
