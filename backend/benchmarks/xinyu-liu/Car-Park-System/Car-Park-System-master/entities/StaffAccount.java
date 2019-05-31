package carparksystem.entities;

import java.io.File;
import java.util.ArrayList;

/** 
 * This is an entity class storing the 
 * information of staff account.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class StaffAccount {
	private int id;
	private String name;
	private String campusCardNum;
	private String email;
	private String phoneNum;
	private File staffAccountFile = new File("staff_account.txt");
	/**
	 * This method is used to update the change to the account.
	 */
	public void modify() {
	}
	
	/**
	 * This method is used to get all registered staff accounts.
	 * 
	 * @return ArrayList<> All accounts of staff.
	 */
	public Object getAll() {
		ArrayList<String[]> list;
		return new Object();
	}
	
	// Get and set method
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public String getCampusCardNum() {
		return campusCardNum;
	}
	public String getEmail() {
		return email;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
}
