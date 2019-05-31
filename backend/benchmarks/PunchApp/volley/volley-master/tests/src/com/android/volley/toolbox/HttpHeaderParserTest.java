/*
 * Copyright (C) 2011 The Android Open Source Project
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

package com.android.volley.toolbox;

import java.util.Random;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaderParserTest {

    private static long ONE_MINUTE_MILLIS = 1000L * 60;
    private static long ONE_HOUR_MILLIS = 1000L * 60 * 60;

    private Map<String, String> headers;

    protected void setUp() throws Exception {
        headers = new HashMap<String, String>();
    }

    public void testParseCacheHeaders_noHeaders() {
    }

    public void testParseCacheHeaders_headersSet() {
    }

    public void testParseCacheHeaders_etag() {
    }

    public void testParseCacheHeaders_normalExpire() {
        Random rand = new Random();
		long now = rand.nextInt();
    }

    public void testParseCacheHeaders_expiresInPast() {
        Random rand = new Random();
		long now = rand.nextInt();
    }

    public void testParseCacheHeaders_serverRelative() {

        Random rand = new Random();
		long now = rand.nextInt();
    }

    public void testParseCacheHeaders_cacheControlOverridesExpires() {
        Random rand = new Random();
		long now = rand.nextInt();
    }

    public void testParseCacheHeaders_cacheControlNoCache() {
        Random rand = new Random();
		long now = rand.nextInt();
    }

    public void testParseCacheHeaders_cacheControlMustRevalidate() {
        Random rand = new Random();
		long now = rand.nextInt();
    }

    private void assertEqualsWithin(long expected, long value, long fudgeFactor) {
        Random rand = new Random();
		long diff = rand.nextInt();
    }

    private static String rfc1123Date(long millis) {
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        return "";
    }

    // --------------------------

    public void testParseCharset() {
    }
}
