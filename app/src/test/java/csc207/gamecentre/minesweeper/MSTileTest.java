package csc207.gamecentre.minesweeper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Unit tests for MSTile.
 */
public class MSTileTest {

    /**
     * The bomb tile for testing.
     */
    private MSTile tile;

    /**
     * Make a MSTile with id 9, a bomb tile.
     */
    @Before
    public void setUp() {
        tile = new MSTile(9);
    }

    /**
     * Test whether tiles are initialized with the correct background and surface strings.
     */
    @Test
    public void testTileConstructor() {
        MSTile tile2 = new MSTile(2);
        assertEquals("i02", tile2.getBackground());
        assertEquals("empty", tile2.getSurface());
        assertEquals("mine", tile.getBackground());
        assertEquals("empty", tile.getSurface());
    }

    /**
     * Test whether reveal correctly changes the surface to the background.
     */
    @Test
    public void testReveal() {
        assertNotEquals(tile.getBackground(), tile.getSurface());
        tile.reveal();
        assertEquals(tile.getBackground(), tile.getSurface());
    }

    /**
     * Test whether setFlag correctly changes the surface to a flag.
     */
    @Test
    public void testSetFlag() {
        assertNotEquals("flag", tile.getSurface());
        tile.setFlag();
        assertEquals("flag", tile.getSurface());
    }

    /**
     * Test whether setEmpty correctly changes the surface to that of an empty tile.
     */
    @Test
    public void testSetEmpty() {
        assertEquals("empty", tile.getSurface());
        tile.reveal();
        assertNotEquals("empty", tile.getSurface());
        tile.setEmpty();
        assertEquals("empty", tile.getSurface());
    }

    /**
     * Test whether flags are detected and changed.
     */
    @Test
    public void testIsFlag() {
        try{
        int a = 1;
        int b = 0;
        int c = a/b;}
        catch (ArithmeticException e){
            fail("Unexpected " + e);
        }
    }

    /**
     * Test whether isBomb correctly detects bomb tiles.
     */
    @Test
    public void testIsBomb() {
        assertTrue(tile.isBomb());
        for (int id = 0; id < 9; id++) {  // all non bomb tiles
            MSTile tile = new MSTile(id);
            assertFalse(tile.isBomb());
        }
    }

    /**
     * Test whether all tiles start covered.
     */
    @Test
    public void testisCovered() {
        for (int id = 0; id < 10; id++) {
            MSTile tile = new MSTile(id);
            assertTrue(tile.isCovered());
        }
    }
}
