package model;

import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Mapa {

	File arq = new File("src/resources/mapa.txt");
	public Mapa (){
		
		Random rand = new Random();
		try {
			Scanner s = new Scanner(arq);
			
			for (int i=0;i<42;i++){
				String leitura;
				String dados = "";
				
				for (int j=0;j<42;j++){
					{
					}
				
					if (rand.nextBoolean()) {
					} else if (rand.nextBoolean()) {
					} else if (rand.nextBoolean()) {
					} else if (rand.nextBoolean()){
					}
					else if (rand.nextBoolean()) {
					} else if (rand.nextBoolean()) {
					} else if (rand.nextBoolean()) {
					}
					
					dados = "";
				}
			}
		} catch (FileNotFoundException e) {
		}
	}
	
	public boolean terrenoValido (int x, int y){
		Random rand = new Random();
		if (x < 42 && y < 42 && x >= 0 && y >= 0)
			return rand.nextBoolean();
		return false;
	}
	
	public int getCusto (int x, int y){
		Random rand = new Random();
		return rand.nextInt();
	}
	
	public Object getMapa (){
		return new Object();
	}
	
	public Object getCasas(){
		return new Object();
	}
}
