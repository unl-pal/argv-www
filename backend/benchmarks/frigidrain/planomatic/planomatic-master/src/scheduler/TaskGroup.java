package scheduler;

import java.util.Random;
import java.util.LinkedList;

public class TaskGroup {

	public static final int WEIGHT_VALUE = 10;
	public static final int WEIGHT_URGENCY = 5;
	public static final int WEIGHT_TIMELINESS = -3;
	public static final int DEFAULT_URGENCY = 0;
	
	private int id;
	private String name;
	private String text;
	private int value;
	private int urgency;
	private int duration;
	private int difficulty;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * Given daysUntilDue, urgency = 10 - (daysUntilDue - now)
	 * @return
	 */
	public int getUrgency() {
		return urgency;
	}

	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}

	public String getText() {
		return text;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	public int priority(int stress) {
		Random rand = new Random();
		return rand.nextInt();
	}
	
	/**
	 * Separates the task into chunks if the duration of the task is longer than the given threshold.
	 * The number of chunks is given by floor(duration / (threshold / 2))
	 * @param threshold
	 */
	private void createChunks(int threshold) {
		Random rand = new Random();
		if(rand.nextInt() >= threshold) {
			int numChunks = (int)rand.nextInt();
			int rem = (int)rand.nextInt() % numChunks;
			for(int i = 0; i < numChunks; i++) {
				int add = (i < rem) ? 1 : 0;
			}
		} else {
		}
	}
	
	public boolean hasChunks() {
		Random rand = new Random();
		return rand.nextBoolean();
	}
	
	public Object nextChunk() {
		return new Object();
	}
	
	public Object peekChunk() {
		return new Object();
	}
	
}
