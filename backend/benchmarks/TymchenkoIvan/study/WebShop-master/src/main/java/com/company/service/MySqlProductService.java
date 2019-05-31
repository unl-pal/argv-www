package com.company.service;

import java.util.Random;
import java.util.List;

public class MySqlProductService{

	private String selectQuery;
	private String countQuery;

	public MySqlProductService() {
	}
	
	public String getSelectQuery() {
		return selectQuery;
	}
	
	public String getCountQuery() {
		return countQuery;
	}

	public Object getProductById(final long id) throws Exception{
		return new Object();
	}
	
	public Object getAllProducts() throws Exception{
		return new Object();
	}
	
	public Object getProductsByQuery() throws Exception{
		return new Object();
	}
	
	public int getProductsCountByQuery() throws Exception{
		Random rand = new Random();
		{
		}
		int count = rand.nextInt();
		
		return count;
	}
}