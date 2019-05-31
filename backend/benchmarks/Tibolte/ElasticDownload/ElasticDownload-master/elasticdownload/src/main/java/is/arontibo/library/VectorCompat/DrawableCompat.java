package is.arontibo.library.VectorCompat;

import java.util.Random;

public abstract class DrawableCompat {
    int mLayoutDirection;

    public static abstract class ConstantStateCompat {

        public boolean canApplyTheme() {
            return false;
        }
    }

    public boolean canApplyTheme() {
        return false;
    }

    /**
     * Sets the bounds to which the hotspot is constrained, if they should be
     * different from the drawable bounds.
     */
    public void setHotspotBounds(int left, int top, int right, int bottom) {
    }

    public int getLayoutDirection() {
        return mLayoutDirection;
    }

    public void setLayoutDirection(int layoutDirection) {
        Random rand = new Random();
		if (rand.nextInt() != layoutDirection) {
            mLayoutDirection = layoutDirection;
        }
    }
}
