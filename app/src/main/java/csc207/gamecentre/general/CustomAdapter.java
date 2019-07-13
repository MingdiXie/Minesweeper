package csc207.gamecentre.general;

/*
Taken from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/CustomAdapter.java

This Class is an overwrite of the Base Adapter class
It is designed to aid setting the button sizes and positions in the GridView
 */

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Creates a custom adapter for the buttons
 */
public class CustomAdapter extends BaseAdapter {

    /**
     * The list of buttons it displays
     */
    private ArrayList<Button> mButtons;

    /**
     * The column width and height of the device
     */
    private int mColumnWidth, mColumnHeight;

    /**
     * Returns a new Custom Adapter
     *
     * @param buttons      the buttons to be displayed
     * @param columnWidth  the column width of the device
     * @param columnHeight the column height of the device
     */
    public CustomAdapter(ArrayList<Button> buttons, int columnWidth, int columnHeight) {
        mButtons = buttons;
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;
    }

    /**
     * A getter for the length of mbuttons.
     *
     * @return the length of the ArrayList mbuttons.
     */
    @Override
    public int getCount() {
        return mButtons.size();
    }

    /**
     * A getter for the item at that particular position
     *
     * @param position the item at the index of mbuttons
     * @return the item
     */
    @Override
    public Object getItem(int position) {
        return mButtons.get(position);
    }

    /**
     * A getter for the ItemId which is its position in mbuttons
     *
     * @param position the ItemId at the index position
     * @return the ItemId
     */
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;
        if (convertView == null) {
            button = mButtons.get(position);
        } else {
            button = (Button) convertView;
        }
        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        button.setLayoutParams(params);
        return button;
    }
}
