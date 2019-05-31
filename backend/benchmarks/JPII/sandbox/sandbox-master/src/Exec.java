import java.util.Random;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;

public class Exec
{
	public int selected=1;
	public Exec(){
		try{
		}
		catch(Exception e){}
	}
	public void init()
	{
		for (int x = 0; x < 800; x += 50) {
		}
		for (int y = 0; y < 600; y += 50){
		}
	}
	public void reset() {
	}
	public Object newBackground() {
		 return new Object();
	}
	public Object clearBuffer() {
		return new Object();
	}
	public void delay(int n){
		try {} catch (Exception e) {}
	}	
	public boolean needsRepaint(){
		Random rand = new Random();
		for(int index = 0; index<rand.nextInt();index++){
			if(rand.nextBoolean()){
				return true;
			}
		}	
		return false;
	}
}
