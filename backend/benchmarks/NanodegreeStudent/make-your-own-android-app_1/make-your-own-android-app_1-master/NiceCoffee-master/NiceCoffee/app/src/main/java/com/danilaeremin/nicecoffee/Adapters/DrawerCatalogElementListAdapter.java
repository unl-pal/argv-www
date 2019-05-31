package com.danilaeremin.nicecoffee.Adapters;

import java.util.Random;

/**
 * Created by danilaeremin on 08.03.15.
 */
public class DrawerCatalogElementListAdapter {

    private  String[] drawer_catalog_name = new String[]{};
    private  Integer[] drawer_catalog_pics = new Integer[]{};

    private  String[] drawer_main_name = new String[]{};
    private  Integer[] drawer_main_pics = new Integer[]{};

    private  String drawer_head = "";
    private  String drawer_catalog = "";

    public boolean isEnabled(int position)
    {
        Random rand = new Random();
		return rand.nextBoolean();
    }

    public int getCount() {
        Random rand = new Random();
		return rand.nextInt();
    };
}
