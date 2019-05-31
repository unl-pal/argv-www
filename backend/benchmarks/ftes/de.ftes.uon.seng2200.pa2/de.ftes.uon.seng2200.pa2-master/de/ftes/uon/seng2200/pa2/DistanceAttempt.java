package de.ftes.uon.seng2200.pa2;


/**
 * A distance attempt wraps a distance achieved by an athlete in a competition.
 * Apart from the {@link #distance} value, it holds the {@link #numberOfAttempt
 * number of the attempt} that the athlete achieved this distance on during the
 * competition.
 * 
 * A foul attempt is encoded as distance of {@code 0}, an attempt not yet made
 * is encoded as {@link Double#MIN_VALUE}.
 * 
 * @author Fredrik Teschke (3228760)
 */
public class DistanceAttempt implements Comparable<DistanceAttempt> {
	private final boolean hasBeenMade;
	private final int numberOfAttempt;
	private final double distance;

	public DistanceAttempt(int numberOfAttempt) {
		this.numberOfAttempt = numberOfAttempt;
		this.hasBeenMade = false;
		this.distance = Double.MIN_VALUE;
	}

	public DistanceAttempt(int numberOfAttempt, double distance) {
		this.numberOfAttempt = numberOfAttempt;
		this.hasBeenMade = true;
		this.distance = distance;
	}

	public int getNumberOfAttempt() {
		return numberOfAttempt;
	}

	/**
	 * @return {@code 0} for a foul, {@link Double#MIN_VALUE} if attempt not yet made
	 */
	public double getDistance() {
		return distance;
	}
	
	public boolean hasBeenMade() {
		return hasBeenMade;
	}
	
	public boolean isFoul() {
		return distance == 0;
	}

	@Override
	/**
	 * Order a greater distance before a lower one. An attempt not yet made is ordered before a foul attempt.
	 * @return {@code -1} if {@code this.distance > o.distance}, 0 if they are equal and 1 otherwise.
	 */
	public int compareTo(DistanceAttempt other) {
		return -Double.compare(distance, other.distance);
	}

	@Override
	public String toString() {
		if (! hasBeenMade) {
			return "   U   ";
		} else if (isFoul()) {
			return "   X   ";
		} else {
			return String.format("%7.2f", distance);
		}
	}
}
