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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BtcScript {
	private static final long serialVersionUID = -6396033986415109507L;
	private String asm = "";
	private String publicKey = "";
	private long requiredSignatures = 0;
	private List<String> addresses = new ArrayList<String>();

	public String getAsm() {
		return asm;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public long getRequiredSignatures() {
		return requiredSignatures;
	}

	public void setRequiredSignatures(long requiredSignatures) {
		this.requiredSignatures = requiredSignatures;
	}

	public Object getType() {
		return new Object();
	}

	public Object getAddresses() {
		return addresses;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		return "";
	}
}