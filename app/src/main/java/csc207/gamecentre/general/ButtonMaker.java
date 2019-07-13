package csc207.gamecentre.general;

import android.content.Context;
import android.widget.Button;

import java.util.ArrayList;

/**
 * An object that makes and manipulates buttons and their images for a board of a game
 *
 * Model code, should not be included in code coverage
 */
public class ButtonMaker {

    /**
     * The current context
     */
    private Context context;

    /**
     * A generic button maker for the context
     *
     * @param context the current context
     */
    ButtonMaker(Context context) {
        this.context = context;
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param board the board to make buttons for
     * @return an arraylist of buttons corresponding to board
     */
    public ArrayList<Button> createTileButtons(Board board) {
        ArrayList<Button> tileButtons = new ArrayList<>();
        for (int row = 0; row != board.getNumRows(); row++) {
            for (int col = 0; col != board.getNumCols(); col++) {
                Button tileButton = new Button(context);
                int image = getTileBackground(board.getTile(row, col));
                tileButton.setBackgroundResource(image);
                tileButtons.add(tileButton);
            }
        }
        return tileButtons;
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     *
     * @param tileButtons the tile buttons to get updated
     * @param board       the board to update the tile buttons according to
     */
    public void updateTileButtons(ArrayList<Button> tileButtons, Board board) {
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / board.getNumRows();
            int col = nextPos % board.getNumCols();
            int image = getTileBackground(board.getTile(row, col));
            b.setBackgroundResource(image);
            nextPos++;
        }
    }

    /**
     * Gets the background image id of a tile
     *
     * @return the background image id
     */
    private int getTileBackground(Tile tile) {
        DrawableResourceAccessor backgrounds = new DrawableResourceAccessor(context);
        String image = tile.getBackground();
        return backgrounds.convertDrawable(image);
    }
}