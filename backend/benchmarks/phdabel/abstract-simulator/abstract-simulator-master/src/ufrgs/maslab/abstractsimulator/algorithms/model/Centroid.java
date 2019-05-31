package ufrgs.maslab.abstractsimulator.algorithms.model;

import java.util.ArrayList;

public class Centroid {
	
	private int id;
    private double x;
    private double y;
    private Double z = null;
    private ArrayList<Double> attributes = new ArrayList<Double>();
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public Double getZ() {
		return z;
	}
	public void setZ(Double z) {
		this.z = z;
	}
	public ArrayList<Double> getAttributes() {
		return attributes;
	}
	public void setAttributes(ArrayList<Double> attributes) {
		this.attributes = attributes;
	}

}
