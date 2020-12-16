package com.core.mongo.data.embedded;

import java.io.Serializable;
import java.util.List;

public class Item implements Serializable{

	private static final long serialVersionUID = 4554258877892405614L;
	
	private String name;
	private int quantity;
	private double netAmount;
	private String type;
	private String subType;
	private String spiceLevel;
	private String description;
	private List<AddOnItem> addOnItemList;
	
	public Item() {
		super();
	}

	public Item(String name, int quantity, double netAmount, String type, String subType, String spiceLevel, String description) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.netAmount = netAmount;
		this.type = type;
		this.subType = subType;
		this.spiceLevel = spiceLevel;
		this.description = description;
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

	public double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getSpiceLevel() {
		return spiceLevel;
	}

	public void setSpiceLevel(String spiceLevel) {
		this.spiceLevel = spiceLevel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

			
}
