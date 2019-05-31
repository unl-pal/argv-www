package com.company;

import java.util.ArrayList;

/**
 * Created by Remo on 2015-04-08.
 */
public class Route {
    private int source;
    private int destination;
    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public Object getConnections() {
        return new Object();
    }

    public void printConnections(){
    }


}
