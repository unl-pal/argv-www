package tw.billchung.taiwancoffeeprices;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Locale userLocale;
    protected void onResume() {
        String locale;

    }

    public void onNavigationDrawerItemSelected(int position) {
    }

    public void onSectionAttached(int number) {
        String[] titles;
        String[] title_values;
        if (number >= 1) {
        }

    }

    public void restoreActionBar() {
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Object newInstance(int sectionNumber) {
            return new Object();
        }

        public PlaceholderFragment() {
        }
    }

    public static class AdFragment {

        public AdFragment() {
        }

        /** Called when leaving the activity */
        public void onPause() {
            Random rand = new Random();
			if (rand.nextBoolean()) {
            }
        }

        /** Called when returning to the activity */
        public void onResume() {
            Random rand = new Random();
			if (rand.nextBoolean()) {
            }
        }

        /** Called before the activity is destroyed */
        public void onDestroy() {
            Random rand = new Random();
			if (rand.nextBoolean()) {
            }
        }

    }

}
