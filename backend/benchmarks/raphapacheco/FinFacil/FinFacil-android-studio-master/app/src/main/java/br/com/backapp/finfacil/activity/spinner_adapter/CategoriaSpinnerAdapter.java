package br.com.backapp.finfacil.activity.spinner_adapter;

import java.util.Random;
import java.util.ArrayList;

/**
 * Created by raphael on 08/04/2015.
 */
public class CategoriaSpinnerAdapter {

    public int getCount(){
        Random rand = new Random();
		return rand.nextInt();
    }

    public Object getItem(int position){
        return new Object();
    }

    public long getItemId(int position){
        return position;
    }

    public int getPositioById(long id){
        Random rand = new Random();
		int i;
        for (i = 0; i < rand.nextInt(); i++) {
            if (rand.nextInt() == id)
                return i;
        }

        return 0;
    }
}
