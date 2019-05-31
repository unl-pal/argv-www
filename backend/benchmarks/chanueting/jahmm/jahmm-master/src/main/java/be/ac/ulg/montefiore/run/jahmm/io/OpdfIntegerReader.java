/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */

package be.ac.ulg.montefiore.run.jahmm.io;

import java.io.IOException;
import java.io.StreamTokenizer;

/**
 * This class implements a {@link OpdfInteger} reader. The syntax of the
 * distribution description is the following.
 * <p>
 * The description always begins with the keyword <tt>IntegerOPDF</tt>. The
 * next (resp. last) symbol is an opening (resp. closing) bracket. Between the
 * backets is a list of numbers separated by a space. The <tt>i</tt>-th
 * number is the probability of <tt>i-1</tt>.
 * <p>
 * For example, reading <tt>IntegerOPDF [ .2 .3 .5 ]</tt> returns a
 * distribution equivalent to
 * <code>new OpdfInteger(new double[] { .2 .3 .5 })</code>.
 */
public class OpdfIntegerReader
{
	private final int nbEntries; // < 0 if number of entries is not checked.

	
	String keyword()
	{
		return "IntegerOPDF";
	}

	
	/**
	 * Implements a reader of distributions over integer observations.
	 */
	public OpdfIntegerReader()
	{
		nbEntries = -1;
	}
	

	/**
	 * Implements a reader of distributions over integer observations. The
	 * number of probabilities given is checked.
	 * 
	 * @param nbEntries
	 *            The number of entries that should be found (<i>i.e.</i> a
	 *            {@link FileFormatException} is triggered if the read
	 *            <code>opdf</code> is not such as
	 *            <code>opdf.nbEntries() == nbEntries</code> ).
	 */
	public OpdfIntegerReader(int nbEntries)
	{
		if (nbEntries <= 0)
			throw new IllegalArgumentException("Argument must be strictly "
					+ "positive");

		this.nbEntries = nbEntries;
	}
}
