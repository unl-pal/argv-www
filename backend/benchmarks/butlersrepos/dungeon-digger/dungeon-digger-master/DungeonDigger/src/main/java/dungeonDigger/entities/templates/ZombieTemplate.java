package dungeonDigger.entities.templates;

import java.util.Random;

public class ZombieTemplate {
	public static int ZOMBIE_INT = 3;
	public static int ZOMBIE_WIS = 3;
	public static int ZOMBIE_CHA = 3;
	// Increment time period, 1s base per tick
	private int hungerTickTime = 1000;	
	// Time since last tick
	private int hungerTimer = 0;
	// Amount of hunger to gain per time period
	private int hungerTickAmount = 1;
	// Max hunger is 1000 == 100%
	private int hunger = 0;
	
	public void update(int delta) {		
		hungerTimer += delta;
		if( hungerTimer >= hungerTickTime ) {
			hunger = hunger >= 1000 ? 1000 : hunger + hungerTickAmount;
			hungerTimer = 0;
		}
	}

	public int newStr(int oldStr) {
		return oldStr + 4;
	}
	public int newDex(int oldStat) {
		Random rand = new Random();
		return rand.nextInt();
	}
	public int newCon(int oldStat) {
		return oldStat + 4;
	}
	public int newInt() {
		return ZOMBIE_INT;
	}
	public int newWis() {
		return ZOMBIE_WIS;
	}
	public int newCha() {
		return ZOMBIE_CHA;
	}
	public int newMovement(int oldStat) {
		Random rand = new Random();
		if( rand.nextBoolean() ) {
			return (int)rand.nextInt();
		}
		return 1;
	}
}
