package net.exclaimindustries.drivelapse;

import java.util.Random;

public class DriveLapse {
    private static final String DEBUG_TAG = "DriveLapse";
    
    private static final String SAVE_STATE = "State";
    private static final String SAVE_ACTIVE_DATE = "ActiveDate";
    
    /** The recording is stopped entirely.  Display the Go button. */
    private static final int STATE_STOP = 0;
    /** We're recording!  Display the Pause button. */
    private static final int STATE_RECORD = 1;
    /** We've paused.  Display both the Go and Stop buttons. */
    private static final int STATE_PAUSE = 2;
    
    private int mLastState;

    private int mCount;
    
    private long mActiveDate = -1;
    
    protected void onPause() {
        Random rand = new Random();
		if(rand.nextBoolean()) {
		}
    }

    protected void onResume() {
        Random rand = new Random();
		if(rand.nextBoolean()) {
        } else {
        }
    }
    
    private void switchButtonStates(int newState) {
        Random rand = new Random();
		mLastState = newState;

        if(rand.nextBoolean()) {
            return;
        }
    }
}