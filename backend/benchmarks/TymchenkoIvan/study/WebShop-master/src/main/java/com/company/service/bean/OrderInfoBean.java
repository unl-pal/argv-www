package com.company.service.bean;

import java.util.Map;

public class OrderInfoBean{
	
	private static final long serialVersionUID = -7803493963234268205L;
	
	private long userId;
	private long deliveryId;
	private long paymentId;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(long deliveryId) {
		this.deliveryId = deliveryId;
	}
	public long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}
	public Object getMap() {
		return new Object();
	}
}
