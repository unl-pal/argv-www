package edu.unl.cse;

public class Constant {

	private String name;
	private int value;
	
	public Constant(){
		this.name = "";
		this.value = 0;
	}
	
	public Constant(String name){
		this.name = name;
		this.value = 0;
	}
	
	public Constant(String name, int value){
		this.name = name;
		this.value = value;
	}
	
	public void setValue(int value){
		this.value = value;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String toString(){
		return "Location "+name+" at value "+value;
	}
	
	public boolean equals(Constant object){
		if(this.name.equals(object.getName()) && this.value == object.getValue()){
			return true;
		}
		return false;
	}
	
}
