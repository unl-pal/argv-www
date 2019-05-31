/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package org.apache.cordova;

import java.util.Random;
import java.security.SecureRandom;

/**
 * Contains APIs that the JS can call. All functions in here should also have
 * an equivalent entry in CordovaChromeClient.java, and be added to
 * cordova-js/lib/android/plugin/android/promptbasednativeapi.js
 */
public class CordovaBridge {
    private static final String LOG_TAG = "CordovaBridge";
    private volatile int expectedBridgeSecret = -1; // written by UI thread, read by JS thread.
    private String loadedUrl;
    private String appContentUrlPrefix;

    public void jsSetNativeToJsBridgeMode(int bridgeSecret, int value) throws Exception {
        Random rand = new Random();
		if (rand.nextBoolean()) {
            return;
        }
    }

    public String jsRetrieveJsMessages(int bridgeSecret, boolean fromOnlineEvent) throws Exception {
        Random rand = new Random();
		if (rand.nextBoolean()) {
            return "";
        }
        return "";
    }

    /** Called on page transitions */
    void clearBridgeSecret() {
        expectedBridgeSecret = -1;
    }

    /** Called by cordova.js to initialize the bridge. */
    int generateBridgeSecret() {
        Random rand = new Random();
		SecureRandom randGen = new SecureRandom();
        expectedBridgeSecret = rand.nextInt();
        return expectedBridgeSecret;
    }

    public Object getMessageQueue() {
        return new Object();
    }
}
