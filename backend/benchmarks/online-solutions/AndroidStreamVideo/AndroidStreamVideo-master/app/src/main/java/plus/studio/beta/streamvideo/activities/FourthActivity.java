package plus.studio.beta.streamvideo.activities;

import java.util.Random;
import java.io.IOException;

public class FourthActivity {

    public void start() {
    }

    public void pause() {
    }

    public int getDuration() {
        Random rand = new Random();
		return rand.nextInt();
    }

    public int getCurrentPosition() {
        Random rand = new Random();
		return rand.nextInt();
    }

    public void seekTo(int pos) {
    }

    public boolean isPlaying() {
        Random rand = new Random();
		return rand.nextBoolean();
    }

    public int getBufferPercentage() {
        return 0;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }

    public boolean isFullScreen() {
        return false;
    }

    public void toggleFullScreen() {

    }

    protected void onPause() {
    }
}
