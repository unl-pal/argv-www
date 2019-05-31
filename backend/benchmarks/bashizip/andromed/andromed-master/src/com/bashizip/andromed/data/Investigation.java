package com.bashizip.andromed.data;

import java.io.Serializable;
import java.util.Date;



public class Investigation implements Serializable{

	private static final long serialVersionUID = 7590103290165670089L;
	
	private long id;
	private String desease;
	
	private String contry;
	private String zone;
	private String author;
	private String code;
	private int status;
	private String description;
	private Date startDate;
	private int syncStatus=0;
	
	public Investigation() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDesease() {
		return desease;
	}

	public void setDesease(String desease) {
		this.desease = desease;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int enCours) {
		this.status = enCours;
	}

	public int getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContry() {
		return contry;
	}

	public void setContry(String contry) {
		this.contry = contry;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
