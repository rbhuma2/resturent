package com.core.mongo.data.embedded;

import java.io.Serializable;

public class AddOnItem implements Serializable{

	private static final long serialVersionUID = 4554258877892405614L;
	
	private String name;
	private int quantity;
	private double amount;
	
	
	public AddOnItem() {
		super();
	}

	public AddOnItem(String name, int quantity, double amount) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.amount = amount;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	
			
}
