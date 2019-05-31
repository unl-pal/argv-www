package com.company.entity;

import java.util.Date;

public class Order{
	
	private long id;
	private long userId;
	private long paymentId;
	private long deliveryId;
	private long statusId;
	private String statusMessage;
	private Date created;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}
	public long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(long deliveryId) {
		this.deliveryId = deliveryId;
	}
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public Object getCreated() {
		return created;
	}
}
