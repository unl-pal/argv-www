package networking.response;

public class ResponseDestroyNPC {

	
	private int npcId;
	
	
     public byte[] temp ;
	
	 public ResponseDestroyNPC()
    {
    }
	
	 public Object constructResponseInBytes()
	 {
		 {
		}
		 //System.out.println(temp+"----");
		 return temp;
	 }

	public int getNpcId() {
		return npcId;
	}

	public void setNpcId(int npcId) {
		this.npcId = npcId;
	}

	
	
	
}
