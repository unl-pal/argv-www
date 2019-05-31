package is.arontibo.library;

/**
 * Created by thibaultguegan on 15/03/15.
 */
public class ElasticDownloadView {

    private static final String LOG_TAG = ElasticDownloadView.class.getSimpleName();

    private int mBackgroundColor;

    /**
     * MARK: Overrides
     */

    protected void onFinishInflate() {
    }

    /**
     * MARK: Public methods
     */

    public void startIntro() {
    }

    public void success() {
    }

    public void fail() {
    }


    /**
     * MARK: Enter animation overrides
     */

    public void onEnterAnimationFinished() {

        // Do further actions if necessary
    }
    

    /**
     * Set the background color of the Elastic Download View
     * @param passedColor int Color Color to set the background
     */
    public void setBackgroundColor(int passedColor) {
        this.mBackgroundColor = passedColor;
    }

}
