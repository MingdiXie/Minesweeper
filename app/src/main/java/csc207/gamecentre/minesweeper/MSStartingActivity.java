package csc207.gamecentre.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import csc207.gamecentre.R;
import csc207.gamecentre.general.FileReader;
import csc207.gamecentre.general.GameLaunchCenterActivity;
import csc207.gamecentre.general.User;
import csc207.gamecentre.scoreboard.FinalScore;
import csc207.gamecentre.scoreboard.Scoreboard;
import csc207.gamecentre.scoreboard.ScoreboardActivity;

/**
 * The Minesweeper starting activity screen view
 */
public class MSStartingActivity extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static String SAVE_FILENAME;

    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "MS_save_file_tmp.ser";

    /**
     * The game's name.
     */
    public static final String GAME_NAME = "Minesweeper";

    /**
     * The current user playing
     */
    User currentUser;

    /**
     * The number of undo
     */
    private int undo;

    /**
     * The board manager.
     */
    private MSBoardManager boardManager;

    /**
     * Minesweeper scoreboard
     */
    private static Scoreboard minesweeperScoreboard = new Scoreboard();

    /**
     * A file reader for this context
     */
    private FileReader fileReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new MSBoardManager(8, 8);
        fileReader = new FileReader(this);
        fileReader.saveToFile(TEMP_SAVE_FILENAME, boardManager);

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("Current_User");
        SAVE_FILENAME = currentUser.getPrevSavedFiles(GAME_NAME);
        FinalScore finalScore = (FinalScore) intent.getSerializableExtra("Finished_Game");
        if (finalScore != null) {
            minesweeperScoreboard.update(finalScore);
        }

        setContentView(R.layout.activity_ms_starting);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        addBackButtonListener();
        setUndoSpinner();
        addUndoListener();

        Button scoreboardButton = findViewById(R.id.STScoreboard);
        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MSStartingActivity.this, ScoreboardActivity.class);
                intent.putExtra("Current_Scoreboard", minesweeperScoreboard);
                startActivity(intent);
            }
        });

        TextView textView = findViewById(R.id.textView3);
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
                boardManager = new MSBoardManager(8, 8);
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
                boardManager = (MSBoardManager)
                        fileReader.loadFromFile(currentUser.getPrevSavedFiles(GAME_NAME));
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
                Intent intent = new Intent(MSStartingActivity.this, GameLaunchCenterActivity.class);
                intent.putExtra("Current_User", currentUser);
                startActivity(intent);
            }
        });
    }

    /**
     * Perform back press
     */
    @Override
    public void onBackPressed() {
        Button backButton = findViewById(R.id.BackButton);
        backButton.performClick();
    }

    /**
     * Add undo options to spinner.
     */
    private void setUndoSpinner() {
        // https://www.tutorialspoint.com/android/android_spinner_control.htm
        Spinner spinner = findViewById(R.id.spinner3);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add(String.valueOf(i));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0); // default 1
    }

    /**
     * Select number of undo.
     */
    private void addUndoListener() {
        // https://stackoverflow.com/questions/20151414/how-can-i-use-onitemselected-in-android,
        // 10/22/18
        Spinner spinner = findViewById(R.id.spinner3);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                undo = Integer.valueOf((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
        boardManager = (MSBoardManager) fileReader.loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the MSGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, MSGameActivity.class);
        tmp.putExtra("Current_User", currentUser);
        tmp.putExtra("Game_Name", GAME_NAME);
        tmp.putExtra("undo", undo);
        fileReader.saveToFile(TEMP_SAVE_FILENAME, boardManager);
        startActivity(tmp);
    }
}
