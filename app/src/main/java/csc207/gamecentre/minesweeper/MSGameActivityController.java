package csc207.gamecentre.minesweeper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import csc207.gamecentre.general.CustomAdapter;
import csc207.gamecentre.general.DrawableResourceAccessor;
import csc207.gamecentre.general.GameActivityController;
import csc207.gamecentre.general.User;

/**
 * The controller for the Minesweeper Game Activity
 */
public class MSGameActivityController extends GameActivityController implements Observer {

    /**
     * The gridview of the game
     */
    private MSGestureDetectGridView gridView;

    /**
     * The current context
     */
    private Context context;

    /**
     * Creates a new Minesweeper Game Activity Controller
     *
     * @param boardManager the boardManager with the current game model
     * @param currUser     the current User playing
     * @param gridView     the grid view for the game
     * @param context      the current context for the game
     * @param gameName     the name of the game
     */
    MSGameActivityController(MSBoardManager boardManager, User currUser,
                             MSGestureDetectGridView gridView, Context context, String gameName) {
        super(currUser, boardManager, context, gameName);
        this.gridView = gridView;
        this.context = context;
    }

    /**
     * Create the tile buttons, set controller as observer
     */
    void setUp() {
        createTileButtons();
        boardManager.getBoard().addObserver(this);
        boardManager.startClock();
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        autoSave();
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
        fileReader.saveToFile(MSStartingActivity.TEMP_SAVE_FILENAME, boardManager);
    }

    /**
     * Returns if the game is over and lost
     *
     * @return if the game is over and lost
     */
    boolean isGameLost() {
        return ((MSBoardManager) boardManager).puzzleLost();
    }

    /**
     * Get number of undo moves left
     *
     * @return number of undo moves left
     */
    int getNumUndo() {
        return ((MSBoardManager) boardManager).getNumUndo();
    }

    /**
     * Get number of bombs minus flagged tiles
     *
     * @return number of bombs minus flagged tiles
     */
    public int bombCounter() {
        return ((MSBoardManager) boardManager).getNumBombsRemaining();
    }

    /**
     * Creates tile buttons from the board by setting the image resources
     */
    private void createTileButtons() {
        MSBoard board = ((MSBoardManager) boardManager).getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != board.getNumRows(); row++) {
            for (int col = 0; col != board.getNumCols(); col++) {
                Button tmp = new Button(this.context);

                int image = getTileSurface((MSTile) board.getTile(row, col));
                Drawable foreground = ContextCompat.getDrawable(this.context, image);
                tmp.setBackgroundResource(image);
                tmp.setForeground(foreground);
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Updates the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        MSBoard board = ((MSBoardManager) boardManager).getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / board.getNumRows();
            int col = nextPos % board.getNumCols();

            int image = getTileSurface((MSTile) board.getTile(row, col));
            Drawable foreground = ContextCompat.getDrawable(this.context, image);
            b.setBackgroundResource(image);
            b.setForeground(foreground);
            nextPos++;
        }
    }

    /**
     * Gets the background image id of a tile
     *
     * @param tile the tile whose image id is retrieved
     * @return the background image id
     */
    private int getTileSurface(MSTile tile) {
        DrawableResourceAccessor backgrounds = new DrawableResourceAccessor(this.context);
        String image = tile.getSurface();
        return backgrounds.convertDrawable(image);
    }
}
