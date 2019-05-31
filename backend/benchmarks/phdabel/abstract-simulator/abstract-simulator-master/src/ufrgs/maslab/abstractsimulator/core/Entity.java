package ufrgs.maslab.abstractsimulator.core;

import java.io.Serializable;
import java.util.Random;

public abstract class Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * <ul>
	 * <li>id of the entity</li>
	 * </ul>
	 */
	private int id;
	
		
	/**
	 * <ul>
	 * <li>constructor generates a new id number to each entity</li>
	 * <li>id number follows an order</li>
	 * <li>there is no two entities with the same id</li>
	 * </ul>
	 */
	public Entity(){
	}
	
	/**
	 * <ul>
	 * <li>returns the id of the entity</li>
	 * </ul>
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * <ul>
	 * <li>set the id of the entity</li>
	 * </ul>
	 * @param id
	 */
	private void setId(int id) {
		this.id = id;
	}
	
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
	protected double rollDices()
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
	protected int rollDices(int maxValue)
	{
		Random rand = new Random();
		return rand.nextInt();
	}
	
	
	/**
	 * <ul>
	 * <li>returns the hashCode of the entity</li>
	 * <li>the same id</li>
	 * </ul>
	 */
	public int hashCode()
	{
		return this.id;
	}

	

}
