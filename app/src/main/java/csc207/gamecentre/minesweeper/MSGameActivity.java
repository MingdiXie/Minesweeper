package csc207.gamecentre.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import csc207.gamecentre.R;
import csc207.gamecentre.general.FileReader;
import csc207.gamecentre.general.User;
import csc207.gamecentre.scoreboard.FinalScore;

/**
 * The Minesweeper game activity
 */
public class MSGameActivity extends AppCompatActivity implements Observer {

    /**
     * The current user playing
     */
    User currentUser;

    /**
     * The controller for this game view
     */
    MSGameActivityController controller;

    /**
     * The grid view for the game
     */
    MSGestureDetectGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("Current_User");
        String gameName = MSStartingActivity.GAME_NAME;
        int undoStates = (int) intent.getSerializableExtra("undo");
        setContentView(R.layout.activity_ms_main);

        FileReader fileReader = new FileReader(this);
        final MSBoardManager boardManager = (MSBoardManager) fileReader.loadFromFile(MSStartingActivity.TEMP_SAVE_FILENAME);
        boardManager.setNumUndo(undoStates);
        Button Undo = findViewById(R.id.undo);
        Undo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boardManager.undo();
                updateUndoCounter(controller.getNumUndo());
            }
        });

        Button Leave = findViewById(R.id.leave);
        Leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MSGameActivity.this, MSStartingActivity.class);
                intent.putExtra("Current_User", currentUser);
                startActivity(intent);

            }
        });


        TextView bombCount = findViewById(R.id.bombCounter);
        bombCount.setText(String.valueOf(boardManager.getNumBombsRemaining()));
        TextView undoCount = findViewById(R.id.undoCounter);
        undoCount.setText(String.valueOf(undoStates));
        gridView = findViewById(R.id.grid);
        controller = new MSGameActivityController(boardManager, currentUser, gridView, this,
                gameName);

        controller.setUp();
        boardManager.getBoard().addObserver(this);
        MSBoard board = boardManager.getBoard();
        final int numCols = board.getNumCols();
        final int numRows = board.getNumRows();

        gridView.setNumColumns(numCols);
        gridView.setBoardManager(boardManager);
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
        updateBombCounter(String.valueOf(controller.bombCounter()));
        if (controller.isGameLost()) {
            if (controller.getNumUndo() == 0) {
                Intent intent = new Intent(MSGameActivity.this, MSStartingActivity.class);
                intent.putExtra("Current_User", currentUser);
                startActivity(intent);
            }
        }

        if (controller.isGameWon()) {
            FinalScore finalScore = controller.getFinalScore();
            currentUser = controller.getUpdatedUser();

            Intent intent = new Intent(MSGameActivity.this, MSStartingActivity.class);
            intent.putExtra("Current_User", currentUser);
            intent.putExtra("Finished_Game", finalScore);
            startActivity(intent);
        }
    }

    /**
     * Set the TextView undoCounter to undoLeft
     *
     * @param undoLeft the undo counter
     */
    public void updateUndoCounter(int undoLeft) {
        TextView textView = findViewById(R.id.undoCounter);
        textView.setText(String.valueOf(undoLeft));
    }

    /**
     * Set the textView getNumBombsRemaining to numBombs
     *
     * @param numBombs the number of bombs that are remaining
     */
    public void updateBombCounter(String numBombs) {
        TextView textView = findViewById(R.id.bombCounter);
        textView.setText(numBombs);
    }

}
