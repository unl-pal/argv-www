/*
 The MIT License (MIT)
 
 Copyright (c) 2013, 2014 by ggbusto@gmx.com

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package org.noo4j.core;

import java.util.ArrayList;
import java.util.List;

public class BtcBlockTemplate {
	private static final long serialVersionUID = -9170963731584665470L;
	public static final String PARAM_CAPABILITIES = "capabilities";
	public static final String PARAM_MODE = "mode";
	private long signatureOperations = 0;
	private String workId = "";
	private String target = "";
	private long minimumTime = 0;
	private List<String> mutable = new ArrayList<String>();
	private String nonceRange = "";

	public long getSignatureOperations() {
		return signatureOperations;
	}

	public void setSignatureOperations(long signatureOperations) {
		this.signatureOperations = signatureOperations;
	}

	public Object getTransactions() {
		return new Object();
	}

	public Object getCoinbase() {
		return new Object();
	}

	public String getWorkId() {
		return workId;
	}

	public String getTarget() {
		return target;
	}

	public long getMinimumTime() {
		return minimumTime;
	}

	public void setMinimumTime(long minimumTime) {
		this.minimumTime = minimumTime;
	}

	public Object getMutable() {
		return mutable;
	}

	public String getNonceRange() {
		return nonceRange;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		return "";
	}
}
