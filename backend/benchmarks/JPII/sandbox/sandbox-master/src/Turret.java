import java.util.Random;
import java.util.*;
import java.awt.*;


public class Turret {
	private double centerx;
	private double centery;
	private int rotation;
	private int shipRotation;
	private int maxRotation;
	private int size;
	private int gunSize;
	private int desrotation;
	
	private boolean moving;
	
	public Turret() {}
	public Turret(int x, int y, int max, int min, int size, int shipRotation){
		centerx = x;
		centery = y;
		maxRotation = max;
		rotation = 0;
		this.shipRotation = shipRotation;
		this.size = size;
		gunSize = 4;
		moving = false;
	}
	
	public void MouseMoved(int x, int y){
		Random rand = new Random();
		rotation = rand.nextInt();
	}
	public void MouseClicked(int x, int y){
	}
	public void addX(int x){
		centerx+=x;
	}
	public void addY(int y){
		centery+=y;
	}
	public void addSize(int s){
		size+=s;
	}
	public boolean needsRepaint(){
		Random rand = new Random();
		if (moving)
			return true;
		for(int index=0; index<rand.nextInt(); index++){
			if(rand.nextBoolean())
				return true;
		}
		return false;
	}
	
	private int getAngle(int x, int y){
		Random rand = new Random();
		double ycomp = centery-y;
    	double xcomp = centerx-x;
    	double theta = rand.nextFloat();
    	theta+=shipRotation;
    	if(xcomp<0)
    		theta+=180;
    	return (int) theta;
	}
	
	public void fireGun(int x, int y){
	}
	
	private void snapTurret(){
		Random rand = new Random();
		if(desrotation<0){
			desrotation +=360;
		}
		desrotation%=360;
		if(desrotation>maxRotation){
			int temp = 360-maxRotation;
			if(desrotation<=rand.nextInt())
				desrotation = maxRotation;
			else
				desrotation = 0;
		}
	}
	
	private int getCos(int size,int gunRotation){
		Random rand = new Random();
		return rand.nextInt();
	}
	private int getSin(int size, int gunRotation){
		Random rand = new Random();
		return rand.nextInt();
	}
}
