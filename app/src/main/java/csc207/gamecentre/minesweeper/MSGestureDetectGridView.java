package csc207.gamecentre.minesweeper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Creating a custom GridView fitted for the functionality of MineSweeper
 */
public class MSGestureDetectGridView extends GridView {

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
    private MSMovementController mController;

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
     * Initializing the Grid View which is a subclass of GridView
     *
     * @param context the current state of the application or object
     */
    public MSGestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Initialize a new grid view
     *
     * @param context the current context
     * @param attrs   the attributes
     */
    public MSGestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Initialize a new grid view
     *
     * @param context      the current context
     * @param attrs        the attributes
     * @param defStyleAttr the style attributes
     */
    public MSGestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * The additional constructor requirements for MSGestureDetectGridView
     */
    private void init(final Context context) {
        mController = new MSMovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = MSGestureDetectGridView.this.pointToPosition
                        ((Math.round(event.getX())), Math.round(event.getY()));
                mController.processTapMovement(context, position);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent event) {
                int position = MSGestureDetectGridView.this.pointToPosition
                        ((Math.round(event.getX())), Math.round(event.getY()));
                mController.flagTile(context, position);

            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }

    /**
     * Process a motion event
     *
     * @param ev the motion event by user
     * @return true if MSGestureDetectGridView consumes the event
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
     * Process a touch event
     *
     * @param ev the motion event
     * @return the touch event consumed by MSGestureDetectGridView
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    /**
     * A setter for a BoardManager
     *
     * @param boardManager a boardManager
     */
    public void setBoardManager(MSBoardManager boardManager) {
        mController.setBoardManager(boardManager);
    }
}
