package csc207.gamecentre.general;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the class UserManager
 */
public class UserManagerTest {

    /**
     * The UserManager
     */
    private UserManager userManager = UserManager.getUserManager();

    /**
     * Tests if isUsernameTaken works for a taken username
     */
    @Test
    public void TestIsUsernameTakenTrue() {
        userManager.clearUsers();
        User playmaker = new User("Yusaku", "Fujiki", "Playmaker",
                "Vrains");
        userManager.addNewUser(playmaker);

        boolean isTaken1 = userManager.isUsernameTaken("Playmaker");
        assertTrue(isTaken1);
    }

    /**
     * Tests if isUsernameTaken works for a not taken username
     */
    @Test
    public void TestIsUsernameTakenFalse() {
        userManager.clearUsers();
        User playmaker = new User("Yusaku", "Fujiki", "Playmaker",
                "Vrains");
        userManager.addNewUser(playmaker);

        boolean isTaken2 = userManager.isUsernameTaken("Revolver");
        assertFalse(isTaken2);
    }

    /**
     * Tests if signIn works if username and password don't match
     */
    @Test
    public void TestSignInNoMatch() {
        userManager.clearUsers();
        User playmaker = new User("Yusaku", "Fujiki", "Playmaker",
                "Vrains");
        User revolver = new User("Ryouken", "Kougami", "Revolver",
                "Hanoi");
        userManager.addNewUser(playmaker);
        userManager.addNewUser(revolver);
        userManager.updateUser(playmaker);

        User blueAngel = userManager.signIn("Blue Angel", "SolTech");
        assertNotEquals(playmaker, blueAngel);
        assertNotEquals(revolver, blueAngel);
    }

    /**
     * Tests if signIn works if only the password matches
     */
    @Test
    public void TestSignInPasswordMatches() {
        userManager.clearUsers();
        User playmaker = new User("Yusaku", "Fujiki", "Playmaker",
                "Vrains");
        User revolver = new User("Ryouken", "Kougami", "Revolver",
                "Hanoi");
        userManager.addNewUser(playmaker);
        userManager.addNewUser(revolver);
        userManager.updateUser(playmaker);

        User soul = userManager.signIn("Soulburner", "Vrains");
        assertNotEquals(playmaker, soul);
    }

    /**
     * Tests if signIn works if only the username matches
     */
    @Test
    public void TestSignInUsernameMatches() {
        userManager.clearUsers();
        User playmaker = new User("Yusaku", "Fujiki", "Playmaker",
                "Vrains");
        User revolver = new User("Ryouken", "Kougami", "Revolver",
                "Hanoi");
        userManager.addNewUser(playmaker);
        userManager.addNewUser(revolver);
        userManager.updateUser(playmaker);

        User mixed = userManager.signIn("Playmaker", "Hanoi");
        assertNotEquals(playmaker, mixed);
        assertNotEquals(revolver, mixed);
    }

    /**
     * Tests if signIn works for a successful sign in
     */
    @Test
    public void TestSignInSuccessful() {
        userManager.clearUsers();
        User playmaker = new User("Yusaku", "Fujiki", "Playmaker",
                "Vrains");
        User revolver = new User("Ryouken", "Kougami", "Revolver",
                "Hanoi");
        userManager.addNewUser(playmaker);
        userManager.addNewUser(revolver);
        userManager.updateUser(playmaker);

        User success = userManager.signIn("Playmaker", "Vrains");
        assertEquals(playmaker, success);
    }
}
