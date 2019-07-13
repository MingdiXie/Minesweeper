package csc207.gamecentre.minesweeper;

import android.content.Context;
import android.widget.Toast;

/**
 * The Minesweeper game movement controller
 */
class MSMovementController {

    /**
     * The board manager its managing
     */
    private MSBoardManager boardManager = null;

    /**
     * Initializing a MSMovementController
     */
    MSMovementController() {
    }

    /**
     * Return the board manager of the current game
     *
     * @return the board manager
     */
    MSBoardManager getBoardManager() {
        return boardManager;
    }

    /**
     * Set the board manager
     *
     * @param boardManager the board manager it will manage
     */
    void setBoardManager(MSBoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Process a tap movement at a position
     *
     * @param context  the context
     * @param position the position of the tap
     */
    void processTapMovement(Context context, int position) {
        if (boardManager.isValidTap(position)) {
            boardManager.logClock();
            boardManager.touchMove(position);

            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN! Moves: " + Integer.toString(boardManager.getNumMoves())
                                + ", Time: " + boardManager.getFormattedTime() + ", Score: "
                                + Integer.toString(boardManager.getScore()),
                        Toast.LENGTH_LONG).show();
            } else if (boardManager.puzzleLost()) {
                Toast.makeText(context, "YOU LOSE!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Change an empty tile to a flag or vice versa
     *
     * @param context  the context
     * @param position the position of the chosen tile
     */
    void flagTile(Context context, int position) {
        if (boardManager.isValidLongTap(position)) {
            boardManager.flagTile(position);
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}

