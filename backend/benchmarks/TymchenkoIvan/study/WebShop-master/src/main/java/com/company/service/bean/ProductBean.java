package com.company.service.bean;

import java.util.Random;
import java.math.BigDecimal;

public class ProductBean{
	
	private static final long serialVersionUID = -4725070866376134136L;
	
	private long id;
	private BigDecimal price;
	private String type;
	private String brand;
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
	public Object getPrice() {
		return price;
	}
	public String getType() {
		return type;
	}
	public String getBrand() {
		return brand;
	}
	public String getName() {
		return name;
	}
	public String getText() {
		return text;
	}
}
