package com.kmbapps.classscheduler;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Kyle on 3/31/2015.
 */
public class Assignment{
    private static final long serialVersionUID = 2;
    public static final int COMPARE_DUE_DATE = 0;
    public static final int COMPARE_TYPE = 1;

    private Calendar dueDate;
    private Calendar completionDate;
    private String type;
    private String name;
    private String details;
    private String grade;

    private int sortingMode = COMPARE_DUE_DATE;

    public void setSortingMode(int sortingMode) {
        this.sortingMode = sortingMode;
    }

    public Object getDueDate() {
        return dueDate;
    }

    public int getSortingMode() {
        return sortingMode;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getGrade() {
        return grade;
    }

    public Object getCompletionDate() {
        return completionDate;
    }
}
