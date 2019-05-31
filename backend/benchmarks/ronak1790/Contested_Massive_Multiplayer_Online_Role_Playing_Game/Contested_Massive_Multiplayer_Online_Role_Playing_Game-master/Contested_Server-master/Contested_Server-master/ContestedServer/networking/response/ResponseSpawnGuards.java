package networking.response;

public class ResponseSpawnGuards {

	
	private int controlPointId;
	private int factionId;
	private int guardId;
	

	
	public byte[] temp ;
	
	 public ResponseSpawnGuards()
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

	public int getFactionId() {
		return factionId;
	}

	public void setFactionId(int factionId) {
		this.factionId = factionId;
	}

	public int getGuardId() {
		return guardId;
	}

	public void setGuardId(int guardId) {
		this.guardId = guardId;
	}

	
	

	

	

	

	

	
	
}
