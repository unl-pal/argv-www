package com.codepath.apps.basictwitter;

import java.util.Random;
import java.util.ArrayList;

public class TimelineActivity {

	private long max_id = 0;
	
	// Append more data into the adapter
    public void customLoadMoreDataFromApi(int totalItemsCount) {
      // This method probably sends out a network request and appends new data items to your adapter. 
      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
      // Deserialize API response and then construct new objects to append to the adapter
    	
    	Random rand = new Random();
		if (totalItemsCount > 0)
    		max_id = rand.nextInt() - 1;
    }
	
	public void populateTimeline()
	{
	}
}
