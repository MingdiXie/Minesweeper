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
 * Creates a new User account for the app
 */
public class SignUpActivity extends AppCompatActivity {

    /**
     * The User Manager
     */
    UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userManager = UserManager.getUserManager();
        addSignUpButtonListener();
    }

    /**
     * Attempts to sign up a new User with the entered information
     */
    private void addSignUpButtonListener() {
        Button startButton = findViewById(R.id.signUp);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText3 = findViewById(R.id.editText3);
                String firstName = editText3.getText().toString();

                EditText editText4 = findViewById(R.id.editText4);
                String lastName = editText4.getText().toString();

                EditText editText5 = findViewById(R.id.editText5);
                String username = editText5.getText().toString();

                EditText editText6 = findViewById(R.id.editText6);
                String password1 = editText6.getText().toString();

                EditText editText7 = findViewById(R.id.editText7);
                String password2 = editText7.getText().toString();

                String message;
                if (userManager.isUsernameTaken(username)) {
                    message = "Username taken";
                } else if (!password1.equals(password2)) {
                    message = "Passwords do not match";
                } else {
                    User newUser = new User(firstName, lastName, username, password1);
                    userManager.addNewUser(newUser);
                    message = "Successfully created account " + newUser.getFirstName() + " "
                            + newUser.getLastName();
                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                }
                makeToastMessage(message);
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
