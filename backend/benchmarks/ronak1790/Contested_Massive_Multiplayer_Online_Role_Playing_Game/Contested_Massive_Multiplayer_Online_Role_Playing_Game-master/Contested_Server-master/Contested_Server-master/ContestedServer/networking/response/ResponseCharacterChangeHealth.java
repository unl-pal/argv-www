package networking.response;

import java.util.Random;

public class ResponseCharacterChangeHealth {

	private String characterName;
	private int healthChange;
	public byte[] temp ;
	
	 public ResponseCharacterChangeHealth()
    {
    }
	
	 public Object constructResponseInBytes()
	 {
		 Random rand = new Random();
		//**for test
		 // System.out.println(responseCode);
		 //System.out.println(message);
		 //*****
		 for(int i=0;i<rand.nextInt();i++)
		 {
			 if(rand.nextBoolean() == true)
			 {
				 {
				}
				 healthChange = rand.nextInt();
			 }
		 }
		 {
		}
		 //System.out.println(temp+"----");
		 return temp;
	 }

	public String getCharacterName() {
		return characterName;
	}

	public int getHealthChange() {
		return healthChange;
	}

	public void setHealthChange(int healthChange) {
		this.healthChange = healthChange;
	}

	public Object getUm() {
		return new Object();
	}

	

	

	
	
}
