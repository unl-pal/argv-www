package com.company.service.bean;

import java.math.BigDecimal;

public class JsonBasketBean{

	private static final long serialVersionUID = 8881230717845867867L;
	
	private int totalCount;
	private BigDecimal totalPrice;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public Object getTotalPrice() {
		return totalPrice;
	}
}
