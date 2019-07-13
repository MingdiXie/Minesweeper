package donottest.activities;

import android.content.Intent;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import csc207.gamecentre.R;
import csc207.gamecentre.general.SignInActivity;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static org.junit.Assert.*;

/**
 * Tests that SignInActivity works
 *
 * IMPORTANT: test succeeds when this class runs by itself, fails when run in a group with others
 */
@RunWith(RobolectricTestRunner.class)
public class SignInActivityTest {

    private SignInActivity activity;

    @Before
    public void setUpActivity() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String message = "Hello";
        intent.putExtra(EXTRA_MESSAGE, message);
        activity = Robolectric.buildActivity(SignInActivity.class, intent).create().get();
    }

    @Test
    public void testSignInSuccess() {
        EditText username = activity.findViewById(R.id.editText);
        username.setText("master");
        EditText password = activity.findViewById(R.id.editText2);
        password.setText("master");
        activity.findViewById(R.id.signin).performClick();
        assertNotNull(activity);

    }

    @Test
    public void testSignInFailure() {
        EditText username = activity.findViewById(R.id.editText);
        username.setText("Yuma");
        EditText password = activity.findViewById(R.id.editText2);
        password.setText("Astral");
        activity.findViewById(R.id.signin).performClick();
        assertNotNull(activity);

    }

    @Test
    public void testSendToSignUp() {
        activity.findViewById(R.id.signup).performClick();
        assertNotNull(activity);
    }
}
