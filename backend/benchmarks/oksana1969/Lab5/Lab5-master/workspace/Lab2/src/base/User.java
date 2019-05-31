package base;

import java.io.Serializable;

public class User implements Comparable<User>, Serializable{
	private int userId;
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private String userName;
	private String userEmail;
	
	public User(int userId, String userName, String userEmail){
		
		this.userId=userId;
		this.userEmail=userEmail;
		this.userName=userName;
	}
	
public String toString(){
		
		
		String s="User [" + userId+  ","+ userName+ "," + userEmail+ "]";

		
		return s;

	}
@Override
public int compareTo(User u){
	int result = 0;
	if (userId > u.userId){
		result= 1;
	}
	else if (userId < u.userId){
		result= -1;
	}
	else if(userId == u.userId){
		return result;
	}
	return result;
	
}

public String getUserName() {
	// TODO Auto-generated method stub
	return null;
}

	
	
}
