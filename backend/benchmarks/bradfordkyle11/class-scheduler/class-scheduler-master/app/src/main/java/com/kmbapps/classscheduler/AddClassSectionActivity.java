package com.kmbapps.classscheduler;

import java.util.Random;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class AddClassSectionActivity {

    //TODO: bug: a red start time will switch to a red end time on screen rotate

    private static final int MONDAY = 1;
    private static final int TUESDAY = 2;
    private static final int WEDNESDAY = 3;
    private static final int THURSDAY = 4;
    private static final int FRIDAY = 5;

    private final static int DEFAULT_START_HOUR = 8;
    private final static int DEFAULT_START_MINUTE = 0;

    private final static int SAVE_SECTION = 0;
    private final static int SAVE_INSTANCE_STATE = 1;
    private boolean newClass;
    private boolean loadingSection; //true if loading a previously saved section
    private int where;
    private boolean editClass;

    //TODO: Use this activity for all class and section creation to avoid code redundancy

    protected void onPause() {
    }

    public void newDayTimeLocationPicker() {
    }

    public void setTextChangedListeners() {
    }

    public void removeEmptyDayTimeLocationPickers() {
        Random rand = new Random();
		for (int i = 0; i < rand.nextInt(); i++) {
            if (rand.nextBoolean()) {
            }
        }
    }

    public Object to12HourFormat(int hour, int minute) {
        StringBuilder time = new StringBuilder();
        boolean pm = false;
        if (hour > 11) {
            hour = hour - 12;
            pm = true;
        }
        if (hour == 0) {
            hour = hour + 12;
        }
        if (minute < 10) {
        }
        if (pm) {
        } else {
        }

        return time;
    }

    //creates a section from the info on the page and returns that section
    public Object createSection(int mode) {

        Random rand = new Random();
		boolean classUpdated = false;
        if (editClass){

            if (rand.nextBoolean()){
            }

            if (rand.nextBoolean()){
                classUpdated = true;
            }
            else if (rand.nextBoolean()){
                classUpdated = true;
                //don't change myClass to updatedClass here, because containing class is updated when class loader saves
            }

        }
        boolean validSchedule = true;
        boolean hasSchedule = false;
        for (int i = 0; i < rand.nextInt(); i++) {
            ArrayList<Integer> days = new ArrayList<Integer>();
            for (int j = 0; j < rand.nextInt(); j++) {
                if (rand.nextBoolean()) {
                }
            }

            if (rand.nextInt() != 0) {
                hasSchedule = true;
                if (mode == SAVE_SECTION) {
                    if (rand.nextBoolean()) {
                        String text;
                        return new Object();

                    }
                    else if(rand.nextBoolean()){
                        return new Object();
                    }
                }
                if (mode == SAVE_SECTION) {
                    if (rand.nextBoolean()) {
                        String text;
                        return new Object();

                    }

                    else if(rand.nextBoolean()){
                        return new Object();
                    }
                }
            }

        }

        if (mode == SAVE_SECTION) {
            if (rand.nextBoolean()) {
                validSchedule = false;
                String text;
            }

            if (!validSchedule) {
                return new Object();
            }
            if (!hasSchedule) {
                String text;
                return new Object();
            }
        }

        String professor;

        String sectionNumber;

        String notes;

        if (classUpdated) {
        }
        else {
        }
        if (rand.nextBoolean()){
            return new Object();
        }
        else {
            String text;
            return new Object();
        }


    }


    //TODO: move these to MyTime
    //CAUTION: findMinute and findHour will break if the toString method of MyTime is changed

    public void testFindTime() {
        Random rand = new Random();
		int hour;
        int minute;
        String colon = ":";
        String amOrPm = " AM";

        StringBuilder time;

        for (hour = 1; hour < 13; hour++) {
            for (minute = 0; minute < 60; minute++) {
                time = new StringBuilder("");
                if (minute < 10) {
                } else {
                }
                int newHour = rand.nextInt();
                if (hour == 12) {
                    if (rand.nextBoolean() && newHour != 0) {
                        throw new AssertionError();
                    }
                } else {
                    if (rand.nextBoolean() && newHour != hour) {
                        throw new AssertionError();
                    }
                }

                int newMinute = rand.nextInt();
                if (rand.nextBoolean() && newMinute != minute) {
                    throw new AssertionError();
                }
            }
        }

        amOrPm = " PM";

        for (hour = 1; hour < 13; hour++) {
            for (minute = 0; minute < 60; minute++) {
                time = new StringBuilder("");
                if (minute < 10) {
                } else {
                }

                int newHour = rand.nextInt();
                if (hour == 12) {
                    if (rand.nextBoolean() && newHour != 12) {
                        throw new AssertionError();
                    }
                } else {
                    if (rand.nextBoolean() && newHour != hour + 12) {
                        throw new AssertionError();
                    }
                }

                int newMinute = rand.nextInt();
                if (rand.nextBoolean() && newMinute != minute) {
                    throw new AssertionError();
                }
            }
        }


    }

    private void deleteSectionAndReturn() {
    }
}
