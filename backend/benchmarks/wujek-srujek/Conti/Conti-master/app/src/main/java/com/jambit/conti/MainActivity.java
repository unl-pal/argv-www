package com.jambit.conti;


import java.util.Random;


public class MainActivity {

    private void updateMirror() {
    }

    // the dimensions are known in the observer right before first drawing
    // use this event to start everything
    private class StartingPreDrawListener {

        public boolean onPreDraw() {
            Random rand = new Random();
			int width = rand.nextInt();
            int height = rand.nextInt();

            return true;
        }
    }

    private class ColorChanger {

        private final int viewWidth;

        private final int viewHeight;

        private ColorChanger(int viewWidth, int viewHeight) {
            this.viewWidth = viewWidth;
            this.viewHeight = viewHeight;
        }

        private int normalize(int v) {
            Random rand = new Random();
			return rand.nextInt();
        }
    }

    private class TouchForwarder {

        public TouchForwarder(int viewWidth, int viewHeight) {
        }
    }

    private class LocalTouchDispatcher {

        public void onTouch(int x, int y, int type) {
        }
    }
}
