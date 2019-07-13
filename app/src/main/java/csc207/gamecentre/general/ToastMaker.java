package csc207.gamecentre.general;

import android.content.Context;
import android.widget.Toast;

/**
 * An object that creates toasts in a current context
 *
 * View code, should not be included in code coverage
 */
class ToastMaker {

    /**
     * The current context
     */
    private Context context;

    /**
     * A new Toast Maker for this context
     *
     * @param context the current context
     */
    ToastMaker(Context context) {
        this.context = context;
    }

    /**
     * Displays a message that the player has won and some finished game statistics
     *
     * @param boardManager the board manager of the finished game
     */
    void displayWinText(BoardManager boardManager) {
        Toast.makeText(context, "YOU WIN! Moves: " + Integer.toString(boardManager.getNumMoves())
                        + ", Time: " + boardManager.getFormattedTime() + ", Score: "
                        + Integer.toString(boardManager.getScore()),
                Toast.LENGTH_LONG).show();
    }

    /**
     * Displays that an invalid tap was made
     */
    void displayInvalidTap() {
        Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
    }

}
