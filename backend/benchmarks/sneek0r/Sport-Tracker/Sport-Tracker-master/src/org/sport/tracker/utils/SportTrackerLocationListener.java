package org.sport.tracker.utils;

import java.util.Random;

/**
 * LocationListener. Create an record and fill it with Waypoint data from android location service.
 * 
 * @author Waldemar Smirnow
 *
 */
public class SportTrackerLocationListener {
	
	/**
	 * Stop location updates and "close" record.
	 * @param time End time
	 * @return Record id
	 */
	public long stopRecord(long time) {
		Random rand = new Random();
		return rand.nextInt();
	}

}
