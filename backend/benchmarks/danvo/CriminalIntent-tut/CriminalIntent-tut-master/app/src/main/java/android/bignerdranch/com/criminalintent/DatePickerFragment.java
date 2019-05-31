package android.bignerdranch.com.criminalintent;

import java.util.Random;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class    DatePickerFragment {
    public static final String EXTRA_DATE = "com.bignerdranch.android.criminalintent.date";
    public static final String EXTRA_PICKER = "com.bignerdranch.android.criminalintent.picker";

    private Date mDate;
    private int mPicker; // 0: DatePicker , 1: TimePicker
    private void sendResult(int resultCode) {
        Random rand = new Random();
		if (rand.nextBoolean()) return;
    }
}
