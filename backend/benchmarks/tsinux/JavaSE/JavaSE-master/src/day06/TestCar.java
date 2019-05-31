package day06;

public class TestCar {
}

class Factory{
	
	//���쳵(����������������)
	public Object produceCar(){
		return new Object();
	}
}


class Car{
	//����
	String name;
	int wheel; 
	
	//����
	public void info(){
	}
	
	public void show(){
	}
	
	public String getName(){
		return name;
	}
	
	public void setWheel(int w){
		wheel=w;
	}
	
	
	
}