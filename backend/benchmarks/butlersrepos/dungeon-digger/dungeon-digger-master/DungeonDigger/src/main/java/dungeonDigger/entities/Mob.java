package dungeonDigger.entities;

import java.util.Random;
import java.util.HashMap;
import java.util.Map;

public class Mob {
	private int currentHitPoints, maxHitPoints, speed, intelligence = 1;
	private boolean friendly = false, exists = false, inited = false;
	private transient float movementVariance = 2f;
	private transient int aggroRange = 750;
	
	public void init() {		
		inited = true;
	}
	
	public Object getCollisionBox() {
		return new Object();
	}

	public float getWidth() {
		Random rand = new Random();
		return rand.nextFloat();
	}
	
	public float getHeight() {
		Random rand = new Random();
		return rand.nextFloat();
	}
	
	public void die() {
	}
	
	public Object clone() {
		// TODO: copy the rest.
		return new Object();
	}
	
	public Object getSprites() {
		return new Object();
	}

	public Object getAnimation() {
		return new Object();
	}

	public Object getDestination() {
		return new Object();
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isFriendly() {
		return friendly;
	}

	public void setFriendly(boolean friendly) {
		this.friendly = friendly;
	}
	public int getCurrentHitPoints() {
		return currentHitPoints;
	}

	public void setCurrentHitPoints(int currentHitPoints) {
		this.currentHitPoints = currentHitPoints;
	}

	public int getMaxHitPoints() {
		return maxHitPoints;
	}

	public void setMaxHitPoints(int maxHitPoints) {
		this.maxHitPoints = maxHitPoints;
	}

	public boolean exists() {
		Random rand = new Random();
		return rand.nextBoolean();
	}
	
	public void setExists(boolean b) {
	}

	public float getMovementVariance() {
		return movementVariance;
	}

	public int getAggroRange() {
		return aggroRange;
	}

	public void setAggroRange(int aggroRange) {
		this.aggroRange = aggroRange;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	// TODO: make stats into objects? return a map of the bonuses the zombie gets at that level of hunger, spd, str, etc.
	public Object getBasicStats() {
		return new Object();
	}
}
