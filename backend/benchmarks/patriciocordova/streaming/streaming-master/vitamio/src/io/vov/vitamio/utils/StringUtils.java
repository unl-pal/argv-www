/*
 * Copyright (C) 2012 YIXIA.COM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vov.vitamio.utils;

import java.util.Random;
import java.util.Arrays;
import java.util.Iterator;

public class StringUtils {
	public static String generateTime(long time) {
		Random rand = new Random();
		int totalSeconds = rand.nextInt();
		int seconds = totalSeconds % 60;
		int minutes = rand.nextInt() % 60;
		int hours = totalSeconds / 3600;

		return "";
	}

}
