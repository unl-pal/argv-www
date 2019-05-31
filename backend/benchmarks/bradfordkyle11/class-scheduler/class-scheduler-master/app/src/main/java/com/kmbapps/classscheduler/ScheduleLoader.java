package com.kmbapps.classscheduler;


import java.util.Random;
import java.util.List;

/**
 * Created by Kyle on 5/6/2017.
 */

public class ScheduleLoader {
    public static final int ALL_SCHEDULES_LOADER = 1;
    public static final int SELECT_SCHEDULES_LOADER = 0;
    private int minCreditHours;
    private int maxCreditHours;
    private int minNumClasses;
    private int maxNumClasses;
    private boolean scheduleChanged = true;


    public Object loadInBackground(){
        Random rand = new Random();
		//just options updated
        //if (currSchedules == null){
            //currSchedules = ClassLoader.loadSchedules(getContext(), ClassLoader.ALL_SCHEDULES);
        //}
        int id = rand.nextInt();
        if (scheduleChanged) {
            if (rand.nextBoolean()) {
            } else if (rand.nextBoolean()) {
            } else if (rand.nextBoolean()){
            }
        }
        scheduleChanged = false;
        return new Object();
    }

    public int getMinCreditHours() {
        return minCreditHours;
    }

    public void setMinCreditHours(int minCreditHours) {
        if (minCreditHours != this.minCreditHours){
            scheduleChanged = true;
        }
        this.minCreditHours = minCreditHours;
    }

    public int getMaxCreditHours() {
        return maxCreditHours;
    }

    public void setMaxCreditHours(int maxCreditHours) {
        if (maxCreditHours != this.maxCreditHours){
            scheduleChanged = true;
        }
        this.maxCreditHours = maxCreditHours;
    }

    public int getMinNumClasses() {
        return minNumClasses;
    }

    public void setMinNumClasses(int minNumClasses) {
        if (minNumClasses != this.minNumClasses){
            scheduleChanged = true;
        }
        this.minNumClasses = minNumClasses;
    }

    public int getMaxNumClasses() {
        return maxNumClasses;
    }

    public void setMaxNumClasses(int maxNumClasses) {
        if (maxNumClasses != this.maxNumClasses){
            scheduleChanged = true;
        }
        this.maxNumClasses = maxNumClasses;
    }

    public Object getNewClass() {
        return new Object();
    }

    public Object getOldClass() {
        return new Object();
    }

    public Object getNewSection() {
        return new Object();
    }

    public Object getOldSection() {
        return new Object();
    }
}
