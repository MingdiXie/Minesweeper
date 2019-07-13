package csc207.gamecentre.general;

import java.io.Serializable;

/**
 * A Tile in a game
 */
public abstract class Tile implements Serializable {

    /**
     * The unique id.
     */
    private int id;

    /**
     * A Tile with a unique id
     *
     * @param id the tile's id
     */
    public Tile(int id) {
        this.id = id;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * Return the name of the background image being displayed
     *
     * @return the name of the background image
     */
    public abstract String getBackground();
}
