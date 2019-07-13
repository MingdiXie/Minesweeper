package csc207.gamecentre.slidingtiles;

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
import java.util.Arrays;
import java.util.List;

import csc207.gamecentre.R;
import csc207.gamecentre.general.FileReader;
import csc207.gamecentre.general.GameLaunchCenterActivity;
import csc207.gamecentre.general.User;
import csc207.gamecentre.scoreboard.FinalScore;
import csc207.gamecentre.scoreboard.Scoreboard;
import csc207.gamecentre.scoreboard.ScoreboardActivity;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class STStartingActivity extends AppCompatActivity {

    /**
     * The current user playing
     */
    User currentUser;

    /**
     * The selected complexity.
     */
    private int complexity = 4;

    /**
     * The number of undo
     */
    private int undo;

    /**
     * The board manager.
     */
    private STBoardManager boardManager;

    /**
     * The file reader for this context
     */
    private FileReader fileReader;

    /**
     * Sliding Tiles' scoreboard
     */
    private static Scoreboard slidingTilesScoreboard = new Scoreboard();

    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "ST_save_file_tmp.ser";

    /**
     * The game's name.
     */
    public static final String GAME_NAME = "Sliding Tiles";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boardManager = new STBoardManager(complexity);
        fileReader = new FileReader(this);
        fileReader.saveToFile(TEMP_SAVE_FILENAME, boardManager);

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("Current_User");

        FinalScore finalScore = (FinalScore) intent.getSerializableExtra("Final_Score");
        if (finalScore != null) {
            slidingTilesScoreboard.update(finalScore);
        }

        setContentView(R.layout.activity_st_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        addBackButtonListener();
        populateComplexitySpinner();
        addComplexityListener();
        setUndoSpinner();
        addUndoListener();

        Button scoreboardButton = findViewById(R.id.STScoreboard);
        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(STStartingActivity.this, ScoreboardActivity.class);
                intent.putExtra("Current_Scoreboard", slidingTilesScoreboard);
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
                boardManager = new STBoardManager(complexity);
                boardManager.setNumUndo(undo);
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

                boardManager = (STBoardManager) fileReader.loadFromFile(
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
                Intent intent = new Intent(STStartingActivity.this, GameLaunchCenterActivity.class);
                intent.putExtra("Current_User", currentUser);
                startActivity(intent);
            }
        });
    }

    /**
     *
     */
    @Override
    public void onBackPressed() {
        Button backButton = findViewById(R.id.BackButton);
        backButton.performClick();
    }

    /**
     * Add complexity options to spinner.
     */
    private void populateComplexitySpinner() {
        // https://www.tutorialspoint.com/android/android_spinner_control.htm, 10/22/18
        Spinner spinner = findViewById(R.id.spinner);
        List<String> list = new ArrayList<>();
        list.add("3x3");
        list.add("4x4");
        list.add("5x5");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(1); // default is 4x4
    }

    /**
     * Select the complexity.
     */
    private void addComplexityListener() {
        // https://stackoverflow.com/questions/20151414/how-can-i-use-onitemselected-in-android,
        // 10/22/18
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dimensions = (String) parent.getItemAtPosition(position);
                complexity = Integer.valueOf(dimensions.substring(0, 1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    /**
     * Add undo options to spinner.
     */
    private void setUndoSpinner() {
        // https://www.tutorialspoint.com/android/android_spinner_control.htm
        Spinner spinner = findViewById(R.id.spinner3);
        String[] options = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        List<String> list = new ArrayList<>(Arrays.asList(options));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(2); // default 3
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
                String numUndo = (String) parent.getItemAtPosition(position);
                undo = Integer.valueOf(numUndo);
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
        boardManager = (STBoardManager) fileReader.loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the STGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, STGameActivity.class);
        tmp.putExtra("Current_User", currentUser);
        fileReader.saveToFile(TEMP_SAVE_FILENAME, boardManager);
        startActivity(tmp);
    }
}