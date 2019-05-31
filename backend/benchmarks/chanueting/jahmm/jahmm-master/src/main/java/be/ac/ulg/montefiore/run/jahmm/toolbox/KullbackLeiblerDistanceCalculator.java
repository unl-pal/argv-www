/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */

package be.ac.ulg.montefiore.run.jahmm.toolbox;

import java.util.List;


/**
 * Computes the distance between HMMs.
 * <p>
 * The distance metric is similar to the Kullback-Leibler distance defined
 * on distributions.  More information can be found in
 * <i>A Probabilistic Distance Measure For HMMs</i> by
 * <i>Rabiner</i> and <i>Juang</i> (AT&T Technical Journal, vol. 64, Feb. 1985,
 * pages 391-408).
 * <p>
 * This distance measure is not symetric: <code>distance(hmm1, hmm2)</code>
 * is not necessary equal to <code>distance(hmm2, hmm1)</code>.  To get a
 * symetric distance definition, compute
 * <code>(distance(hmm1, hmm2) + distance(hmm2, hmm1)) / 2</code>.
 */
public class KullbackLeiblerDistanceCalculator
{	
	private int sequencesLength = 1000;
	private int nbSequences = 10;
	
	
	/**
	 * Returns the number of sequences generated to estimate a distance.
	 * 
	 * @return The number of generated sequences.
	 */
	public int getNbSequences()
	{
		return nbSequences;
	}


	/**
	 * Sets the number of sequences generated to estimate a distance.
	 * 
	 * @param nb The number of generated sequences.
	 */
	public void setNbSequences(int nb)
	{
		this.nbSequences = nb;
	}
	
	
	/**
	 * Returns the length of sequences generated to estimate a distance.
	 * 
	 * @return The sequences length.
	 */
	public int getSequencesLength()
	{
		return sequencesLength;
	}


	/**
	 * Sets the length of sequences generated to estimate a distance.
	 * 
	 * @param length The sequences length.
	 */
	public void setSequencesLength(int length)
	{
		this.sequencesLength = length;
	}
}
