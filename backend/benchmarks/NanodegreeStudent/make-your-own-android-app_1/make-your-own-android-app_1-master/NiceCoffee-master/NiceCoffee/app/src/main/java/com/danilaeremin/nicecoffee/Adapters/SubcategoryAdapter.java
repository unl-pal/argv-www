package com.danilaeremin.nicecoffee.Adapters;

import java.util.Random;

/**
 * Created by danilaeremin on 09.03.15.
 */
public class SubcategoryAdapter {
    public Object getCategory(int position) {
        Random rand = new Random();
		if(rand.nextBoolean()) {
            return new Object();
        }

        return new Object();
    }
}
