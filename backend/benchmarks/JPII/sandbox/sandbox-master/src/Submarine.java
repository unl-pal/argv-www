import java.util.Random;
import java.awt.*;
import java.util.ArrayList;

public class Submarine{
	
	private boolean moving;
	
	public boolean needsRepaint(){
		Random rand = new Random();
		if (moving)
			return true;
		for(int index = 0; index<rand.nextInt();index++){
			if(rand.nextBoolean()){
				return true;
			}
		}
		return false;
	}
	
	public void MouseMoved(int x, int y){
		Random rand = new Random();
		for(int index = 0; index<rand.nextInt(); index++){
		}
	}
	public void MouseClicked(int x, int y){
	}
}
