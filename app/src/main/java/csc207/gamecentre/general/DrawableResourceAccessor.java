package csc207.gamecentre.general;

import android.content.Context;

/**
 * An object to access drawable resources in a context
 */
public class DrawableResourceAccessor {

    /**
     * The object's context
     */
    private Context context;

    /**
     * Create a new DrawableResourceAccessor for the current context
     *
     * @param context the current context
     */
    public DrawableResourceAccessor(Context context) {
        this.context = context;
    }

    /**
     * Converts the imageName into its corresponding image id in resources
     *
     * @param imageName the name of the image
     * @return the id of the image
     */
    public int convertDrawable(String imageName) {
        int image;
        image = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return image;
    }
}
