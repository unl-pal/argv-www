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

package symbolicheap;

import java.util.Random;

public class NodeBuggy {

	int elem;
	public NodeBuggy(int x) {
		elem = x;
	}

	void addGCIssue() {

		Random rand = new Random();
		NodeBuggy right;
		if (rand.nextBoolean()) {
		}

	}

	void addSimple3() {
		Random rand = new Random();
		int depth = 0;
		while (rand.nextBoolean()) {
			if (rand.nextBoolean()) {
			} else {
			}
			depth++;
			if (depth == 2)
				return;
		}
	}

	void addSimple3b() {
		Random rand = new Random();
		int depth = 2;
		while (rand.nextBoolean() && depth > 0) {
			if (rand.nextBoolean()) {
			} else {
			}
			depth--;
		}
	}

	void addSimple4() {
		Random rand = new Random();
		if (rand.nextBoolean()) {
			if (rand.nextBoolean()) {
			} else {
			}
		}
		if (rand.nextBoolean()) {
			if (rand.nextBoolean()) {
			} else {
			}
		}
	}

	public static void runTest(int x) {
		Random rand = new Random();
		if (rand.nextBoolean()) {
		}
		// Debug.printSymbolicRef(X, "node = ");
	}

}
