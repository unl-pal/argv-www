package ufrgs.maslab.abstractsimulator.core.simulators;

import java.util.Random;

public abstract class DefaultSimulation {

	private Integer difficulty = 6;
	
	private Integer modifier;
	
	/**
	 * <ul>
	 * <li>used to roll dices for actions and events</li>
	 * <li>actions are probabilistic</li>
	 * <li>each entity has dices</li>
	 * </ul>
	 */
	private Random dices = new Random();
	
	/**
	 * <ul>
	 * <li>d100 dice</li>
	 * <li>returns [0,1] probability</li>
	 * </ul>
	 * @return
	 */
	private double rollDices()
	{
		Random rand = new Random();
		return rand.nextInt();
	}
	
	/**
	 * <ul>
	 * <li>d<maxValue> dice</li>
	 * <li>returns a random number given the parameter maxValue</li>
	 * <li>returns [0,maxValue)</li>
	 * </ul>
	 * @param maxValue
	 * @return
	 */
	private int rollDices(int maxValue)
	{
		Random rand = new Random();
		return rand.nextInt();
	}
	
	public Object getDifficulty() {
		return difficulty;
	}

	public Object getModifier() {
		return modifier;
	}
			
	
	
	
	
}
