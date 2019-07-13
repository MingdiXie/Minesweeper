package donottest.activities;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import csc207.gamecentre.R;
import csc207.gamecentre.general.User;
import csc207.gamecentre.scoreboard.FinalScore;
import csc207.gamecentre.slidingtiles.STStartingActivity;

import static org.junit.Assert.*;

/**
 * Tests that Sliding Tiles Starting Activity works
 *
 * IMPORTANT: Tests will succeed when run individually, may fail when run as a class, and may fail
 * when run in a group with other tests
 */
@RunWith(RobolectricTestRunner.class)
public class STStartingActivityTest {

    /**
     * Tests that STStartingActivity will successfully create
     */
    @Test
    public void testOnCreate() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        User user = new User("Yusei", "Fudo", "Stardust", "Head");
        FinalScore finalScore = new FinalScore("Sliding Tiles", 300);

        intent.putExtra("Current_User", user);
        intent.putExtra("Final_Score", finalScore);

        STStartingActivity activity = Robolectric.buildActivity(STStartingActivity.class, intent).create().get();
        assertNotNull(activity);
    }

    @Test
    public void testScoreboardButton() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        User user = new User("Yusei", "Fudo", "Stardust", "Head");
        intent.putExtra("Current_User", user);
        STStartingActivity activity = Robolectric.buildActivity(STStartingActivity.class, intent).create().get();
        activity.findViewById(R.id.STScoreboard).performClick();
        assertNotNull(activity);
    }

    @Test
    public void testStartButton() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        User user = new User("Yusei", "Fudo", "Stardust", "Head");
        intent.putExtra("Current_User", user);
        STStartingActivity activity = Robolectric.buildActivity(STStartingActivity.class, intent).create().get();
        activity.findViewById(R.id.StartButton).performClick();
        assertNotNull(activity);
    }

    @Test
    public void testLoadButton() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        User user = new User("Yusei", "Fudo", "Stardust", "Head");
        intent.putExtra("Current_User", user);
        STStartingActivity activity = Robolectric.buildActivity(STStartingActivity.class, intent).create().get();
        activity.findViewById(R.id.LoadButton).performClick();
        assertNotNull(activity);
    }

    @Test
    public void testBackButton() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        User user = new User("Yusei", "Fudo", "Stardust", "Head");
        intent.putExtra("Current_User", user);
        STStartingActivity activity = Robolectric.buildActivity(STStartingActivity.class, intent).create().get();
        activity.findViewById(R.id.BackButton).performClick();
        activity.onBackPressed();
        assertNotNull(activity);
    }

    @Test
    public void testSaveButton() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        User user = new User("Yusei", "Fudo", "Stardust", "Head");
        intent.putExtra("Current_User", user);
        STStartingActivity activity = Robolectric.buildActivity(STStartingActivity.class, intent).create().get();
        activity.findViewById(R.id.SaveButton).performClick();
        assertNotNull(activity);
    }
}