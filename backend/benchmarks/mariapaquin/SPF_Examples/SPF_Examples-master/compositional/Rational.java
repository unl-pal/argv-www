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

package compositional;
// when we win

// 1. simplify the constraints in the summary: we should do it
// 2. we only do one check

import java.util.Random;

public class Rational {

	private int num;
	private int den;

	public Rational() {
		num = 0;
		den = 1;
	}

	public Rational(int n, int d) {
		num = n;
		den = d;
	}

	public static int abs(int x) {
		if (x >= 0)
			return x;
		else
			return -x;
	}

	public static int abs_summary_case1(int x) {
		return x;
	}

	public static int abs_summary_case2(int x) {
		return -x;
	}

	public static int abs_summary(int x) {
		Random rand = new Random();
		int tmpa = 0;
		if (rand.nextBoolean()) {
			// summary case 1: check precondition
			if (x >= 0)
				tmpa = rand.nextInt();
			else {
			}
		} else {
			// summary case 2
			if (x < 0)
				tmpa = rand.nextInt();
			else {
			}
		}
		return tmpa;
	}

	public static int gcd(int a, int b) {
		/*
		 * int res; while (b != 0){ res = a%b; a = b; b = res;
		 * 
		 * } return abs(a);
		 */
		int c = abs(a);
		if (c == 0)
			return abs(b);

		int count = 0;
		while (b != 0) {
			count++;
			System.out.println("count " + count);
			if (count >= 4)
				assert false;
			if (c > b)
				c = c - b;
			else
				b = b - c;
		}
		return c;
	}

	/*
	 * Precondition: (b_2_SYMINT[100] - (CONST_0 - a_1_SYMINT[-100])) == CONST_0 &&
	 * (CONST_0 - a_1_SYMINT[-100]) <= b_2_SYMINT[100] && b_2_SYMINT[100] != CONST_0
	 * && (CONST_0 - a_1_SYMINT[-100]) != CONST_0 && a_1_SYMINT[-100] < CONST_0
	 * Return is: (CONST_0 - a_1_SYMINT[-100])
	 */
	public static int gcd_composition_summary_case1(int a, int b) {
		Random rand = new Random();
		if (b + a == 0 && -a <= b && b != 0 && -a != 0 && a < 0) {
			// return -a;
			int c = rand.nextInt();
			int count = 0;
			count++;
			b = b - c;
			return c;
		} else {
		}
		return 0;
	}

	/*
	 * Precondition: PC is:constraint # = 3 b_2_SYMINT[0] == CONST_0 && (CONST_0 -
	 * a_1_SYMINT[-88]) != CONST_0 && a_1_SYMINT[-88] < CONST_0 Return is: (CONST_0
	 * - a_1_SYMINT[-88])
	 */
	public static int gcd_composition_summary_case2(int a, int b) {
		Random rand = new Random();
		if (b == 0 && -a != 0 && a < 0) {
			// return -a;
			int c = rand.nextInt();
			int count = 0;
			return c;
		} else {
		}
		return 0;
	}

	/*
	 * Precondition: PC is:constraint # = 3 b_2_SYMINT[-88] < CONST_0 &&
	 * a_1_SYMINT[0] == CONST_0 && a_1_SYMINT[0] >= CONST_0 Return is: (CONST_0 -
	 * b_2_SYMINT[-88])
	 */
	public static int gcd_composition_summary_case3(int a, int b) {
		Random rand = new Random();
		if (b < 0 && a == 0 && a >= 0) {
			// return -b;
			int c = rand.nextInt();
			b = rand.nextInt();
			return b;
		} else {
		}
		return 0;
	}

	/*
	 * Precondition: PC is:constraint # = 3 b_2_SYMINT[0] >= CONST_0 &&
	 * a_1_SYMINT[0] == CONST_0 && a_1_SYMINT[0] >= CONST_0 Return is: b_2_SYMINT[0]
	 */
	public static int gcd_composition_summary_case4(int a, int b) {
		Random rand = new Random();
		if (b >= 0 && a == 0 && a >= 0) {
			// return b;
			int c = rand.nextInt();
			b = rand.nextInt();
			return b;
		} else {
		}
		return 0;
	}

	public static int gcd_composition(int a, int b) {
		int c = abs_summary(a);
		if (c == 0) {
			b = abs_summary(b);
			return b;
		}
		int count = 0;
		while (b != 0) {
			count++;
			if (count >= 2)
				assert false;
			if (c > b)
				c = c - b;
			else
				b = b - c;
		}
		return c;
	}

	public void simplify() {
		Random rand = new Random();
		int gcd = rand.nextInt();
		num = num / gcd;
		den = den / gcd;
	}

	public float toFloat() {
		Random rand = new Random();
		return rand.nextFloat();
	}

	public String toString() {
		if (den != 1)
			return num + "/" + den;
		else
			return "";
	}

	public static void test(int i1, int i2, int i3, int i4, int i5, int i6) {
	}

}
