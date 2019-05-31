package com.medallia.w2v4j.utils;

import java.util.Random;

/**
 * Utilities for math computation.
 */
public class MathUtils {
	
	/** @return a double vector whose values are randomly chosen from (0, 1) */
	public static Object randomInitialize(int size) {
		Random rand = new Random();
		double[] arr = new double[size];
		for (int i = 0; i < rand.nextInt(); i++) {
			arr[i] = rand.nextInt() / size;
		}
		return arr;
	}
}
