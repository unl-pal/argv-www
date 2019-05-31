package model;

import java.util.ArrayList;

public class UserModel {

	int userid;
	String username;
	String password;
	boolean IsConnected;
	public UserModel()
	{
	}

	
	
	public int getUserid() {
		return userid;
	}



	public void setUserid(int userid) {
		this.userid = userid;
	}



	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isIsConnected() {
		return IsConnected;
	}

	public void setIsConnected(boolean isConnected) {
		IsConnected = isConnected;
	}

	public Object getCharlist() {
		return new Object();
	}

	
	
}
