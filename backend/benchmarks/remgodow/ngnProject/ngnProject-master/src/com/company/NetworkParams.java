package com.company;

import java.util.ArrayList;

/**
 * Created by remo on 31.03.15.
 */
public class NetworkParams {

    public static final int STM1_CAPACITY = 149760000;
    public static final double G_729_CODEC_CAPACITY = 8000;
    public static final double G_711_CODEC_CAPACITY = 64000;
    public static final double RT_PACKET_HEAD = 320;
    public static final double NRT_PACKET_HEAD = 320;
    public static final double B = 0.002;
    public static final int N_DSP = 4;
    public static final int N_PCM3032 = 30;

    private double g729PacketTime = 0;
    private double g711PacketTime = 0;
    private double nrtPacketLength = 0;
    private int rtQueueLength = 0;
    private int nrtQueueLength = 0;


    private ArrayList<ArrayList<Double>> rtMatrix = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> nrtMatrix = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> rtCapacity = new ArrayList<ArrayList<Double>>();
    private ArrayList<ArrayList<Double>> nrtCapacity = new ArrayList<ArrayList<Double>>();


    public double getNrtPacketLength() {
        return nrtPacketLength;
    }

    public int getNrtQueueLength() {
        return nrtQueueLength;
    }

    public void setNrtQueueLength(int nrtQueueLength) {
        this.nrtQueueLength = nrtQueueLength;
    }

    public int getRtQueueLength() {
        return rtQueueLength;
    }

    public void setRtQueueLength(int rtQueueLength) {
        this.rtQueueLength = rtQueueLength;
    }

    public double getG711PacketTime() {
        return g711PacketTime;
    }

    public double getG729PacketTime() {
        return g729PacketTime;
    }

    public Object getRtMatrix() {
        return rtMatrix;
    }

    public Object getNrtMatrix() {
        return nrtMatrix;
    }

    public Object getNodeList() {
        return new Object();
    }

    public Object getRtCapacity() {
        return rtCapacity;
    }

    public Object getNrtCapacity() {
        return nrtCapacity;
    }

    public Object getRoutes() {
        return new Object();
    }

    public Object getRoute(int source, int destination){
        return new Object();
    }

      public String toString() {

        String result ="";
        result = result + "Macierz przepływności RT: \n";
        result = result + "Macierz przepływności NRT: \n";
        return result;
    }

    public String inputToString(){
        String result ="";
        result = "Macierz RT:"+"\n";
        result = result +"Macierz NRT:"+"\n";
        result = result + "T_g711="+g711PacketTime+"\n";
        result = result + "T_g729="+g729PacketTime+"\n";
        result = result + "Dlugosc kolejki RT="+rtQueueLength+"\n";
        result = result + "Dlugosc kolejki NRT="+nrtQueueLength+"\n";
        result = result + "Dlugosc pakietu NRT="+nrtPacketLength+"\n";
        result = result + "\n";
        return result;
    }

    public void setGwOutParams(){
        {
		}
        {
		}
    }
}
