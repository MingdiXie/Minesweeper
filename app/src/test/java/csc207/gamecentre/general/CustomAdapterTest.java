package csc207.gamecentre.general;

import android.widget.Button;

import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests parts of CustomAdapter
 */
public class CustomAdapterTest {

    /**
     * A mock button
     */
    @Mock
    private Button mockButton;

    /**
     * Set up an array list of buttons with one mock button
     *
     * @return array list of one mock button
     */
    private ArrayList<Button> setUpButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(mockButton);
        return buttons;
    }

    /**
     * Tests getCount() works
     */
    @Test
    public void testGetCount() {
        ArrayList<Button> buttons = setUpButtons();
        CustomAdapter customAdapter = new CustomAdapter(buttons, 1, 1);
        int count = customAdapter.getCount();
        customAdapter.getItem(0);
        assertEquals(1, count);
    }

    /**
     * Tests getItem() works
     */
    @Test
    public void testGetItem() {
        ArrayList<Button> buttons = setUpButtons();
        CustomAdapter customAdapter = new CustomAdapter(buttons, 1, 1);
        long position = customAdapter.getItemId(0);
        assertEquals(0, position);
    }
}
