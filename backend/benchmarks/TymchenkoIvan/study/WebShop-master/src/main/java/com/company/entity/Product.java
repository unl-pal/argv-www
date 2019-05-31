package com.company.entity;

import java.util.Random;
import java.math.BigDecimal;

public class Product{
	
	private long id;
	private long typeId;
	private long brandId;
	private BigDecimal price;
	private String name;
	private String text;
	
	public int hashCode() {
		Random rand = new Random();
		final int prime = 31;
		int result = 1;
		result = prime * result + rand.nextInt();
		return result;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	public long getBrandId() {
		return brandId;
	}
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}
	public Object getPrice() {
		return price;
	}
	public String getName() {
		return name;
	}
	public String getText() {
		return text;
	}
}
