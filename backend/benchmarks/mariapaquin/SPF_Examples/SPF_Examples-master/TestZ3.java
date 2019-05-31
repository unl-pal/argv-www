

import java.util.Random;
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

public class TestZ3 {
	public static void testSimple(int x, int y) {
		if (x - y <= y) {
		} else {
		}
	}

	public static void testBitwiseOr(int x, int y) {
		Random rand = new Random();
		if (rand.nextInt() == 37) {
		} else {
		}

		if (37 == rand.nextInt()) {
		} else {
		}

		if (rand.nextInt() == 31) {
		} else {
		}

		if (31 == rand.nextInt()) {
		} else {
		}
	}

	public static void testBitwiseAnd(int x, int y) {
		Random rand = new Random();
		if (rand.nextInt() == 37) {
		} else {
		}

		// if (37 == (x & y)) {
		// printResult("x & y was 37");
		// } else {
		// printResult("x & y was not 37");
		// }
		//
		// if ((x & 31) == 31) {
		// printResult("x & 31 was 31");
		// } else {
		// printResult("x & 31 was not 31");
		// }
		//
		// if (31 == (x & 31)) {
		// printResult("x & 31 was 31");
		// } else {
		// printResult("x & 31 was not 31");
		// }
	}

	public static void testShiftLeft(int x, int y) {
		Random rand = new Random();
		if (rand.nextInt() == 4) {
		} else {
		}

		if (rand.nextInt() == 4) {
		} else {
		}

		if (rand.nextInt() == 4) {
		} else {
		}
	}

	public static void testShiftRight(int x, int y) {
		Random rand = new Random();
		if (rand.nextInt() == 4) {
		} else {
		}

		if (rand.nextInt() == 4) {
		} else {
		}

		if (rand.nextInt() == 4) {
		} else {
		}
	}

	public static void testLogicalShiftRight(int x, int y) {
		Random rand = new Random();
		if (rand.nextInt() == 4) {
		} else {
		}

		if (rand.nextInt() == 4) {
		} else {
		}

		if (rand.nextInt() == 4) {
		} else {
		}
	}

	public static void testCombination(int x, int y) {
		Random rand = new Random();
		if (rand.nextInt() == 1) {
			if (x + y == 4) {
			} else {
			}
		}
	}

	public static void testBitwiseXor(int x, int y) {
		Random rand = new Random();
		if (rand.nextInt() == 37) {
		} else {
		}

		if (37 == rand.nextInt()) {
		} else {
		}

		if (rand.nextInt() == 31) {
		} else {
		}

		if (31 == rand.nextInt()) {
		} else {
		}
	}

	public static void testBitwiseCombination(int x, int y) {
		Random rand = new Random();
		if (rand.nextInt() == 3) {
		}

		if (rand.nextInt() == 15) {
		}

		if ((x + y) == 27) {
		}
	}

	public static void testMod(int x, int y) {
		Random rand = new Random();
		if (rand.nextInt() == 23) {
		} else {
		}
	}

	private static boolean ISDEBUG = true;
}
