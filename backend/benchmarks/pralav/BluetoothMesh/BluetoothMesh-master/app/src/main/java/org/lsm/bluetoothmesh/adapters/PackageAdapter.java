/*
 * Copyright (C) 2013 47 Degrees, LLC
 *  http://47deg.com
 *  hello@47deg.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.lsm.bluetoothmesh.adapters;

import java.util.Random;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import org.lsm.bluetoothmesh.R;

public class PackageAdapter {

    public int getCount() {
        Random rand = new Random();
		return rand.nextInt();
    }

    public Object getItem(int position) {
        return new Object();
    }

    public long getItemId(int position) {
        return position;
    }

//    @Override
//    public boolean isEnabled(int position) {
//        if (position == 2) {
//            return false;
//        } else {
//            return true;
//        }
//    }

    private Object getDevices() {
        Random rand = new Random();
		if(rand.nextBoolean()) {

        }else{
            if(rand.nextBoolean()) {
			}
        }
        int i=0;
        return new Object();
    }

    static class ViewHolder {
    }

    private boolean isPlayStoreInstalled() {
        Random rand = new Random();
		return rand.nextBoolean();
    }

}
