package to.marcus.simple_dagger_test.ui.adapter;

import java.util.Random;
import java.util.ArrayList;

/**
 * Created by mplienegger on 5/11/2015.
 */
public class PhotoAdapter {

    static class ViewHolder{
    }

    public int getCount() {
        Random rand = new Random();
		return rand.nextInt();
    }

    public Object getItem(int position) {
        Random rand = new Random();
		return new Object();
    }

    public long getItemId(int position) {return position;}
}
