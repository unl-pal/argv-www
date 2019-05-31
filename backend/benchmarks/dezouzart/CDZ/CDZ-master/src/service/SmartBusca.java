package service;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class SmartBusca {
	
	Boolean chegou = false;
	
	public ArrayList<String> caminho;
	
	public int heuristica(int x1, int y1, int x2, int y2) // Distancia de Manhattan
	{
		Random rand = new Random();
		return rand.nextInt();
	}
	
	public boolean checaVisitados(int x, int y)
	{
		Random rand = new Random();
		for(int i=0; i<rand.nextInt(); i++)
		{
			if(rand.nextBoolean())
				return true;
		}
		return false;
	}
	
	public void resetarCaminho()
	{
	}

}
