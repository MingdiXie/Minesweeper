package csc207.gamecentre.general;

import java.io.Serializable;

/**
 * A generic board manager for the board of a game
 */
public abstract class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    protected Board currBoard;

    /**
     * Records time elapsed in milliseconds.
     */
    private long timeElapsed = 0;

    /**
     * The last system time recorded in milliseconds.
     */
    private long lastTime;

    /**
     * An empty constructor
     */
    public BoardManager() {
    }

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    protected BoardManager(Board board) {
        this.currBoard = board;
    }

    /**
     * Returns board being manage
     *
     * @return board being managed
     */
    public Board getBoard() {
        return currBoard;
    }

    /**
     * Returns if the tap at position is valid
     *
     * @param position the position of the tap
     * @return if the tap is valid
     */
    public abstract boolean isValidTap(int position);

    /**
     * Execute the move at position
     *
     * @param position The position of the tap
     */
    public abstract void touchMove(int position);

    /**
     * Returns if the puzzle is solved currently
     *
     * @return the board is finished
     */
    public abstract boolean puzzleSolved();

    /**
     * The total moves made up until this point
     *
     * @return the total moves made
     */
    public abstract int getNumMoves();

    /**
     * The final score of the game based off of moves made and time taken
     *
     * @return the final score
     */
    public abstract int getScore();

    /**
     * The time taken since starting the game
     *
     * @return the time taken
     */
    protected long getTileElapsed() {
        return timeElapsed;
    }

    /**
     * Returns the time that has elapsed since this STBoard Manager was created and in use.
     *
     * @return the time that has passed since this boardManager was in use
     */
    public String getFormattedTime() {
        int totalSeconds = (int) timeElapsed / 1000;
        String minutes = String.valueOf(totalSeconds / 60);
        String seconds = String.valueOf(totalSeconds % 60);
        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }
        return minutes + ":" + seconds;
    }

    /**
     * Updates lastTime with the current system time.
     */
    public void startClock() {
        lastTime = System.currentTimeMillis();
    }

    /**
     * Updates the time elapsed and sets up a new clock interval.
     */
    public void logClock() {
        timeElapsed += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
    }
}
