package csc207.gamecentre.minesweeper;

import java.io.Serializable;

import csc207.gamecentre.general.Tile;

/**
 * A minesweeper game tile
 */
public class MSTile extends Tile implements Serializable {

    /**
     * The image currently shown to the user.
     */
    private String surface;

    /**
     * The underlying background image.
     */
    private String background;

    /**
     * Create a new minesweeper tile with id id and its corresponding background image.
     *
     * @param id the id of the tile
     */
    MSTile(int id) {
        super(id);

        if (id != 9) {
            this.background = "i0" + Integer.toString(id);
        } else {
            this.background = "mine";
        }
        this.surface = "empty";
    }

    /**
     * Sets the surface of the tile to a flag
     */
    void setFlag() {
        this.surface = "flag";
    }

    /**
     * Sets the surface of the tile to that of an empty tile
     */
    void setEmpty() {
        this.surface = "empty";
    }

    /**
     * Returns whether the tile is flagged
     *
     * @return true if the surface is a flag
     */
    public boolean isFlag() {
        return surface.equals("flag");
    }

    /**
     * Returns whether the tile is a bomb
     *
     * @return true if the background of the tile is a mine
     */
    boolean isBomb() {
        return background.equals("mine");
    }

    /**
     * Reveals the tile by setting the surface to the background
     */
    void reveal() {
        surface = background;
    }

    /**
     * Returns the tile's surface image name
     *
     * @return the surface of the tile
     */
    public String getSurface() {
        return surface;
    }

    /**
     * Returns the tile's background image name
     *
     * @return the background of the tile
     */
    public String getBackground() {
        return background;
    }

    /**
     * Checks if the tile is covered by the empty tile or a flag
     *
     * @return true if the background is not the same as the surface.
     */
    boolean isCovered() {
        return surface.equals("empty") || surface.equals("flag");
    }
}
