package com.bashizip.andromed.data;

import java.io.Serializable;
import java.util.Date;





public class Patient implements Serializable{
	

    private Long id;
 
    private String name;
	private int age;
	private String categorie;
	private int illnessDays;
	private boolean underTreatement;
//private Date creationDate;


	public Patient() {
	
	}
	


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Patient(int age, String categorie, int illnessDays,
			boolean underTreatement) {
		
		super();
		this.age = age;
		this.categorie = categorie;
		this.illnessDays = illnessDays;
		this.underTreatement = underTreatement;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getCategorie() {
		return categorie;
	}


	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}




	public int getIllnessDays() {
		return illnessDays;
	}




	public void setIllnessDays(int illnessDays) {
		this.illnessDays = illnessDays;
	}


	public boolean isUnderTreatement() {
		return underTreatement;
	}


	public void setUnderTreatement(boolean underTreatement) {
		this.underTreatement = underTreatement;
	}


	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}


	
	
	

}