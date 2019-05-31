package networking.response;

public class ResponseSpawnGolem {

	
	private int controlPointId;
	
	
     public byte[] temp ;
	
	 public ResponseSpawnGolem()
    {
    }
	
	 public Object constructResponseInBytes()
	 {
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

	
	
	
}
