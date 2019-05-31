package networking.response;

public class ResponseRenderCharacter {

	private String characterName;
	private int factionId;
	

	
	public byte[] temp ;
	
	 public ResponseRenderCharacter()
    {
    }
	
	 public Object constructResponseInBytes()
	 {
		 {
		}
		 //System.out.println(temp+"----");
		 return temp;
	 }

	public String getCharacterName() {
		return characterName;
	}

	public int getFactionId() {
		return factionId;
	}

	public void setFactionId(int factionId) {
		this.factionId = factionId;
	}

	
	
}
