package com.company.entity;

import java.math.BigDecimal;

public class OrderItem{

	private long id;
	private long orderId;
	private long productId;
	private long count;
	private BigDecimal price;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public Object getPrice() {
		return price;
	}
}
