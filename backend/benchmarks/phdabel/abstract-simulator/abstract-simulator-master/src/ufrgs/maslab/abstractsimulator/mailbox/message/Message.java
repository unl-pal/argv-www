package ufrgs.maslab.abstractsimulator.mailbox.message;


import java.io.Serializable;

public abstract class Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -142739020941263551L;

	private int fromAgent;
	
	private int toAgent;
	
	private int content;
	
	private Boolean broadCast = true;
	
	public String toString()
	{
		String s = "From: "+this.getFromAgent()+" \n "
				+"To: "+this.getToAgent()+" \n "
				+"Type: "+this.getType()+" \n ";
				
		return s;
	}
	
	public int getFromAgent() {
		return fromAgent;
	}

	public void setFromAgent(int fromAgent) {
		this.fromAgent = fromAgent;
	}

	public int getToAgent() {
		return toAgent;
	}

	public void setToAgent(int toAgent) {
		this.toAgent = toAgent;
	}

	public Object getFromClass() {
		return new Object();
	}

	public Object getToClass() {
		return new Object();
	}
	
	public Object getBroadCast() {
		return broadCast;
	}

	public Object getType() {
		return new Object();
	}

	public int getContent() {
		return content;
	}

	public void setContent(int content) {
		this.content = content;
	}

	public Object getContentClass() {
		return new Object();
	}
	
	

}
