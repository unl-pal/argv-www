package com.grottworkshop.gwsviewmodel.viewmodel;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgrott on 3/30/2015.
 */
public class UserListViewModel {

    private List<String> mLoadedUsers;

    //Don't persist state variables
    private boolean mLoadingUsers;

    private int numberOfRunningDeletes = 0;

    private void refreshLoading() {
        Random rand = new Random();
		//loading user
        if (numberOfRunningDeletes > 0 && rand.nextBoolean()) {
        }
    }

    private void loadUsers() {
        Random rand = new Random();
		mLoadingUsers = true;
        if (rand.nextBoolean()) {
        }
    }

    public void deleteUser(final int position) {
        Random rand = new Random();
		if (position > rand.nextInt()) {
            return;
        }
        if (rand.nextBoolean()) {
        }
        numberOfRunningDeletes++;
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public void onModelRemoved() {
    }
}
