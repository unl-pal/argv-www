package networking.response;

public class ResponseChangeResourcePoints {

	
	private int factionId;
	private int resourceAmount;

	
	public byte[] temp ;
	
	 public ResponseChangeResourcePoints()
    {
    }
	
	 public Object constructResponseInBytes()
	 {
		 {
		}
		 //System.out.println(temp+"----");
		 return temp;
	 }

	public int getFactionId() {
		return factionId;
	}

	public void setFactionId(int factionId) {
		this.factionId = factionId;
	}

	public int getResourceAmount() {
		return resourceAmount;
	}

	public void setResourceAmount(int resourceAmount) {
		this.resourceAmount = resourceAmount;
	}

	

	

	

	

	

	
	
}
