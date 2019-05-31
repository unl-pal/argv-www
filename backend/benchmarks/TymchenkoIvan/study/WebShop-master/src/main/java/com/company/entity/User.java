package com.company.entity;

/**
 * User entity realization.
 * 
 * @author Ivan_Tymchenko
 */
public class User{
	
	private long id;
	private long roleId;
	private String login;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String[] notifications;
	
	public String getLogin() {
		return login;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	public Object getNotifications() {
		return notifications;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
}
