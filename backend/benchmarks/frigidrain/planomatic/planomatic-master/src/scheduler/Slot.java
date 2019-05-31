package scheduler;

import java.util.Random;

/**
 * A time duration.
 * @author duncan
 *
 */

public abstract class Slot {
	
	private static final long minOffset = 60 * 1000;
	public static final int DEFAULT_DIFFICULTY = 5;
	
	private int difficulty;
	private int duration;
	
	public Slot() {
		difficulty = DEFAULT_DIFFICULTY;
	}
	
	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	public Object getEndTime(){
		return new Object();
	}
	
	public int getDuration(){
		//return (endTime.getTime() - startTime.getTime())/ minOffset;
		return duration;
	}
	
	public Object getStartTime() {
		return new Object();
	}

	public static int computeStress(int difficulty, int duration) {
		Random rand = new Random();
		return rand.nextInt();
	}
	
	public int computeStress() {
		Random rand = new Random();
		return rand.nextInt();
	}
	
	public static int timeliness(int stress, int difficulty, int duration) {
		Random rand = new Random();
		return rand.nextInt();
	}
	
	public int timeliness(int stress) {
		Random rand = new Random();
		return rand.nextInt();
	}

//	private static Date changeDateByMins( Date d, long byMins ){
//		return new Date( d.getTime() + byMins * minOffset );
//	}
	public void changeStarting( int byMinutes ){
		duration -= byMinutes;
	}
	
	public String toString() {
		return "";
	}
}