package csc207.gamecentre.general;

import android.content.Context;

/**
 * A controller for the movements on the board
 */
public class MovementController {

    /**
     * The board manager it's managing
     */
    protected BoardManager boardManager = null;

    /**
     * The ToastMaker for displaying text
     */
    private ToastMaker toastMaker;

    /**
     * An empty constructor
     */
    MovementController() {
    }

    /**
     * Process a tap movement at a position
     *
     * @param context  the context
     * @param position the position of the tap
     */
    void processTapMovement(Context context, int position) {
        if (toastMaker == null) {
            toastMaker = new ToastMaker(context);
        }
        if (boardManager.isValidTap(position)) {
            boardManager.logClock();
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                toastMaker.displayWinText(boardManager);
            }
        } else {
            toastMaker.displayInvalidTap();
        }
    }

    /**
     * Set the board manager
     *
     * @param boardManager the board manager it will manage
     */
    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Sets the toastMaker
     *
     * @param toastMaker the toastMaker to set
     */
    void setToastMaker(ToastMaker toastMaker) {
        this.toastMaker = toastMaker;
    }
}
