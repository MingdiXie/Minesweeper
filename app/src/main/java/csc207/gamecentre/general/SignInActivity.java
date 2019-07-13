package csc207.gamecentre.general;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import csc207.gamecentre.R;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * The starting screen to sign in
 */
public class SignInActivity extends AppCompatActivity {

    /**
     * The user manager
     */
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        if (message != null) {
            makeToastMessage(message);
        }

        addSignInButtonListener();
        addSignUpButtonListener();

        // For testing purposes
        User master = new User("for", "testing", "master", "master");
        userManager = UserManager.getUserManager();
        userManager.addNewUser(master);
    }

    /**
     * Attempt to Sign In with inputted username and password
     */
    private void addSignInButtonListener() {
        Button startButton = findViewById(R.id.signin);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText1 = findViewById(R.id.editText);
                String username = editText1.getText().toString();

                EditText editText2 = findViewById(R.id.editText2);
                String password = editText2.getText().toString();

                User currUser = userManager.signIn(username, password);
                if (currUser != null) {
                    Intent intent = new Intent(SignInActivity.this, GameLaunchCenterActivity.class);
                    intent.putExtra("Current_User", currUser);
                    startActivity(intent);

                } else {
                    String message = "Failure to Log In";
                    makeToastMessage(message);
                }
            }
        });
    }

    /**
     * Sends app to the sign up screen
     */
    void addSignUpButtonListener() {
        Button signupButton = findViewById(R.id.signup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Display a message
     *
     * @param message the message to display
     */
    private void makeToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
