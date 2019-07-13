package donottest.activities;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import csc207.gamecentre.R;
import csc207.gamecentre.general.User;
import csc207.gamecentre.memorypuzzle.MPStartingActivity;

import static org.junit.Assert.*;

/**
 * Tests that Memory Puzzle Starting Activity works
 */
@RunWith(RobolectricTestRunner.class)
public class MPStartingActivityTest {

    /**
     * Tests that MPStartingActivity will successfully create
     */
    @Test
    public void testOnCreate() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        User user = new User("Yusei", "Fudo", "Stardust", "Head");
        intent.putExtra("Current_User", user);
        MPStartingActivity activity = Robolectric.buildActivity(MPStartingActivity.class, intent).create().get();
        assertNotNull(activity);
    }

    @Test
    public void testScoreboardButton() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        User user = new User("Yusei", "Fudo", "Stardust", "Head");
        intent.putExtra("Current_User", user);
        MPStartingActivity activity = Robolectric.buildActivity(MPStartingActivity.class, intent).create().get();
        activity.findViewById(R.id.MPScoreboard).performClick();
        assertNotNull(activity);
    }

    @Test
    public void testStartButton() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        User user = new User("Yusei", "Fudo", "Stardust", "Head");
        intent.putExtra("Current_User", user);
        MPStartingActivity activity = Robolectric.buildActivity(MPStartingActivity.class, intent).create().get();
        activity.findViewById(R.id.StartButton).performClick();
        assertNotNull(activity);
    }

    @Test
    public void testLoadButton() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        User user = new User("Yusei", "Fudo", "Stardust", "Head");
        intent.putExtra("Current_User", user);
        MPStartingActivity activity = Robolectric.buildActivity(MPStartingActivity.class, intent).create().get();
        activity.findViewById(R.id.LoadButton).performClick();
        assertNotNull(activity);
    }

    @Test
    public void testBackButton() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        User user = new User("Yusei", "Fudo", "Stardust", "Head");
        intent.putExtra("Current_User", user);
        MPStartingActivity activity = Robolectric.buildActivity(MPStartingActivity.class, intent).create().get();
        activity.findViewById(R.id.BackButton).performClick();
        activity.onBackPressed();
        assertNotNull(activity);
    }

    @Test
    public void testSaveButton() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        User user = new User("Yusei", "Fudo", "Stardust", "Head");
        intent.putExtra("Current_User", user);
        MPStartingActivity activity = Robolectric.buildActivity(MPStartingActivity.class, intent).create().get();
        activity.findViewById(R.id.SaveButton).performClick();
        assertNotNull(activity);
    }
}