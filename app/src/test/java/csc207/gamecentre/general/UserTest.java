package csc207.gamecentre.general;

import org.junit.Test;

import csc207.gamecentre.scoreboard.Scoreboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the class User
 */
public class UserTest {

    /**
     * Tests if getFirstName works
     */
    @Test
    public void testGetFirstName() {
        User newUser = new User("Yusaku", "Fujiki", "Playmaker",
                "Vrains");
        String firstName = newUser.getFirstName();
        assertEquals("Yusaku", firstName);
    }

    /**
     * Tests if getLastName works
     */
    @Test
    public void testGetLastName() {
        User newUser = new User("Yusaku", "Fujiki", "Playmaker",
                "Vrains");
        String lastName = newUser.getLastName();
        assertEquals("Fujiki", lastName);
    }

    /**
     * Tests if getUsername works
     */
    @Test
    public void testGetUsername() {
        User newUser = new User("Yusaku", "Fujiki", "Playmaker",
                "Vrains");
        String username = newUser.getUsername();
        assertEquals("Playmaker", username);
    }

    /**
     * Tests if getUsername works
     */
    @Test
    public void testGetPassword() {
        User newUser = new User("Yusaku", "Fujiki", "Playmaker",
                "Vrains");
        String password = newUser.getPassword();
        assertEquals("Vrains", password);
    }

    /**
     * Tests if getPrevSavedFiles works
     */
    @Test
    public void testGetPrevSavedFiles() {
        User newUser = new User("Yusaku", "Fujiki", "Playmaker",
                "Vrains");
        String slidingtiles = newUser.getPrevSavedFiles("Sliding Tiles");
        String memorypuzzle = newUser.getPrevSavedFiles("Memory Puzzle");
        String minesweeper = newUser.getPrevSavedFiles("Minesweeper");

        assertEquals("Playmaker_ST_save_file.ser", slidingtiles);
        assertEquals("Playmaker_MP_save_file.ser", memorypuzzle);
        assertEquals("Playmaker_MS_save_file.ser", minesweeper);
    }

    /**
     * Testing the scoreboard is initialized when User is initialized
     */
    @Test
    public void testScoreboards() {
        User newUser = new User("Yusaku", "Fujiki", "Playmaker",
                "Vrains");
        Scoreboard scoreboard = newUser.getScoreboard();
        assertNotNull(scoreboard);
    }
}