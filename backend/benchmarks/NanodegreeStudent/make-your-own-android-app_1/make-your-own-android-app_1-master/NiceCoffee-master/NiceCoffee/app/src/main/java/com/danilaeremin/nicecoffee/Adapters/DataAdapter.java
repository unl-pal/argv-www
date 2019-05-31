package com.danilaeremin.nicecoffee.Adapters;

import java.util.Random;

/**
 * Created by danilaeremin on 08.03.15.
 */
public class DataAdapter {

    private String[] drawerMainElements;
    private Integer[] drawerMainElementsPics;

    // возвращает содержимое выделенного элемента списка
    public String GetItem(int position) {
        return drawerMainElements[position];
    }


    public int getCount() {
        Random rand = new Random();
		return rand.nextInt();
    }

}
