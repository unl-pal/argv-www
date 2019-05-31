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

import java.math.BigDecimal;

public class BtcMiningInfo {
	private static final long serialVersionUID = -1675343423503889069L;
	private long currentBlockSize = 0;
	private long currentBlockTransactions = 0;
	private boolean generate = false;
	private long generateProcessorLimit = 0;
	private long hashesPerSecond = 0;
	private BigDecimal networkGhps = BigDecimal.ZERO;
	private long pooledTransactions = 0;

	public long getCurrentBlockSize() {
		return currentBlockSize;
	}

	public void setCurrentBlockSize(long currentBlockSize) {
		this.currentBlockSize = currentBlockSize;
	}

	public long getCurrentBlockTransactions() {
		return currentBlockTransactions;
	}

	public void setCurrentBlockTransactions(long currentBlockTransactions) {
		this.currentBlockTransactions = currentBlockTransactions;
	}

	public boolean isGenerate() {
		return generate;
	}

	public void setGenerate(boolean generate) {
		this.generate = generate;
	}

	public long getGenProcessorLimit() {
		return generateProcessorLimit;
	}

	public void setGenProcessorLimit(long generateProcessorLimit) {
		this.generateProcessorLimit = generateProcessorLimit;
	}

	public long getHashesPerSecond() {
		return hashesPerSecond;
	}

	public void setHashesPerSecond(long hashesPerSecond) {
		this.hashesPerSecond = hashesPerSecond;
	}
	
	public Object getNetworkGhps() {
		return networkGhps;
	}

	public long getPooledTransactions() {
		return pooledTransactions;
	}

	public void setPooledTransactions(long pooledTransactions) {
		this.pooledTransactions = pooledTransactions;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		return "";
	}
}
