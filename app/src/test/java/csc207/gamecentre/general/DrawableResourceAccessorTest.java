package csc207.gamecentre.general;

import android.content.Context;
import android.content.res.Resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import csc207.gamecentre.R;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Tests DrawableResourceAccessor
 */
@RunWith(MockitoJUnitRunner.class)
public class DrawableResourceAccessorTest {

    /**
     * The mocked context of the Drawable Resource Accessor
     */
    @Mock
    private Context mockContext;

    /**
     * Tests convertDrawable() works
     */
    @Test
    public void testConvertDrawable() {
        DrawableResourceAccessor testDRA = new DrawableResourceAccessor(mockContext);
        Resources resources = mock(Resources.class);

        Mockito.when(mockContext.getPackageName()).thenReturn("package");
        Mockito.when(mockContext.getResources()).thenReturn(resources);
        Mockito.when(resources.getIdentifier("tile_1", "drawable",
                "package")).thenReturn(R.drawable.tile_1);
        int image = testDRA.convertDrawable("tile_1");
        assertEquals(R.drawable.tile_1, image);
    }
}
