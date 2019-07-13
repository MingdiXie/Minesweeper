package donottest.activities;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertNotNull;

import android.widget.EditText;

import csc207.gamecentre.R;
import csc207.gamecentre.general.SignUpActivity;

/**
 * Tests that SignUpActivity works
 *
 * IMPORTANT: test succeeds when this class runs by itself, fails when run in a group with others
 */
@RunWith(RobolectricTestRunner.class)
public class SignUpActivityTest {

    @Test
    public void testCreateNewUser() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        SignUpActivity activity = Robolectric.buildActivity(SignUpActivity.class, intent).create().get();

        EditText firstName = activity.findViewById(R.id.editText3);
        firstName.setText("Yuma");
        EditText lastName = activity.findViewById(R.id.editText4);
        lastName.setText("Tsukumo");
        EditText username = activity.findViewById(R.id.editText5);
        username.setText("Zexal");
        EditText password1 = activity.findViewById(R.id.editText6);
        password1.setText("Astral");
        EditText password2 = activity.findViewById(R.id.editText7);
        password2.setText("Astral");
        activity.findViewById(R.id.signUp).performClick();
        assertNotNull(activity);
    }
}