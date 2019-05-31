package edu.unl.cse;

public class Location {
	
	private String name;
	private int address;
	
	public Location(){
		this.name = "";
		this.address = 0;
	}
	
	public Location(String name){
		this.name = name;
		this.address = 0;
	}
	
	public Location(String name, int address){
		this.name = name;
		this.address = address;
	}
	
	public void setAddress(int address){
		this.address = address;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getAddress(){
		return this.address;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String toString(){
		return "Location "+name+" at address "+address;
	}
}
