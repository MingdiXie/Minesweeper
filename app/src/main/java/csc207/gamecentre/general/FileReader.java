package csc207.gamecentre.general;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static android.content.Context.MODE_PRIVATE;

/**
 * A File Reader that reads and writes files in a context
 */
public class FileReader {

    /**
     * The current context the file reader is in
     */
    private Context context;

    /**
     * Creates a new File Reader for this context
     *
     * @param context the current context
     */
    public FileReader(Context context) {
        this.context = context;
    }

    /**
     * Load the object from fileName.
     *
     * @param fileName the name of the file
     * @return the object in the file
     */
    public Object loadFromFile(String fileName) {
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                Object readObject = input.readObject();
                inputStream.close();
                return readObject;
            }
        } catch (FileNotFoundException e) {
            Log.e("file reader activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("file reader activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("file reader activity", "File contained unexpected data type: " + e.toString());
        }
        return null;
    }

    /**
     * Save the object to fileName.
     *
     * @param fileName    the name of the file
     * @param writeObject the object to write to the file
     */
    public void saveToFile(String fileName, Object writeObject) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(writeObject);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
