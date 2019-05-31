package networking.response;

public class ResponseCharacterMovement {

	private String username;
	private float x;
	private float y;
	private float z;
	private float h;
	private int isMoving;
	public byte[] temp ;
	
	 public ResponseCharacterMovement()
    {
    }
	
	 public Object constructResponseInBytes()
	 {
		 {
		}
		 //System.out.println(temp+"----");
		 return temp;
	 }

	public String getUsername() {
		return username;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getH() {
		return h;
	}

	public int getIsMoving() {
		return isMoving;
	}

	public void setIsMoving(int isMoving) {
		this.isMoving = isMoving;
	}

	public Object getUm() {
		return new Object();
	}

	 

	
	
}
