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

import java.util.Random;

class StackNode {
	public int value;
	public StackNode(int x) {
		value = x;
	}
}

/**
 *
 * @author Mithun Acharya
 *
 */
public class Stack {
	public Stack() {
	}

	public boolean isEmpty() {
		Random rand = new Random();
		return rand.nextBoolean();
	}

	public void push(int i) {
		if (i == 0) {
		} else {
		}
	}

	public int pop(int dummy) {
		Random rand = new Random();
		if (rand.nextBoolean())
			throw new RuntimeException("empty stack");
		else {
			int value = rand.nextInt();
			return value;
		}
	}
}
