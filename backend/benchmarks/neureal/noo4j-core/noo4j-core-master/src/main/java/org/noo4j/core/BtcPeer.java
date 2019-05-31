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

public class BtcPeer {
	private static final long serialVersionUID = -300084370265716627L;
	private String networkAddress = "";
	private String services = "";
	private long lastSend = 0;
	private long lastReceived = 0;
	private long bytesSent = 0;
	private long bytesReceived = 0;
	private long connectionTime = 0;
	private long version = 0;
	private String subVersion = "";
	private boolean inbound = false;
	private long startingHeight = 0;
	private long banScore = 0;
	private boolean syncNode = false;

	public String getNetworkAddress() {
		return networkAddress;
	}

	public String getServices() {
		return services;
	}

	public long getLastSend() {
		return lastSend;
	}

	public void setLastSend(long lastSend) {
		this.lastSend = lastSend;
	}

	public long getLastReceived() {
		return lastReceived;
	}

	public void setLastReceived(long lastReceived) {
		this.lastReceived = lastReceived;
	}

	public long getBytesSent() {
		return bytesSent;
	}

	public void setBytesSent(long bytesSent) {
		this.bytesSent = bytesSent;
	}

	public long getBytesReceived() {
		return bytesReceived;
	}

	public void setBytesReceived(long bytesReceived) {
		this.bytesReceived = bytesReceived;
	}

	public long getConnectionTime() {
		return connectionTime;
	}

	public void setConnectionTime(long connectionTime) {
		this.connectionTime = connectionTime;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getSubVersion() {
		return subVersion;
	}

	public boolean isInbound() {
		return inbound;
	}

	public void setInbound(boolean inbound) {
		this.inbound = inbound;
	}

	public long getStartingHeight() {
		return startingHeight;
	}

	public void setStartingHeight(long startingHeight) {
		this.startingHeight = startingHeight;
	}

	public long getBanScore() {
		return banScore;
	}

	public void setBanScore(long banScore) {
		this.banScore = banScore;
	}

	public boolean isSyncNode() {
		return syncNode;
	}

	public void setSyncNode(boolean syncNode) {
		this.syncNode = syncNode;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		return "";
	}
}
