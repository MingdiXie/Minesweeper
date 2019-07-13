package csc207.gamecentre.memorypuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import csc207.gamecentre.R;
import csc207.gamecentre.general.FileReader;
import csc207.gamecentre.general.GameLaunchCenterActivity;
import csc207.gamecentre.general.User;
import csc207.gamecentre.scoreboard.FinalScore;
import csc207.gamecentre.scoreboard.Scoreboard;
import csc207.gamecentre.scoreboard.ScoreboardActivity;

/**
 * The Memory Puzzle starting activity
 */
public class MPStartingActivity extends AppCompatActivity {

    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "MP_save_file_tmp.ser";

    /**
     * The game's name.
     */
    public static final String GAME_NAME = "Memory Puzzle";

    /**
     * The current user playing
     */
    User currentUser;

    /**
     * A file reader for this context
     */
    FileReader fileReader;

    /**
     * The board manager.
     */
    private MPBoardManager boardManager;

    /**
     * Memory Puzzle scoreboard
     */
    private static Scoreboard memoryPuzzleScoreboard = new Scoreboard();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp_starting);
        fileReader = new FileReader(this);

        boardManager = new MPBoardManager();
        fileReader.saveToFile(TEMP_SAVE_FILENAME, boardManager);

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("Current_User");

        FinalScore finalScore = (FinalScore) intent.getSerializableExtra("Final_Score");
        if (finalScore != null) {
            memoryPuzzleScoreboard.update(finalScore);
        }

        setContentView(R.layout.activity_mp_starting);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        addBackButtonListener();

        Button scoreboardButton = findViewById(R.id.MPScoreboard);
        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MPStartingActivity.this, ScoreboardActivity.class);
                intent.putExtra("Current_Scoreboard", memoryPuzzleScoreboard);
                startActivity(intent);
            }
        });

        TextView textView = findViewById(R.id.textView1);
        String message = currentUser.getUsername() + " is currently playing";
        textView.setText(message);
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new MPBoardManager();
                switchToGame();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = (MPBoardManager) fileReader.loadFromFile(
                        currentUser.getPrevSavedFiles(GAME_NAME));
                fileReader.saveToFile(TEMP_SAVE_FILENAME, boardManager);
                makeToastLoadedText();
                switchToGame();
            }
        });
    }

    /**
     * Activate the Back Button
     */
    private void addBackButtonListener() {
        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MPStartingActivity.this, GameLaunchCenterActivity.class);
                intent.putExtra("Current_User", currentUser);
                startActivity(intent);
            }
        });
    }

    /**
     * Press the back button
     */
    @Override
    public void onBackPressed() {
        Button backButton = findViewById(R.id.BackButton);
        backButton.performClick();
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileReader.saveToFile(currentUser.getPrevSavedFiles(GAME_NAME), boardManager);
                fileReader.saveToFile(TEMP_SAVE_FILENAME, boardManager);
                makeToastSavedText();
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        boardManager = (MPBoardManager) fileReader.loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the MPGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, MPGameActivity.class);
        tmp.putExtra("Current_User", currentUser);
        tmp.putExtra("Game_Name", GAME_NAME);
        fileReader.saveToFile(TEMP_SAVE_FILENAME, boardManager);
        startActivity(tmp);
    }
}
