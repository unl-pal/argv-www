package networking.response;

import java.util.Random;
import java.util.ArrayList;

public class ResponseControlPointState {

	private int controlPointId;
	private int controlPointState;
	public byte[] temp ;
	
	 public ResponseControlPointState()
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

	public int getControlPointState() {
		return controlPointState;
	}

	public void setControlPointState(int controlPointState) {
		this.controlPointState = controlPointState;
	}

	public Object getCp() {
		return new Object();
	}

	

	
}
