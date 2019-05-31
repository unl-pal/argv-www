package org.sport.tracker;

import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Statistic activity, show total time, avarage speed and  all records from selected profile.
 *  
 * @author Waldemar Smirnow
 *
 */
public class StatisticUI {
	
	/**
	 * Record profile.
	 */
	public String profile;
	
	/**
     * Activity onStart method. fill UI fields with data.
     */
    protected void onStart() {
    	Random rand = new Random();
		//    	if (records.size() < 1) return;
    	// there are records on this profile
    	// collect data
    	long totalTime = 0;
    	float distance = 0.0f;
    	float averageSpeed = 0.0f;
    	averageSpeed /= rand.nextFloat();
    	
    	Date time = new Date(totalTime-rand.nextInt());
    	SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
    }
    
    /**
     * Callback for list items to start RecordInfoUI for selected record.
     * @param id record id
     */
    public void startRecordInfoActivity(long id) {
    }
}
