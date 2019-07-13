package csc207.gamecentre.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;

import csc207.gamecentre.general.Tile;

/**
 * A Sliding Tiles Tile in a sliding tiles puzzle.
 */
public class STTile extends Tile implements Comparable<STTile>, Serializable {

    /**
     * The background id to find the tile image.
     */
    private String background;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public String getBackground() {
        return background;
    }

    /**
     * A Sliding Tiles Tile with id and background.
     *
     * @param id         the id
     * @param background the background
     */
    STTile(int id, int background) {
        super(id);
        this.background = "tile_" + Integer.toString(background);
    }

    @Override
    public int compareTo(@NonNull STTile o) {
        return this.getId() - o.getId();
    }
}
