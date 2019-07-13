package csc207.gamecentre.general;

/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * A grid view that has built in logic for handling swipes between buttons
 */
public class GestureDetectGridView extends GridView {

    /**
     * The min distance for a swipe
     */
    public static final int SWIPE_MIN_DISTANCE = 100;

    /**
     * A Gesture Detector for a single tap and long press
     */
    private GestureDetector gDetector;

    /**
     * A minesweeper movement processor for revealing the tile and setting the flag
     */
    private MovementController mController;

    /**
     * Interceptor for the touch event if ACTION_DOWN is called
     */
    private boolean mFlingConfirmed = false;

    /**
     * The X-coordinate of the touch
     */
    private float mTouchX;

    /**
     * The Y-coordinate of the touch
     */
    private float mTouchY;

    /**
     * Creates a new GestureDetectGridView for the current context
     *
     * @param context the current context
     */
    public GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Creates a new GestureDetectGridView for the current context
     *
     * @param context the current context
     * @param attrs   the attributes
     */
    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Creates a new GestureDetectGridView for the current context
     *
     * @param context      the current context
     * @param attrs        the attributes
     * @param defStyleAttr the style of the attributes
     */
    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Initialized the grid view in the current context
     *
     * @param context the current context
     */
    private void init(final Context context) {
        mController = new MovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = GestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));

                mController.processTapMovement(context, position);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }

    /**
     * The gridView will consume the touch event if the function returns true
     *
     * @param ev the motion event encountered
     * @return true if the gridView consumes the event
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * @param ev the motion event
     * @return true if the view group contains consumes the event
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    /**
     * Set the board Manager of the movement controller
     *
     * @param boardManager the board manager
     */
    public void setBoardManager(BoardManager boardManager) {
        mController.setBoardManager(boardManager);
    }
}
