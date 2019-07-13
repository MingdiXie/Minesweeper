package csc207.gamecentre.general;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import csc207.gamecentre.R;
import csc207.gamecentre.memorypuzzle.MPStartingActivity;
import csc207.gamecentre.minesweeper.MSStartingActivity;
import csc207.gamecentre.scoreboard.ScoreboardActivity;
import csc207.gamecentre.slidingtiles.STStartingActivity;

/**
 * Game Launch Center where all games and the User scoreboard can be accessed
 */
public class GameLaunchCenterActivity extends AppCompatActivity {

    /**
     * The current User playing the app
     */
    private User currentUser;

    /**
     * The background music
     */
    MediaPlayer song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_launch_center);
        song = MediaPlayer.create(this, R.raw.music);
        song.start();
        song.setLooping(true);
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("Current_User");

        TextView textView = findViewById(R.id.textView2);
        String message = "Welcome back " + currentUser.getUsername();
        textView.setText(message);

        Button btSTStartGame = findViewById(R.id.gamebutton);
        Button btMPStartGame = findViewById(R.id.gamebutton2);
        Button btMSStartGame = findViewById(R.id.gamebutton3);
        Button btScoreboard = findViewById(R.id.scorebutton);
        Button btSignOut = findViewById(R.id.signoutbutton);

        btSTStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameLaunchCenterActivity.this, STStartingActivity.class);
                intent.putExtra("Current_User", currentUser);
                startActivity(intent);
            }
        });

        btMPStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameLaunchCenterActivity.this, MPStartingActivity.class);
                intent.putExtra("Current_User", currentUser);
                startActivity(intent);
            }


        });

        btMSStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameLaunchCenterActivity.this, MSStartingActivity.class);
                intent.putExtra("Current_User", currentUser);
                startActivity(intent);
            }
        });

        btSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GameLaunchCenterActivity.this, SignInActivity.class);
                startActivity(intent1);
            }
        });

        btScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(GameLaunchCenterActivity.this, ScoreboardActivity.class);
                intent2.putExtra("Current_Scoreboard", currentUser.getScoreboard());
                startActivity(intent2);
            }


        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        song.release();
        finish();
    }
}