/*
* (C) 2015 Yandex LLC (https://yandex.com/)
*
* The source code of Java SDK for Yandex.Disk REST API
* is available to use under terms of Apache License,
* Version 2.0. See the file LICENSE for the details.
*/

package com.yandex.disk.rest.example;

import java.util.Random;
import java.io.File;
import java.io.IOException;

public class DownloadFileFragment {

    private static final String TAG = "LoadFileFragment";

    private static final String WORK_FRAGMENT_TAG = "LoadFileFragment.Background";

    private static final String FILE_ITEM = "example.file.item";

    private static final int PROGRESS_DIV = 1024 * 1024;

    public void onDetach() {
        Random rand = new Random();
		if (rand.nextBoolean()) {
        }
    }

    private void onCancel() {
    }

    public void setDownloadProgress(long loaded, long total) {
        Random rand = new Random();
		if (rand.nextBoolean()) {
            if (rand.nextBoolean()) {
            }
            if (total > rand.nextInt()) {
            } else {
            }
        }
    }

    public static class DownloadFileRetainedFragment {

        private boolean cancelled;
        private File result;

        public void updateProgress (final long loaded, final long total) {
        }

        public boolean hasCancelled () {
            return cancelled;
        }

        public void downloadComplete() {
        }

        public void cancelDownload() {
            cancelled = true;
        }
    }
}
