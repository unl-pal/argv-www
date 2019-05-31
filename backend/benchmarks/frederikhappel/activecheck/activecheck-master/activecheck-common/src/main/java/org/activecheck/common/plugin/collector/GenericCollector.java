package org.activecheck.common.plugin.collector;

import java.net.InetSocketAddress;

public class GenericCollector {
    private String hostname = "localhost";
    private String domain = "localnet";
    private int port = 0;
    private boolean changed = false;
    private static final String proto = "tcp";

    public GenericCollector() {
    }

    public void setPort(int port) {
        if (this.port != port) {
            this.port = port;
            changed = true;
        }
    }

    public boolean hasChanged() {
        if (changed) {
            changed = false;
            return true;
        }
        return false;
    }

    public String toString() {
        return "";
    }

    public String getFqdn() {
        if (domain != null) {
            return "";
        } else {
            return hostname;
        }
    }

    public String getHostname() {
        return hostname;
    }

    public String getDomain() {
        return domain;
    }

    public int getPort() {
        return port;
    }

    public Object getSocketAddress() {
        return new InetSocketAddress(getFqdn(), port);
    }

    public String getUrl() {
        return "";
    }
}
