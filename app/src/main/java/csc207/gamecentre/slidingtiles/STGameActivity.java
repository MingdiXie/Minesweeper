package csc207.gamecentre.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import csc207.gamecentre.R;
import csc207.gamecentre.general.FileReader;
import csc207.gamecentre.general.GestureDetectGridView;
import csc207.gamecentre.general.User;
import csc207.gamecentre.scoreboard.FinalScore;

/**
 * The Sliding Tiles game activity that controls the view.
 */
public class STGameActivity extends AppCompatActivity implements Observer {

    /**
     * The current user playing
     */
    User currentUser;

    /**
     * The number of states to go back on Undo
     */
    private int undoStates;

    /**
     * The controller for the view of the Sliding Tiles game
     */
    private STGameActivityController controller;

    /**
     * The gridView of the game
     */
    GestureDetectGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("Current_User");
        String gameName = STStartingActivity.GAME_NAME;

        setContentView(R.layout.activity_st_main);

        // Set up boardManager
        FileReader fileReader = new FileReader(this);
        STBoardManager boardManager = (STBoardManager) fileReader.loadFromFile(STStartingActivity.TEMP_SAVE_FILENAME);

        // Set up undo button
        populateUndoSpinner();
        addUndoSpinnerListener();
        Button Undo = findViewById(R.id.undo);
        Undo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.undo(undoStates);
            }
        });

        // Set up leave button
        Button Leave = findViewById(R.id.leave);
        Leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(STGameActivity.this, STStartingActivity.class);
                intent.putExtra("Current_User", currentUser);
                startActivity(intent);

            }
        });

        // Set up grid
        gridView = findViewById(R.id.grid);
        controller = new STGameActivityController(boardManager, currentUser, gridView, this,
                gameName);
        controller.setUp();
        boardManager.getBoard().addObserver(this);
        STBoard board = boardManager.getBoard();
        final int numCols = board.getNumCols();
        final int numRows = board.getNumRows();

        gridView.setNumColumns(board.getNumCols());
        gridView.setBoardManager(boardManager);

        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        int columnWidth = displayWidth / numCols;
                        int columnHeight = displayHeight / numRows;

                        controller.setColumnWidth(columnWidth);
                        controller.setColumnHeight(columnHeight);

                        controller.display();
                    }
                });
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        controller.onPause();
    }

    /**
     * On resume of the game
     */
    @Override
    protected void onResume() {
        super.onResume();
        controller.onResume();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (controller.isGameWon()) {
            FinalScore finalScore = controller.getFinalScore();
            currentUser = controller.getUpdatedUser();

            Intent intent = new Intent(STGameActivity.this, STStartingActivity.class);
            intent.putExtra("Current_User", currentUser);
            intent.putExtra("Final_Score", finalScore);
            startActivity(intent);
        }
    }

    /**
     * Populates the undo spinner
     */
    private void populateUndoSpinner() {
        // https://www.tutorialspoint.com/android/android_spinner_control.htm, 10/22/18
        Spinner spinner = findViewById(R.id.spinner2);
        String[] options = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        List<String> list = new ArrayList<>(Arrays.asList(options));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(2);
    }

    /**
     * Select the number of states to undo
     */
    private void addUndoSpinnerListener() {
        // https://stackoverflow.com/questions/20151414/how-can-i-use-onitemselected-in-android,
        // 10/22/18
        Spinner spinner = findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedUndo = (String) parent.getItemAtPosition(position);
                undoStates = Integer.valueOf(selectedUndo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
