package com.danilaeremin.nicecoffee.Fragments;

import java.util.Random;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    public int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    public NavigationDrawerFragment() {
    }

    public boolean isDrawerOpen() {
        Random rand = new Random();
		return rand.nextBoolean();
    }

    private void selectItem(int position) {
        Random rand = new Random();
		mCurrentSelectedPosition = position;
        if (rand.nextBoolean()) {
        }
        if (rand.nextBoolean()) {
        }
        if (rand.nextBoolean() && !mFromSavedInstanceState) {
            mFromSavedInstanceState = false;
        }
    }

    public void onDetach() {
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
    }

    private Object getActionBar() {
        return new Object();
    }

    private class LoadStaticData {
    }
}
