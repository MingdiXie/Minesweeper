package donottest.activities;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import csc207.gamecentre.R;
import csc207.gamecentre.general.GameLaunchCenterActivity;
import csc207.gamecentre.general.User;

import static org.junit.Assert.*;

/**
 * Tests that GameLaunchCenterActivity works
 */
@RunWith(RobolectricTestRunner.class)
public class GameLaunchCenterActivityTest {

    private GameLaunchCenterActivity activity;

    @Before
    public void setUpActivity() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        User user = new User("Yusaku", "Fujiki", "Playmaker", "Vrains");
        intent.putExtra("Current_User", user);
        activity = Robolectric.buildActivity(
                GameLaunchCenterActivity.class, intent).create().get();
    }

    @Test
    public void testSlidingTilesButton() {
        activity.findViewById(R.id.gamebutton).performClick();
        assertNotNull(activity);
    }

    @Test
    public void testMemoryPuzzleButton() {
        activity.findViewById(R.id.gamebutton2).performClick();
        assertNotNull(activity);
    }

    @Test
    public void testMinesweeperButton() {
        activity.findViewById(R.id.gamebutton3).performClick();
        assertNotNull(activity);
    }

    @Test
    public void testScoreboardButton() {
        activity.findViewById(R.id.scorebutton).performClick();
        assertNotNull(activity);
    }

    @Test
    public void testSignOutButton() {
        activity.findViewById(R.id.signoutbutton).performClick();
        assertNotNull(activity);
    }
}
