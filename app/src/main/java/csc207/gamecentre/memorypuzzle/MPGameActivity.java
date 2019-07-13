package csc207.gamecentre.memorypuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.util.Observable;
import java.util.Observer;

import csc207.gamecentre.R;
import csc207.gamecentre.general.FileReader;
import csc207.gamecentre.general.GestureDetectGridView;
import csc207.gamecentre.general.User;
import csc207.gamecentre.scoreboard.FinalScore;

/**
 * The Memory Puzzle game activity view
 */
public class MPGameActivity extends AppCompatActivity implements Observer {

    /**
     * The current user playing
     */
    User currentUser;

    /**
     * The controller for this game view
     */
    MPGameActivityController controller;


    /**
     * The grid view for the game
     */
    GestureDetectGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("Current_User");
        String gameName = MPStartingActivity.GAME_NAME;
        setContentView(R.layout.activity_mp_game);

        // Set up board manager
        FileReader fileReader = new FileReader(this);
        MPBoardManager boardManager = (MPBoardManager) fileReader.loadFromFile(MPStartingActivity.TEMP_SAVE_FILENAME);

        // Set up undo button
        Button Undo = findViewById(R.id.undo);
        Undo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.undo();
            }
        });

        // Set up leave button
        Button Leave = findViewById(R.id.leave);
        Leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MPGameActivity.this, MPStartingActivity.class);
                intent.putExtra("Current_User", currentUser);
                startActivity(intent);

            }
        });

        // Set up grid
        gridView = findViewById(R.id.grid);
        controller = new MPGameActivityController(boardManager, currentUser, gridView, this,
                gameName);
        controller.setUp();
        boardManager.getBoard().addObserver(this);

        MPBoard board = boardManager.getBoard();
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

            Intent intent = new Intent(MPGameActivity.this, MPStartingActivity.class);
            intent.putExtra("Current_User", currentUser);
            intent.putExtra("Final_Score", finalScore);
            startActivity(intent);
        }
    }
}
