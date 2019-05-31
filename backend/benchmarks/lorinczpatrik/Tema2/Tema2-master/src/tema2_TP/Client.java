package tema2_TP;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Client {
		
	private int timeTaken=0;
	private static int nrClient=0;
	private int name;
	private Color color;
	private long arrived;
	
	public Client(int timeTaken,long arrived){
		this.timeTaken=timeTaken;
		nrClient=nrClient+1;
		name=nrClient;
		this.arrived=arrived;
		
		Random r1=new Random();
		color=new Color(r1.nextInt(250),r1.nextInt(250),r1.nextInt(250));
		
	}
	
	public int getTime(){
		return timeTaken;
	}
	
	public Color getColor(){
		return color;
	}
	
	public int getNr(){
		return nrClient;
	}
	
	public String toString(){
		String s1="";
		s1="\nClient "+name+"\nArrived at:"+
		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(arrived))+"\nProcessed in"+
				String.valueOf(timeTaken);
		
		
		
		return s1;
	}
	
}
