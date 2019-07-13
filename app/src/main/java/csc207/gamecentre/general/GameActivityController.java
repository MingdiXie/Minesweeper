package csc207.gamecentre.general;

import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;

import csc207.gamecentre.scoreboard.FinalScore;
import csc207.gamecentre.scoreboard.Scoreboard;

/**
 * The generic Game Activity Controller
 */
public class GameActivityController {

    /**
     * The column width of the game
     */
    protected int columnWidth;

    /**
     * The column height of the game
     */
    protected int columnHeight;

    /**
     * The current user playing
     */
    private User currUser;

    /**
     * The boardmanager of the current game
     */
    protected BoardManager boardManager;

    /**
     * The buttons to display.
     */
    protected ArrayList<Button> tileButtons;

    /**
     * A button maker for the buttons
     */
    protected ButtonMaker buttonMaker;

    /**
     * The file reader for this context
     */
    protected FileReader fileReader;

    /**
     * The name of the game
     */
    private String gameName;

    /**
     * A generic GameActivityController
     *
     * @param currUser     the current user playing
     * @param boardManager the boardmanager of the game
     * @param context      the current context
     * @param gameName     the name of the game
     */
    public GameActivityController(User currUser, BoardManager boardManager, Context context,
                                  String gameName) {
        this.currUser = currUser;
        this.boardManager = boardManager;
        this.gameName = gameName;
        this.fileReader = new FileReader(context);
        this.buttonMaker = new ButtonMaker(context);
    }

    /**
     * A getter for the tile buttons
     *
     * @return the tileButtons
     */
    public ArrayList<Button> getTileButtons() {
        return tileButtons;
    }

    /**
     * A setter for the file reader
     *
     * @param fileReader the file reader to set
     */
    public void setFileReader(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    /**
     * A setter for the button maker
     *
     * @param buttonMaker the button maker to set
     */
    public void setButtonMaker(ButtonMaker buttonMaker) {
        this.buttonMaker = buttonMaker;
    }

    /**
     * Updates the current user's scoreboard and then return the user
     *
     * @return the updated user
     */
    public User getUpdatedUser() {
        Scoreboard userScoreboard = currUser.getScoreboard();
        FinalScore finalScoreUser = new FinalScore(gameName, boardManager.getScore());
        userScoreboard.update(finalScoreUser);

        UserManager userManager = UserManager.getUserManager();
        userManager.updateUser(currUser);
        return currUser;
    }

    /**
     * A getter for the board manager
     *
     * @return the board manager
     */
    public BoardManager getBoardManager() {
        return boardManager;
    }

    /**
     * A setter for the column width
     *
     * @param columnWidth the column width to set
     */
    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    /**
     * A setter for the column height
     *
     * @param columnHeight the column height to set
     */
    public void setColumnHeight(int columnHeight) {
        this.columnHeight = columnHeight;
    }

    /**
     * Get the Final Score of the game
     *
     * @return the Finished Game of the game
     */
    public FinalScore getFinalScore() {
        return new FinalScore(currUser.getUsername(), boardManager.getScore());
    }

    /**
     * Returns if the game is over and won
     *
     * @return if the game is over and won
     */
    public boolean isGameWon() {
        return boardManager.puzzleSolved();
    }

    /**
     * Restarts the game clock
     */
    public void onResume() {
        boardManager.startClock();
    }

    /**
     * Autosaves the current game state to the current' user's save file
     */
    protected void autoSave() {
        fileReader.saveToFile(currUser.getPrevSavedFiles(gameName), boardManager);
    }
}
