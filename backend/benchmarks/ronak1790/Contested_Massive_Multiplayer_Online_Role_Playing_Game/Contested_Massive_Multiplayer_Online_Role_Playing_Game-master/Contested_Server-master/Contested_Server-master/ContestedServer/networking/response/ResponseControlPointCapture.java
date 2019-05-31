package networking.response;

import java.util.Random;

public class ResponseControlPointCapture {

	private int controlPointId;
	private int factionId;
	public byte[] temp ;
	
	 public ResponseControlPointCapture()
    {
    }
	
	 public Object constructResponseInBytes()
	 {
		 Random rand = new Random();
		//**for test
		 // System.out.println(responseCode);
		 //System.out.println(message);
		 //*****
		 controlPointId = rand.nextInt();
		 factionId = rand.nextInt();
		 {
		}
		 //System.out.println(temp+"----");
		 return temp;
	 }

	public int getControlPointId() {
		return controlPointId;
	}

	public void setControlPointId(int controlPointId) {
		this.controlPointId = controlPointId;
	}

	public int getFactionId() {
		return factionId;
	}

	public void setFactionId(int factionId) {
		this.factionId = factionId;
	}

	public Object getCp() {
		return new Object();
	}

	

	
}
