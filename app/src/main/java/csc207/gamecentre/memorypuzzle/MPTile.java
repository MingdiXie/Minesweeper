package csc207.gamecentre.memorypuzzle;

import java.io.Serializable;

import csc207.gamecentre.general.Tile;

/**
 * A Tile in the Memory Puzzle
 */
public class MPTile extends Tile implements Serializable {

    /**
     * If the tile's picture is flipped up and visible
     */
    private boolean flippedUp;

    /**
     * The tile's picture background
     */
    private String background;

    /**
     * The tile's blank surface
     */
    private String surface;

    /**
     * Creates a new Memory Puzzle tile with id id and corresponding background, blank surface,
     * and is not flipped up
     *
     * @param id the Tile's id
     */
    public MPTile(int id) {
        super(id);
        flippedUp = false;
        surface = "surface";
        background = "pic_" + Integer.toString(id);
    }

    /**
     * Returns if the Tile is flipped up and the background is visible
     *
     * @return if Tile is flipped up
     */
    boolean isFlippedUp() {
        return flippedUp;
    }

    /**
     * Flips the Tile
     */
    void flip() {
        flippedUp = !flippedUp;
    }

    /**
     * Returns the current background of the Tile
     *
     * @return the current background
     */
    public String getBackground() {
        if (isFlippedUp()) {
            return background;
        } else {
            return surface;
        }
    }
}
