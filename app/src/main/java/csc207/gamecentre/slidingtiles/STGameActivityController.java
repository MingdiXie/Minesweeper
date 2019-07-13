package csc207.gamecentre.slidingtiles;

import android.content.Context;

import java.util.Observable;
import java.util.Observer;

import csc207.gamecentre.general.CustomAdapter;
import csc207.gamecentre.general.GameActivityController;
import csc207.gamecentre.general.GestureDetectGridView;
import csc207.gamecentre.general.User;

/**
 * The Controller for the Sliding Tiles Game Activity
 */
public class STGameActivityController extends GameActivityController implements Observer {

    /**
     * The grid view for the game
     */
    private GestureDetectGridView gridView;

    /**
     * Creates a new Sliding Tiles Game Activity Controller
     *
     * @param boardManager the boardManager with the current game model
     * @param currUser     the current User playing
     * @param gridView     the grid view for the game
     * @param context      the current context for the game
     * @param gameName     the name of the game
     */
    STGameActivityController(STBoardManager boardManager, User currUser, GestureDetectGridView gridView,
                             Context context, String gameName) {
        super(currUser, boardManager, context, gameName);
        this.gridView = gridView;
    }

    /**
     * Set up the Sliding Tiles game, create the tile buttons, set up the observer
     */
    void setUp() {
        STBoard board = (STBoard) boardManager.getBoard();
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
    }

    /**
     * Undo the game by undoStates moves
     *
     * @param undoStates the number of moves to undo
     */
    void undo(int undoStates) {
        ((STBoardManager) boardManager).undo(undoStates);
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
        fileReader.saveToFile(STStartingActivity.TEMP_SAVE_FILENAME, boardManager);
    }
}
