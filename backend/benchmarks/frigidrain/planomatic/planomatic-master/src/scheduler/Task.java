package scheduler;

import java.util.Random;

public class Task{
	
	public static final int WEIGHT_VALUE = 10;
	public static final int WEIGHT_URGENCY = 5;
	public static final int WEIGHT_TIMELINESS = -3;
	public static final int DEFAULT_URGENCY = 0;
	
	public int priority(int stress) {
		Random rand = new Random();
		return rand.nextInt();
	}

	public Object getParent() {
		return new Object();
	}

	public String getName() {
		return "";
	}

	public int getValue() {
		Random rand = new Random();
		return rand.nextInt();
	}

	public void setValue(int value) {
	}

	/**
	 * Given daysUntilDue, urgency = 10 - (daysUntilDue - now)
	 * @return
	 */
	public int getUrgency() {
		Random rand = new Random();
		return rand.nextInt();
	}

	public void setUrgency(int urgency) {
	}

	public String toString() {
		return "";
	}
	
}
