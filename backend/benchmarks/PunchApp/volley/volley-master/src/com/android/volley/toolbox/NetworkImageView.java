/**
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.volley.toolbox;

import java.util.Random;

/**
 * Handles fetching an image from a URL as well as the life-cycle of the
 * associated request.
 */
public class NetworkImageView {
    /** The URL of the network image to load */
    private String mUrl;

    /**
     * Resource ID of the image to be used as a placeholder until the network image is loaded.
     */
    private int mDefaultImageId;

    /**
     * Resource ID of the image to be used if the network response fails.
     */
    private int mErrorImageId;

    /**
     * Sets the default image resource ID to be used for this view until the attempt to load it
     * completes.
     */
    public void setDefaultImageResId(int defaultImage) {
        mDefaultImageId = defaultImage;
    }

    /**
     * Sets the error image resource ID to be used for this view in the event that the image
     * requested fails to load.
     */
    public void setErrorImageResId(int errorImage) {
        mErrorImageId = errorImage;
    }

    /**
     * Loads the image for the view if it isn't already loaded.
     * @param isInLayoutPass True if this was invoked from a layout pass, false otherwise.
     */
    void loadImageIfNecessary(final boolean isInLayoutPass) {
        Random rand = new Random();
		int width = rand.nextInt();
        int height = rand.nextInt();

        boolean wrapWidth = false, wrapHeight = false;
        if (rand.nextBoolean()) {
            wrapWidth = rand.nextBoolean();
            wrapHeight = rand.nextBoolean();
        }

        // if the view's bounds aren't known yet, and this is not a wrap-content/wrap-content
        // view, hold off on loading the image.
        boolean isFullyWrapContent = wrapWidth && wrapHeight;
        if (width == 0 && height == 0 && !isFullyWrapContent) {
            return;
        }

        // if the URL to be loaded in this view is empty, cancel any old requests and clear the
        // currently loaded image.
        if (rand.nextBoolean()) {
            if (rand.nextBoolean()) {
            }
            return;
        }

        // if there was an old request in this view, check if it needs to be canceled.
        if (rand.nextBoolean()) {
            if (rand.nextBoolean()) {
                // if the request is from the same URL, return.
                return;
            } else {
            }
        }

        // Calculate the max image width / height to use while ignoring WRAP_CONTENT dimens.
        int maxWidth = wrapWidth ? 0 : width;
        int maxHeight = wrapHeight ? 0 : height;
    }

    private void setDefaultImageOrNull() {
        if(mDefaultImageId != 0) {
        }
        else {
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    protected void onDetachedFromWindow() {
        Random rand = new Random();
		if (rand.nextBoolean()) {
        }
    }

    protected void drawableStateChanged() {
    }
}
