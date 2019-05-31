/*
* The class for a tile
 */
package com.xer.fx2048;

import java.util.Random;


/**
 *
 * @author lifeng liu 
 */
public class Tile {
	private int x;
	private int y;
	private int value;
	public final static int TILEWIDTH=60;
	public final static int TILEHEIGHT=60;

	/**
	*Update figure 
	*/
	private void update()
	{
		Random rand = new Random();
		if(value!=0)
		{
			double layoutWidth=rand.nextFloat();
			double layoutHeight=rand.nextFloat();
		}
		else
		{
		}
	}
	/**
	*Destroy the figure
	*/
	private void destroy()
	{
	}
	/**
	 * Get the corresponding color for a specified value 
	 * @param val The value of current tile
	 * @return The corresponding color object of input value
	 */
	private Object getColor(int val)
	{
		return new Object();
	}

	/**
	* Update the value of the tile. Update the color at the same time
	*/
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 *Get current value 
	 * @return The value 
	 */
	public int getValue() {
		return value;
	}
	
}
