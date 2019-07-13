package csc207.gamecentre.memorypuzzle;

import android.content.Context;

import java.util.Observable;
import java.util.Observer;

import csc207.gamecentre.general.CustomAdapter;
import csc207.gamecentre.general.GameActivityController;
import csc207.gamecentre.general.GestureDetectGridView;
import csc207.gamecentre.general.User;

/**
 * The Controller for the Memory Puzzle Game Activity
 */
public class MPGameActivityController extends GameActivityController implements Observer {

    /**
     * The grid view for the game
     */
    private GestureDetectGridView gridView;

    /**
     * Creates a new Memory Puzzle Game Activity Controller
     *
     * @param boardManager the boardManager with the current game model
     * @param currUser     the current User playing
     * @param gridView     the grid view for the game
     * @param context      the current context for the game
     * @param gameName     the name of the game
     */
    MPGameActivityController(MPBoardManager boardManager, User currUser, GestureDetectGridView gridView,
                             Context context, String gameName) {
        super(currUser, boardManager, context, gameName);
        this.gridView = gridView;
    }

    /**
     * Create the tile buttons, set controller as observer
     */
    void setUp() {
        MPBoard board = (MPBoard) boardManager.getBoard();
        tileButtons = buttonMaker.createTileButtons(board);
        boardManager.getBoard().addObserver(this);
        boardManager.startClock();
    }

    /**
     * Set up the background image for each button, and call the adapter to set the view, then autosave.
     */
    public void display() {
        buttonMaker.updateTileButtons(tileButtons, boardManager.getBoard());
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        autoSave();
        ((MPBoardManager) boardManager).resetMove();
    }

    /**
     * Undo the game by 1 move
     */
    void undo() {
        ((MPBoardManager) boardManager).undo();
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }

    /**
     * Pauses the current game time and saves it
     */
    void onPause() {
        boardManager.logClock();
        fileReader.saveToFile(MPStartingActivity.TEMP_SAVE_FILENAME, boardManager);
    }
}

