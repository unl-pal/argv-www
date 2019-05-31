/*
 * Copyright (C) 2014, United States Government, as represented by the
 * Administrator of the National Aeronautics and Space Administration.
 * All rights reserved.
 *
 * Symbolic Pathfinder (jpf-symbc) is licensed under the Apache License, 
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0. 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package sequences;

import java.lang.reflect.*;
import java.text.*;
import java.util.Random;

/**
 * @author Mithun Acharya Loosely based on TaoSymbolicDriverForBST Tried to
 *         remove the necessity to enumerate m1, m2... and s1, s2, ... in
 *         TaoSymbolicDriverForBST Currently, no reflection Currently, its only
 *         for BST.
 */
public class MethodSequenceGeneratorTao {

	// Constructor
	public MethodSequenceGeneratorTao() {
	}

	// Name of the class for which method sequences are to be generated
	private String className;
	// Number of public methods
	private int numPublicMethods;
	// sequence length?
	private int sequenceLength = 0;
	// range
	private int range = 0;

	/*
	 * Converts the decimalNumber to base-b with numDigits digits and stores it in
	 * array digitsArray
	 */
	public static void shred(int decimalNumber, int[] digitsArray, int b, int numDigits) {
		int quotient = 1;
		int remainder = 0;
		int count = 0;

		// Start shredding
		quotient = decimalNumber;
		for (int i = 0; i < numDigits; i++) {
			count = 0;
			while (quotient >= b) {
				count++;
				quotient = quotient - b;
			}
			remainder = quotient;
			quotient = count;
			digitsArray[i] = remainder;
		}
	}

	// precondition currently hardcoded
	// numSequences < P^S and input < R^P
	public void methodSequenceDriver(int numSequences, int input) {
		// loop counters
		int i;

		int[] numSequencesDigits = new int[sequenceLength];
		int[] inputDigits = new int[range];

		int P, R, S;
		P = numPublicMethods;
		S = sequenceLength;
		R = range;

		for (i = 0; i < sequenceLength; i++) {
		}
	}
}