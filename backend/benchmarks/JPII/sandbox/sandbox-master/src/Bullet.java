import java.awt.*;

public class Bullet {
	protected double startx;
	protected double endx;
	protected double starty;
	protected double endy;
	protected int currentx;
	protected int currenty;
	protected int count;
	
	protected boolean moving;
	protected boolean done;
	
	public Bullet(int sx,int sy,int ex, int ey){
		startx = sx;
		starty = sy;
		endx = ex;
		endy = ey;
		currentx=(int)startx-1;
		currenty=(int)starty-1;
		count = 0;
		moving =false;
	}
	
	public boolean needsRepaint(){
		if(moving)
			return true;
		return false;
	}
	
	protected void increaseX(int ammount){
		double xdistance = endx-startx;
		double ydistance = endy-starty;
		double theta = Math.atan(ydistance/xdistance);
		if(xdistance < 0 ){
			theta+=Math.PI;
		}
		double x = (count)*Math.cos(theta);
		double y = (count)*Math.sin(theta);
		currentx=(int)(x+startx);
		currenty=(int)(y+starty);
		count+=2;
	}
	
	public void drawBullet(Graphics g){
		if(endx < currentx)
			increaseX(-1);
		if(endx>currentx)
			increaseX(1);
		g.setColor(new Color(150,150,0));
		drawBull(g);
		if(isnearX()){
			moving=true;
		}
		else {
			finished();
		}
	}
	protected void finished(){
		done = true;
		moving = false;
	}
	
	public void drawBull(Graphics g){
		g.fillOval(currentx-2, currenty-2, 7, 3);
	}
	
	protected boolean isnearX(){
		if(currentx<(int)endx+2 && currentx > (int)endx-2){
				return false;
		}
		return true;
	}
	
	public boolean isdone(){
		return done;
	}
	public boolean getMoving(){
		return moving;
	}
}
