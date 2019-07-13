package csc207.gamecentre.memorypuzzle;

import android.content.Context;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import csc207.gamecentre.general.ButtonMaker;
import csc207.gamecentre.general.FileReader;
import csc207.gamecentre.general.GestureDetectGridView;
import csc207.gamecentre.general.User;
import csc207.gamecentre.general.UserManager;
import csc207.gamecentre.scoreboard.FinalScore;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

/**
 * Tests the Memory Puzzle GameActivityController
 */
@RunWith(MockitoJUnitRunner.class)
public class MPGameActivityControllerTest {

    /**
     * The mock context
     */
    @Mock
    private Context mockContext;

    /**
     * The mock GestureDetectGridView
     */
    @Mock
    private GestureDetectGridView mockGridView;

    /**
     * A mock button maker
     */
    @Mock
    private ButtonMaker mockButtonMaker;

    /**
     * A mock file reader
     */
    @Mock
    private FileReader mockFileReader;

    /**
     * The MPGameActivityController being tested
     */
    private MPGameActivityController controller;

    /**
     * Sets up the MPGameActivityController before each test
     */
    @Before
    public void setUpController() {
        MPBoardManager boardManager = new MPBoardManager();
        User user = new User("Judai", "Yuki", "Haou", "EHero");
        UserManager userManager = UserManager.getUserManager();
        userManager.addNewUser(user);
        controller = new MPGameActivityController(boardManager, user, mockGridView, mockContext,
                "MemoryPuzzle");
        controller.setButtonMaker(mockButtonMaker);
        controller.setColumnHeight(5);
        controller.setColumnWidth(5);
        controller.setFileReader(mockFileReader);
    }

    /**
     * Tests that the controller successfully ran setup
     */
    @Test
    public void testSetUp() {
        ArrayList<Button> list = new ArrayList<>();
        Mockito.when(mockButtonMaker.createTileButtons(
                controller.getBoardManager().getBoard())).thenReturn(list);
        controller.setUp();
        assertNotNull(controller);
    }

    /**
     * Tests that the controller successfully runs display
     */
    @Test
    public void testDisplay() {
        Mockito.doNothing().when(mockButtonMaker).updateTileButtons(controller.getTileButtons(),
                controller.getBoardManager().getBoard());
        controller.display();
        assertNotNull(controller);
    }

    /**
     * Tests that undo successfully runs
     */
    @Test
    public void testUndo() {
        controller.undo();
        assertNotNull(controller);
    }

    /**
     * Tests that isGameWon works
     */
    @Test
    public void testIsGameWon() {
        boolean isWon = controller.isGameWon();
        assertFalse(isWon);
    }

    /**
     * Tests that getFinalScore works
     */
    @Test
    public void testGetFinalScore() {
        controller.onResume();
        FinalScore finalScore = controller.getFinalScore();
        assertEquals(60000, finalScore.getScore());
    }

    /**
     * Tests that getUpdatedUser works
     */
    @Test
    public void testGetUpdatedUser() {
        User newUser = controller.getUpdatedUser();
        assertEquals("Haou", newUser.getUsername());
    }

    /**
     * Tests that onPause successfully runs
     */
    @Test
    public void testOnPause() {
        controller.onPause();
        assertNotNull(controller);
    }

    /**
     * Tests that update successfully runs
     */
    @Test
    public void testUpdate() {
        Mockito.doNothing().when(mockButtonMaker).updateTileButtons(controller.getTileButtons(),
                controller.getBoardManager().getBoard());
        controller.update(controller.getBoardManager().getBoard(), null);
        assertNotNull(controller);
    }
}
