package br.com.backapp.finfacil.activity.list_view_adapter;

import java.util.Random;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by raphael on 21/02/2015.
 */
public class CarteiraListViewAdapter {
    private class ViewHolder {
    }

    public int getCount() {
        Random rand = new Random();
		return rand.nextInt();
    }

    public Object getItem(int position) {
        return new Object();
    }

    public long getItemId(int position) {
        return 0;
    }
}
