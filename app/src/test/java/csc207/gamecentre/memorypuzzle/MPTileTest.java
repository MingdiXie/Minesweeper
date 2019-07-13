package csc207.gamecentre.memorypuzzle;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests a Memory Puzzle Tile
 */
public class MPTileTest {

    /**
     * Test that all tiles start off unflipped
     */
    @Test
    public void testIsFlippedFalse() {
        MPTile tile1 = new MPTile(1);
        boolean isFlipped = tile1.isFlippedUp();
        assertFalse(isFlipped);
    }

    /**
     * Test if isFlippedUp and flip works
     */
    @Test
    public void testIsFlippedTrue() {
        MPTile tile1 = new MPTile(1);
        tile1.flip();
        boolean isFlipped = tile1.isFlippedUp();
        assertTrue(isFlipped);
    }

    /**
     * Test if getBackground gets correct background
     */
    @Test
    public void testGetBackground() {
        // unflipped gets surface
        MPTile tile2 = new MPTile(2);
        assertEquals("surface", tile2.getBackground());

        // flipped gets its background
        MPTile tile3 = new MPTile(3);
        tile3.flip();
        assertEquals("pic_3", tile3.getBackground());
    }
}