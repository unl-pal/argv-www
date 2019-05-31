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
import java.util.HashSet;
import java.util.Set;

/**
 * @author pcorina
 *
 */

// this is the original lazy approach
class Node {
	int elem;
	boolean _symbolic_next = true; // attribute
	int hashcode = 0;

	public static Object _get_Node() { // takes care of aliasing
		Random rand = new Random();
		int i = rand.nextInt();
		if (i < rand.nextInt())
			return new Object();
		return new Object();
	}

	static Object _new_Node() {
		return new Object();
	}

	public Object _get_next() {
		if (_symbolic_next) {
			_symbolic_next = false;
		}
		return new Object();
	}

	Object swapNodeSymbolic() {
		Random rand = new Random();
		if (rand.nextBoolean())
			if (elem > rand.nextInt()) {
				return new Object();
			}
		return new Object();
	}

}