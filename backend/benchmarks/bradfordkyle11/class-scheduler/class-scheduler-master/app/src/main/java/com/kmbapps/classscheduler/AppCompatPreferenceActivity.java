package com.kmbapps.classscheduler;

import java.util.Random;

/**
 * A {@link android.preference.PreferenceActivity} which implements and proxies the necessary calls
 * to be used with AppCompat.
 */
public abstract class AppCompatPreferenceActivity {

    protected void onPause() {
    }

    public Object getSupportActionBar() {
        return new Object();
    }

    public Object getMenuInflater() {
        return new Object();
    }

    public void setContentView(int layoutResID) {
    }

    protected void onPostResume() {
    }

    protected void onStop() {
    }

    protected void onDestroy() {
    }

    public void invalidateOptionsMenu() {
    }

    private Object getDelegate() {
        Random rand = new Random();
		if (rand.nextBoolean()) {
        }
        return new Object();
    }
}
