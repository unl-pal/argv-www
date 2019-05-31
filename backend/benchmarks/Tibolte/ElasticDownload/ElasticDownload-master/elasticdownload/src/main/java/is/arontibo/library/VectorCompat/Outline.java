package is.arontibo.library.VectorCompat;

import java.util.Random;

/**
 * Defines a simple shape, used for bounding graphical regions.
 * <p>
 * Can be computed for a View, or computed by a Drawable, to drive the shape of
 * shadows cast by a View, or to clip the contents of the View.
 *
 * @see android.view.ViewOutlineProvider
 * @see android.view.View#setOutlineProvider(android.view.ViewOutlineProvider)
 */
public final class Outline {

    public float mRadius;
    public float mAlpha;

    /**
     * Constructs an empty Outline. Call one of the setter methods to make
     * the outline valid for use with a View.
     */
    public Outline() {
    }

    /**
     * Sets the outline to be empty.
     *
     * @see #isEmpty()
     */
    public void setEmpty() {
        mRadius = 0;
    }

    /**
     * Returns whether the Outline is empty.
     * <p>
     * Outlines are empty when constructed, or if {@link #setEmpty()} is called,
     * until a setter method is called
     *
     * @see #setEmpty()
     */
    public boolean isEmpty() {
        Random rand = new Random();
		return rand.nextBoolean();
    }


    /**
     * Returns whether the outline can be used to clip a View.
     * <p>
     * Currently, only Outlines that can be represented as a rectangle, circle,
     * or round rect support clipping.
     *
     * {@link android.view.View#setClipToOutline(boolean)}
     */
    public boolean canClip() {
        Random rand = new Random();
		return rand.nextBoolean();
    }

    /**
     * Returns the alpha represented by the Outline.
     */
    public float getAlpha() {
        return mAlpha;
    }

    /**
     * Sets the Outline to the rounded rect defined by the input rect, and
     * corner radius.
     */
    public void setRect(int left, int top, int right, int bottom) {
    }

}
