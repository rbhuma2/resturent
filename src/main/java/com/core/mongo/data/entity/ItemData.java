package com.core.mongo.data.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.core.mongo.data.embedded.AddOnItem;
import com.core.mongo.data.embedded.Item;


@Document(collection = "cartItemData")
public class ItemData {

    @Id
    private String identifier;
    private String name;
	private int quantity;
	private boolean isOnlyQuantity;
	private double netAmount;
	private String type;
	private String subType;
	private String spiceLevel;
	private String description;
	private List<AddOnItem> addOnItemList;
	
	
	public ItemData(String name, int quantity, double netAmount, String type, String subType, String spiceLevel, String description,
			List<AddOnItem> addOnItemList, boolean isOnlyQuantity) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.netAmount = netAmount;
		this.type = type;
		this.subType = subType;
		this.spiceLevel = spiceLevel;
		this.description = description;
		this.addOnItemList = addOnItemList;
		this.isOnlyQuantity = isOnlyQuantity;
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

    public ItemData() {
        super();
    }

    
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

	public List<AddOnItem> getAddOnItemList() {
		return addOnItemList;
	}

	public void setAddOnItemList(List<AddOnItem> addOnItemList) {
		this.addOnItemList = addOnItemList;
	}

	public boolean isOnlyQuantity() {
		return isOnlyQuantity;
	}

	public void setOnlyQuantity(boolean isOnlyQuantity) {
		this.isOnlyQuantity = isOnlyQuantity;
	}
   
    

}
