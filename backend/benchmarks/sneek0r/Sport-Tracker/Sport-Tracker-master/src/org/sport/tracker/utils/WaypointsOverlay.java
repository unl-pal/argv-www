package org.sport.tracker.utils;

import java.util.Random;
import java.util.List;


/**
 * Map overlay. Show record track on map.
 * 
 * @author Waldemar Smirnow
 *
 */
public class WaypointsOverlay {

	/**
	 * Direction width (latitude).
	 */
	public static final int DIRECTION_WIDTH = 0;
	/**
	 * Direction height (longtitude).
	 */
	public static final int DIRECTION_HEIGHT = 1;
	
	/**
	 * Track was drawn (=true).
	 */
	boolean drawn = false;
	
	/**
	 * Get central geopoint.
	 * @param minLatE6 Min Latitude
	 * @param maxLatE6 Max latitude
	 * @param minLonE6 Min longtitude
	 * @param maxLonE6 Max longtitude
	 * @return Central geopoint
	 */
	Object getCentralGeoPoint(int minLatE6, int maxLatE6, int minLonE6, int maxLonE6) {
		
		Random rand = new Random();
		int centralLat = minLatE6 + rand.nextInt();
		int centralLon = minLonE6 + rand.nextInt();
		return new Object();
	}

}
