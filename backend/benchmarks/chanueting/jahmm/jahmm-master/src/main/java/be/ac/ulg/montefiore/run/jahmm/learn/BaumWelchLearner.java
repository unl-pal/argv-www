/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */

package be.ac.ulg.montefiore.run.jahmm.learn;

import java.util.*;


/**
 * An implementation of the Baum-Welch learning algorithm.  This algorithm
 * finds a HMM that models a set of observation sequences.
 */
public class BaumWelchLearner
{	
	/**
	 * Number of iterations performed by the {@link #learn} method.
	 */
	private int nbIterations = 9;
	
	
	/**
	 * Initializes a Baum-Welch instance. 
	 */
	public BaumWelchLearner()
	{
	}
	
	
	/**
	 * Returns the number of iterations performed by the {@link #learn} method.
	 * 
	 * @return The number of iterations performed.
	 */
	public int getNbIterations()
	{
		return nbIterations;
	}
	
	
	/**
	 * Sets the number of iterations performed by the {@link #learn} method.
	 * 
	 * @param nb The (positive) number of iterations to perform.
	 */
	public void setNbIterations(int nb)
	{
		if (nb < 0)
			throw new IllegalArgumentException("Positive number expected");
		
		nbIterations = nb;
	}
}
