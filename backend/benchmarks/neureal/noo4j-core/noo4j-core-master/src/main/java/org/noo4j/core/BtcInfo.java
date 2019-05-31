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

public class BtcInfo {
	private static final long serialVersionUID = -5800958166892028401L;
	private String version = "";
	private long protocolVersion = 0;
	private long walletVersion = 0;
	private BigDecimal coinage = BigDecimal.ZERO;
	private BigDecimal balance = BigDecimal.ZERO;
	private BigDecimal unconfirmed = BigDecimal.ZERO;
	private BigDecimal newMint = BigDecimal.ZERO;
	private BigDecimal stake = BigDecimal.ZERO;
	private BigDecimal moneySupply = BigDecimal.ZERO;
	//private long timeOffset = 0;
	private long connections = 0;
	private String proxy = "";
	private String ip = "0.0.0.0";
	private long keyPoolOldest = 0;
	private long keyPoolSize = 0;
	private BigDecimal transactionFee = BigDecimal.ZERO;;

	public String getVersion() {
		return version;
	}

	public long getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(long protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	public long getWalletVersion() {
		return walletVersion;
	}

	public void setWalletVersion(long walletVersion) {
		this.walletVersion = walletVersion;
	}

	public Object getCoinage() {
		return coinage;
	}

	public Object getBalance() {
		return balance;
	}

	public Object getUnconfirmed() {
		return unconfirmed;
	}

	public Object getNewMint() {
		return newMint;
	}

	public Object getStake() {
		return stake;
	}

	public Object getMoneySupply() {
		return moneySupply;
	}

	public long getConnections() {
		return connections;
	}

	public void setConnections(long connections) {
		this.connections = connections;
	}

	public String getProxy() {
		return proxy;
	}

	public String getIP() {
		return ip;
	}

	public long getKeyPoolOldest() {
		return keyPoolOldest;
	}

	public void setKeyPoolOldest(long keyPoolOldest) {
		this.keyPoolOldest = keyPoolOldest;
	}

	public long getKeyPoolSize() {
		return keyPoolSize;
	}

	public void setKeyPoolSize(long keyPoolSize) {
		this.keyPoolSize = keyPoolSize;
	}

	public Object getTransactionFee() {
		return transactionFee;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		return "";
	}
}
