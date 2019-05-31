package com.company.util.querygenerator.select;

public class LimitGenerator{

	private int page;
	private int onPage;
	
	public LimitGenerator(int page, int onPage){
		this.page = page;
		this.onPage = onPage;
	}

	private int setCount(){
		return onPage;
	}
	
	private int setFrom(){
		return ((page - 1) * onPage);
	}
}
