package networking.response;

public class ResponseGolemPiece {

	
	private int factionId;
	
	
     public byte[] temp ;
	
	 public ResponseGolemPiece()
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

	
	
}
