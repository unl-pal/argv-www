package com.kmbapps.classscheduler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by Kyle on 9/23/2014.
 */
public class ClassLoader {
    private static boolean classesLoaded = false;
    private static boolean classesChanged = false;

    private static boolean schedulesChange = false;
    private static boolean selectSchedulesChanged = false;
    private static boolean schedulesChanged = false;
    private static boolean schedulesLoaded = false;
    private static boolean schedulesOptionsChanged = false;
    public static final int ALL_SCHEDULES = 0;
    public static final int SELECT_SCHEDULES = 1;

    private static boolean currentScheduleChanged = false;
    private static boolean scheduleLoaded = false;

    private static String savedClassesFile = "savedclasses.txt";
    private static String currentScheduleFile = "CurrentSchedule.txt";
    private static String savedNotebooksFile = "Notebooks.txt";
    private static String schedulesFile = "schedules.txt";
    private static String selectSchedulesFile = "SelectSchedules.txt";

    private static boolean notebooksChanged = false;
    private static boolean notebooksLoaded = false;

    private static Deque<Integer> availableColors;
    private static Deque<Integer> currScheduleAvailableColors;

    private static int minCreditHours = Integer.MIN_VALUE;
    private static int maxCreditHours = Integer.MAX_VALUE;
    private static int minNumClasses = Integer.MIN_VALUE;
    private static int maxNumClasses = Integer.MAX_VALUE;
    public static final int CURR_SCHEDULE = 0;
    public static final int DESIRED_CLASSES = 1;


    static void setSchedulesChanged(boolean schedulesChanged){
    }

    static void setSchedulesOptionsChanged(boolean schedulesOptionsChanged){
    }

    /**
     * Notify the color deque that this color is no longer in use
     * @param color the color that is no longer in use
     * @param which which color list to release from
     */

    public static void releaseColor(int color, int which){

    }

    public static Object getMyClasses() {
        return new Object();
    }

    public static int getMinCreditHours() {
        return minCreditHours;
    }

    public static int getMaxCreditHours() {
        return maxCreditHours;
    }

    public static int getMinNumClasses() {
        return minNumClasses;
    }

    public static int getMaxNumClasses() {
        return maxNumClasses;
    }

    public static Object getNewClass() {
        return new Object();
    }

    public static Object getOldClass() {

        return new Object();
    }

    public static Object getNewSection() {
        return new Object();
    }

    public static Object getOldSection() {
        return new Object();
    }
}
