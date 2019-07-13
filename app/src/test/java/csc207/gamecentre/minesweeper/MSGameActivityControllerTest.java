package csc207.gamecentre.minesweeper;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import csc207.gamecentre.general.FileReader;
import csc207.gamecentre.general.User;
import csc207.gamecentre.general.UserManager;
import csc207.gamecentre.scoreboard.FinalScore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests for the class MSGameActivityController
 */
@RunWith(MockitoJUnitRunner.class)
public class MSGameActivityControllerTest {

    private MSGameActivityController controller;
    @Mock
    private Context mockContext;

    private MSBoardManager boardManager;

    @Mock
    private MSGestureDetectGridView mockGridView;

    @Mock
    private FileReader mockFileReader;

    public void setUpController() {
        boardManager = new MSBoardManager(4, 4);
        User user = new User("Judai", "Yuki", "Haou", "EHero");
        UserManager userManager = UserManager.getUserManager();
        userManager.addNewUser(user);
        controller = new MSGameActivityController(boardManager, user, mockGridView,
                mockContext, "Minesweeper");
        controller.setColumnHeight(5);
        controller.setColumnWidth(5);
        controller.setFileReader(mockFileReader);
    }

    /**
     * Tests that isGameWon works
     */
    @Test
    public void testIsGameWon() {
        setUpController();
        boolean isWon = controller.isGameWon();
        assertFalse(isWon);
    }

    /**
     * tests if isGameLost works
     */
    @Test
    public void testIsGameLost() {
        setUpController();
        boolean isLost = controller.isGameLost();
        assertFalse(isLost);
    }

    /**
     * tests the number of Undos
     */
    @Test
    public void testGetNumUndo() {
        setUpController();
        boardManager.setNumUndo(1);
        assertEquals(1, controller.getNumUndo());
    }

    /**
     * tests bomb counter
     */
    @Test
    public void testBombCounter() {
        setUpController();
        assertEquals(10, controller.bombCounter());
    }

    /**
     * Tests that getFinalScore works
     */
    @Test
    public void testGetFinalScore() {
        setUpController();
        FinalScore finalScore = controller.getFinalScore();
        assertEquals(40000, finalScore.getScore());
    }

    /**
     * Tests that getUpdatedUser works
     */
    @Test
    public void testGetUpdatedUser() {
        setUpController();
        User newUser = controller.getUpdatedUser();
        assertEquals("Haou", newUser.getUsername());
    }


}
