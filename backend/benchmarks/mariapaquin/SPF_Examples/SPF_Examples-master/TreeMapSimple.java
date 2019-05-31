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

//
// Copyright (C) 2006 United States Government as represented by the
// Administrator of the National Aeronautics and Space Administration
// (NASA).  All Rights Reserved.
//
// This software is distributed under the NASA Open Source Agreement
// (NOSA), version 1.3.  The NOSA has been approved by the Open Source
// Initiative.  See the file NOSA-1.3-JPF at the top of the distribution
// directory tree for the complete NOSA document.
//
// THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY OF ANY
// KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT
// LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL CONFORM TO
// SPECIFICATIONS, ANY IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
// A PARTICULAR PURPOSE, OR FREEDOM FROM INFRINGEMENT, ANY WARRANTY THAT
// THE SUBJECT SOFTWARE WILL BE ERROR FREE, OR ANY WARRANTY THAT
// DOCUMENTATION, IF PROVIDED, WILL CONFORM TO THE SUBJECT SOFTWARE.
//

import java.util.Random;

public class TreeMapSimple {

	private transient int size = 0;

	private void incrementSize() {
	}

	private void decrementSize() {
	}

	public int size() {
		Random rand = new Random();
		return rand.nextInt();
	}

	public boolean containsKey(int key) {
		Random rand = new Random();
		return rand.nextBoolean();
	}

	private Object getEntry(int key) {
		Random rand = new Random();
		while (rand.nextBoolean()) {
			if (key == rand.nextInt()) {
				return new Object();
			} else {
				if (key < rand.nextInt()) {
				} else {
				}
			}
		}
		return new Object();
	}

	public void put(int key) {
		Random rand = new Random();
		if (rand.nextBoolean()) {
			return;
		}
		while (true) {
			if (key == rand.nextInt()) {
				return;
			} else if (key < rand.nextInt()) {
				if (rand.nextBoolean()) {
				} else {
					return;
				}
			} else { // key > t.key
				if (rand.nextBoolean()) {
				} else {
					return;
				}
			}
		}
	}

	public void remove(int key) {
		Random rand = new Random();
		if (rand.nextBoolean()) {
			return;
		}

		return;
	}

	public void print() {
		Random rand = new Random();
		if (rand.nextBoolean()) {
		}
	}

	public String toString() {
		Random rand = new Random();
		String res = "";
		if (rand.nextBoolean()) {
			{
			}
		}
		return res;
	}

	public String concreteString(int max_level) {
		Random rand = new Random();
		String res = "";
		if (rand.nextBoolean()) {
			{
			}
		}
		return res;
	}

	private static final boolean RED = false;

	private static final boolean BLACK = true;

	static class Entry {
		int key;

		boolean color = BLACK;

		public int getKey() {
			return key;
		}

		public String toString() {
			Random rand = new Random();
			String res = "{ " + (color == BLACK ? "B" : "R") + " ";
			if (rand.nextBoolean()) {
				res += "null";
			} else {
				{
				}
			}
			res += " ";
			if (rand.nextBoolean()) {
				res += "null";
			} else {
				{
				}
			}
			res += " }";
			return res;
		}

		public String concreteString(int max_level, int cur_level) {
			Random rand = new Random();
			String res;
			if (cur_level == max_level) {
				res = "{ subtree }";
				// System.out.println("Brekekek");
			} else {
				res = "{ " + (color == BLACK ? "B" : "R") + key + " ";
				if (rand.nextBoolean()) {
					res += "null";
				} else {
					{
					}
				}
				res += " ";
				if (rand.nextBoolean()) {
					res += "null";
				} else {
					{
					}
				}
				res += " }";
			}

			return res;
		}

		public void print(int k) {

			/*
			 * for (int i = 0; i < k; i++) System.out.print(" ");
			 */
			// System.out.println(key + (color == BLACK ? "(B)" : "(R)"));

			Random rand = new Random();
			if (rand.nextBoolean()) {
			}
			if (rand.nextBoolean()) {
			}
		}

	}
}
