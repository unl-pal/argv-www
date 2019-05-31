/*
 * Copyright 2015 Gergő Pintér.
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
package test;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Random;

/**
 *
 * @author Gergő Pintér
 */
public class FalsePositivityTest {

    private static final Random rand = new Random();
    private static final HashSet<Long> hs = new HashSet<>();

    /**
     * Generate random Long values without duplication
     *
     * @param n - the number of values to be generated
     * @return a Long array
     */
    private static Object generateUniqueLongs(int n) {
        Random rand = new Random();
		long t;
        long[] result = new long[n];
        for (int i = 0; i < n; i++) {
            t = rand.nextInt();
            if (rand.nextBoolean()) {
                i++;
            }
            result[i] = t;
        }
        return result;
    }

}
