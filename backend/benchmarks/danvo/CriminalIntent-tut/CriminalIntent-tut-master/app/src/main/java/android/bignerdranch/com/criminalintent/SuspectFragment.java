package android.bignerdranch.com.criminalintent;


import java.util.UUID;

public class SuspectFragment {

    public static final String EXTRA_SUSPECT_CRIME_ID= "com.bignerdranch.android.criminalintent.suspect_crime_id";

    public static final String EXTRA_ORDER = "com.bignerdranhc.andorid.criminalintent.fragment_order";

    public static final int ORDER_DELETE = 2;
    public static final int ORDER_NEW_SUSPECT = 3;
    public static final int ORDER_CALL_SUSPECT = 4;


    private int mOrder;

    private void closeFragment() {
    }

    private void sendResult(int result) {
    }

    public void onDestroyView() {
        if (mOrder != 0) {
        }
    }
}



