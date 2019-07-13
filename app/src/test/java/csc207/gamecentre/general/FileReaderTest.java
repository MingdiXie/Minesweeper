package csc207.gamecentre.general;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.assertNull;

/**
 * Tests if FileReader works
 */
@RunWith(MockitoJUnitRunner.class)
public class FileReaderTest {

    /**
     * The mock context
     */
    @Mock
    private Context mockContext;

    /**
     * A mock output stream
     */
    @Mock
    private FileOutputStream mockOutputStream;

    /**
     * Testing ReadFile to make sure an item is recovered by it is saved then loaded
     *
     * @throws FileNotFoundException when getItem is null
     */
    @Test
    public void testReadFile() throws FileNotFoundException {
        FileReader fileReader = new FileReader(mockContext);
        String item = "Test";
        Mockito.when(mockContext.openFileOutput("test.ser", MODE_PRIVATE)).thenReturn(mockOutputStream);
        fileReader.saveToFile("test.ser", item);
        Object getItem = fileReader.loadFromFile("test.ser");
        assertNull(getItem);
    }
}
