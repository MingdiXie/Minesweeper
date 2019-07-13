package csc207.gamecentre.minesweeper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * A test for the MSMovementControllerTest class
 */
public class MSMovementControllerTest {

    /**
     * a MSMovementController
     */
    private MSMovementController controller;

    /**
     * a MSBoardManager
     */
    private MSBoardManager boardManager;

    /**
     * setting up the movement controller and the board manager
     */
    @Before
    public void setUp() {
        controller = new MSMovementController();
        boardManager = new MSBoardManager(4, 4);
    }

    /**
     * A test for the setter of boardManager
     */
    @Test
    public void testSetter() {
        setUp();
        controller.setBoardManager(boardManager);
        assertNotNull(controller.getBoardManager());

    }
}
