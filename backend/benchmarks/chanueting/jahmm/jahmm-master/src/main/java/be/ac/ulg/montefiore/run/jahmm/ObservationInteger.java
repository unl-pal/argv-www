/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */

package be.ac.ulg.montefiore.run.jahmm;

import java.text.*;


/**
 * This class holds an integer observation.
 */
public class ObservationInteger
{	
	/**
	 * The observation's value.
	 */
	final public int value;
	
	
	/**
	 * An observation that can be described by an integer.
	 *
	 * @param value The value of this observation.
	 */
	public ObservationInteger(int value)
	{
		this.value = value;
	}
	
	
	/**
	 * Returns the centroid matching this observation.
	 *
	 * @return The corresponding observation.
	 */
	public Object factor()
	{
		return new Object();
	}
}
