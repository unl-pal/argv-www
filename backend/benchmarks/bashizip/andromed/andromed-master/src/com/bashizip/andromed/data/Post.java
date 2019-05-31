package com.bashizip.andromed.data;

import java.io.Serializable;




public class Post implements Serializable {

	private static final long serialVersionUID = 7390103290165670089L;
	
	
	private Long id;

	//user
	private String firstname;
	private String lastname;
	private String mail;
	private String phone;
	
	//zone	
	private String pays;
	private String zone;
	private String description;

	
	//desease	
	private String desease;
	
	
	//patient
	private String name;	
	private String categorie;
	private int age;
	private int  sinceDays;
	private boolean underTreatment;
	
	private String postDate;
	
	private int syncStatus=0;
	
	
	
	public Post() {

	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getFirstname() {
		return firstname;
	}



	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}



	public String getLastname() {
		return lastname;
	}



	public void setLastname(String lastname) {
		this.lastname = lastname;
	}



	public String getMail() {
		return mail;
	}



	public void setMail(String mail) {
		this.mail = mail;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getPays() {
		return pays;
	}



	public void setPays(String pays) {
		this.pays = pays;
	}



	public String getZone() {
		return zone;
	}



	public void setZone(String zone) {
		this.zone = zone;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getDesease() {
		return desease;
	}



	public void setDesease(String desease) {
		this.desease = desease;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getCategorie() {
		return categorie;
	}



	public int getSyncStatus() {
		return syncStatus;
	}



	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}



	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}



	public int getAge() {
		return age;
	}



	public void setAge(int age) {
		this.age = age;
	}

	public int getSinceDays() {
		return sinceDays;
	}

	public void setSinceDays(int sinceDays) {
		this.sinceDays = sinceDays;
	}

	public boolean isUnderTreatment() {
		return underTreatment;
	}


	public void setUnderTreatment(boolean underTreatment) {
		this.underTreatment = underTreatment;
	}



	public String getPostDate() {
		return postDate;
	}



	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}



	

	

}
