package csc207.gamecentre.slidingtiles;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests that STTile works
 */
public class STTileTest {

    /**
     * Tests if getBackground gets the correct image name
     */
    @Test
    public void testGetBackground() {
        STTile tile = new STTile(1, 1);
        String background = tile.getBackground();
        assertEquals("tile_1", background);
    }


    /**
     * Tests if compareTo returns negative if tile is compared to a bigger tile
     */
    @Test
    public void testCompareTo() {
        STTile tile1 = new STTile(1, 1);
        STTile tile2 = new STTile(2, 2);
        int difference = tile1.compareTo(tile2);
        assertEquals(-1, difference);
    }
}
