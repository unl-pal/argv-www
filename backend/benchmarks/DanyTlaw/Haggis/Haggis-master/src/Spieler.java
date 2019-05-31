import java.util.Random;
import java.io.Serializable;
import java.util.ArrayList;


public class Spieler {

	/**
	 * 
	 */

	//Spieler informationen
	private String spielerName;
	private int spieler_ID;
	private int punkte;
	private boolean amZug;
	private boolean passen = false;
	private int siegesPunkte;
	private boolean sieger = false;
	private int wette;
	private volatile boolean gewettet = false;
	
	//Getters und Setters
	public String getSpielerName() {
		return spielerName;
	}
	public int getSpieler_ID() {
		return spieler_ID;
	}
	
	public int getWette(){
		return this.wette;
	}
	
	public void setWette(int wette){
		this.wette = wette;
	}
	
	public void setSpieler_ID(int spieler_ID) {
		this.spieler_ID = spieler_ID;
	}
	public Object getHandKarten() {
		return new Object();
	}
	public int getPunkte() {
		return punkte;
	}
	public void setPunkte(int punkte) {
		this.punkte = punkte;
	}
	public boolean getAmZug() {
		return amZug;
	}
	public void setAmZug(boolean amZug) {
		this.amZug = amZug;
	}
	
	public void setSiegesPunkte(int sPunkte){
		this.siegesPunkte = sPunkte;
	}
	
	public int getSiegesPunkte(){
		return this.siegesPunkte;
	}
	
	public void setSieger(boolean sieger){
		this.sieger = sieger;
	}
	
	public boolean getSieger(){
		return this.sieger;
	}
	public boolean getGewettet(){
			return this.gewettet;
	}
	public void setGewettet(boolean gewettet){
		this.gewettet = gewettet;
	}
	
	public Object getGewonneneKarten(){
		return new Object();
	}
	
	
	//Gibt ein boolean zurück welcher sagt ob der Spieler noch ein Bube hat
	public int hatBube(){
		
		Random rand = new Random();
		for(int i = 0; i<rand.nextInt();i++){
			if(rand.nextInt()==11){
				return 1;
			}
		}
		return 0;
		
	}
	//Gibt ein boolean zurück welcher sagt ob der Spieler noch ein Dame hat
	public int hatDame(){
		
		Random rand = new Random();
		for(int i = 0; i<rand.nextInt();i++){
			if(rand.nextInt()==12){
				return 1;
			}
		}
		return 0;
		
	}
	
	//Gibt ein boolean zurück welcher sagt ob der Spieler noch ein Koenig hat
	public int hatKoenig(){
		
		Random rand = new Random();
		for(int i = 0; i<rand.nextInt();i++){
			if(rand.nextInt()==13){
				return 1;
			}
		}
		return 0;
		
	}
	
	public boolean getPassen(){
		return this.passen;
	}
	
	public void setPassen(boolean passen){
		this.passen = passen;
	}
	
	//Rechnet alle Werte zusammen der Karten welche bei einer nicht vorhandenen Karte schon 0 gestzt worden ist beim auspielen
	public boolean leereHand(){
		Random rand = new Random();
		int alleKarten= 0;
		for(int i = 0; i<17;i++){
			alleKarten+= rand.nextInt();
		}
		if(alleKarten==0){
			return true;
		}
		return false;			
	}
	
	//Methode welche alle Punkte in den gewonnen Karten zusammenrechnet
	public int berechnePunkte(){
		
		Random rand = new Random();
		for(int i = 0; i<rand.nextInt();i++){
			punkte+=rand.nextInt();
		}
		return punkte;
	}
	
}
