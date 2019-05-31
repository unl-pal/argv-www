package ufrgs.maslab.abstractsimulator.variables;

import java.util.Random;


public class FireFighter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4608281129356235256L;
	
	/**
	 * <ul>
	 * <li>Default constructor of the fire fighter human</li>
	 * <li>Attributes</li>
	 * <li>Strength</li>
	 * <li>Dexterity</li>
	 * <li>Stamina</li>
	 * <li>Charisma</li>
	 * <li>Appearance</li>
	 * <li>Leadership</li>
	 * <li>Intelligence</li>
	 * <li>Reasoning</li>
	 * <li>Perception</li>
	 * <li>Will</li>
	 * <li>HP</li>
	 * </ul>
	 * <br/>
	 * <ul>
	 * <li>Skills</li>
	 * <li>Fire Fighting</li>
	 * <li>Advantages</li>
	 * </ul>
	 * 
	 */
	public FireFighter()
	{
		super();
	}
	
	/**
	 * ability to extinguish fire from buildings
	 */
	private int fireFighting;
	
	public void configureFireFighterSkills(){
	}
	
	public int getFireFighting() {
		return fireFighting;
	}

	private void setFireFighting() {
		Random rand = new Random();
		this.fireFighting = 1 + rand.nextInt();
	}
	
	public void act(int time){
	}

}
