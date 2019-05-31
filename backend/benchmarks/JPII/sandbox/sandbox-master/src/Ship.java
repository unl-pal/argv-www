import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class Ship {
	
	protected int x,y;
	protected int size;
	protected boolean moving;
	protected int color;
	protected double angle;
	
	public final int NORTH = 0;
	public final int SOUTH = 180;
	public final int EAST = 270;
	public final int WEST = 90;
	
	public Ship(int x, int y,int color,double angle){
		this.x=x;
		this.y=y;
		size = 15;
		this.color=color;
		this.angle=angle;
	}
	public void addX(int x){
		this.x+=x;
	}
	public void addY(int y){
		this.y+=y;
	}
	protected void addAngle(int angle){
		this.angle+=angle;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public double getAngle(){
		return angle;
	}
	abstract public void MouseMoved(int x, int y);
	abstract public void MouseClicked(int x, int y);
	public boolean needsRepaint(){
		if(moving){
			return true;
		}
		return false;
	}
	public void drawShip(Graphics g){
		base(g,size);
	}	
	abstract protected void base(Graphics g,int size);
	
	protected Color getColor(){
		switch(color){
			case 1: return Color.green;
			case 2: return Color.red;
			case 3: return Color.cyan;
			case 4: return Color.yellow;
			case 5: return Color.orange;
			
			default: return null;
		}
	}
	
	public AffineTransform rotate(double angle, double distancex, double distancey,int imgwidth,int imgheight) {
		
		AffineTransform aff = new AffineTransform();
		aff.translate(distancex+imgwidth,distancey+imgheight);
		aff.rotate(angle / 57.29577);
		aff.translate(-imgwidth,-imgheight);
		return aff;
	}
}
