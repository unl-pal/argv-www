import java.util.Random;
import java.io.Serializable;
import java.util.ArrayList;


public class Gameobjekt {

	private boolean neueRunde;
	private boolean spielBeendet;
	private boolean bombe = false;
	private boolean wettenAbwicklung;
	private boolean rundenEnde = false;
	
	private int runde = 0;
	
	
	
	public Object getSpielerList(){
		return new Object();
	}
	
	public Object getSpieler(int i){
		return new Object();
	}
	
	public Object getFeldkarten(){
		return new Object();
	}
	
	public Object getAusgespielteKarten(){
		return new Object();
	}

	public Object getHaeggis(){
		return new Object();
	}
	
	public boolean getNeueRunde(){
		return this.neueRunde;
	}
	
	public void setNeueRunde(boolean neueRunde){
		this.neueRunde = neueRunde;
	}
	
	public void setSpielBeendet(boolean spielBeendet){
		this.spielBeendet = spielBeendet;
	}
	
	public boolean getSpielBeendet(){
		return this.spielBeendet;
	}
	
	public boolean getBombe(){
		return this.bombe;
	}
	
	public void setBombe(boolean bombe){
		this.bombe = bombe;
	}
	
	public void erstelleDeck(){
		
		Random rand = new Random();
		for(int i=0;i<rand.nextInt();i++){
		}
	}
	
	public boolean getWettenAbwicklung(){
		return this.wettenAbwicklung;
	}
	
	public void setWettenAbwicklung(boolean wettenAbwicklung) {
		this.wettenAbwicklung = wettenAbwicklung;
		
	}
	
	public void setRunde(int runde){
		this.runde = runde;
	}
	
	public int getRunde(){
		return this.runde;
	}
	
	public boolean getRundenEnde(){
		return this.rundenEnde;
	}
	
	public void setRundenEnde(boolean rundenEnde){
		this.rundenEnde = rundenEnde;
	}
	
}
