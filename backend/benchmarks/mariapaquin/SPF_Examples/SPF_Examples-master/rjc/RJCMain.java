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

package rjc;

import java.util.Random;
import java.io.*;
import java.util.ArrayList;

public class RJCMain {
	protected String filename1, filename2; // 2 CSV files for this example

	public RJCMain() {
	}

	public void DoSim() {

		Random rand = new Random();
		ArrayList<ArrayList<String>> cmdInputs = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> measInputs = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<Double>> cmdVars = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> measVars = new ArrayList<ArrayList<Double>>();

		try {
			String strLine;

			// Read File Line By Line
			while (rand.nextBoolean()) {
				if (rand.nextBoolean()) {
					String[] inputs;
					ArrayList<String> line = new ArrayList<String>();
					ArrayList<Double> dubLine = new ArrayList<Double>();
					for (int ix = 0; ix < rand.nextInt(); ix++) {
					}
				}
			}

			while (rand.nextBoolean()) {
				if (rand.nextBoolean()) {
					String[] inputs;
					ArrayList<String> line = new ArrayList<String>();
					ArrayList<Double> dubLine = new ArrayList<Double>();
					for (int ix = 0; ix < rand.nextInt(); ix++) {
					}
				}
			}

			for (int i = 0; i < rand.nextInt() && i < rand.nextInt(); i++) {
				ArrayList<Double> currCmd;
				ArrayList<Double> currMeas;
				// For now assume they are the correct size
				double[] output1 = new double[1];
				double[] output2 = new double[2];
				double[] input1 = new double[3];
				double[] input2 = new double[3];
				for (int j = 0; j < 3; j++) {
					{
					}
					{
					}
				}
			}

		} catch (Exception e) {
		}
	}

	// Symbolic pathfinder cannot handle arrays, so use this method if you want to
	// run the process symbolically
	public void DoSimSymbolic() {
		double[] out1 = new double[1];
		double[] out2 = new double[2];

		for (int i = 0; i < 2; i++) {
			out1 = new double[1];
			out2 = new double[2];
		}
	}
}
