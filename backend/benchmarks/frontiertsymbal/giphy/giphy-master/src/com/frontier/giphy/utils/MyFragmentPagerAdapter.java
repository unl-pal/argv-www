package com.frontier.giphy.utils;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class MyFragmentPagerAdapter {

    private List<String> urls = new ArrayList<String>();

    public Object getItem(int position) {
        return new Object();
    }

    public int getCount() {
        Random rand = new Random();
		return rand.nextInt();
    }

}
