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

package uberlazy;

import java.util.Random;

/**
 * @author Neha Rungta A test driver for checking the values in the equivalence
 *         classes when variables of primitive type differ in their values.
 **/

public class TestParentFieldInit {

	public void run() {
		Random rand = new Random();
		if (rand.nextBoolean()) {
			// when a primitive field reference is "used"
			// and it differs in the value/type then the partition
			// function separates the ones that are different
			if (rand.nextInt() > 10) {
			} else {
			}
			/**
			 * if(m.testElem > 10) { System.out.println("the value of Node.testElem is
			 * greater than 10++++++++++++++++++++++++++++++++++++++++"); m.print();
			 * System.out.println("########################################"); } else {
			 * System.out.println("the value is less than 10
			 * -------------------------------------------");
			 * 
			 * }
			 **/
		}
	}

	public void differentField() {
		Random rand = new Random();
		if (rand.nextBoolean()) {
			// when a primitive field reference is read
			// and it differs in the value then the partition
			// function separates the ones that are different
			if (rand.nextInt() < 5) {
			}
		}
	}

}