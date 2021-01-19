package com.core.mongo.data.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.core.mongo.data.embedded.AddOnItem;


@Document(collection = "cartItemData")
public class ItemData {

    @Id
    private String identifier;
    private String name;
    private double amount;
	private int quantity;
	private boolean isOnlyQuantity;
	private double netAmount;
	private String type;
	private String subType;
	private String spiceLevel;
	private String specialNote;		;
	private List<AddOnItem> addOnItemList;
	private String image;
	private String email;
	
	
	public ItemData(String name, int quantity, double netAmount, String type, String subType, String spiceLevel, String specialNote,
			List<AddOnItem> addOnItemList, boolean isOnlyQuantity, double amount, String image,String email) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.netAmount = netAmount;
		this.type = type;
		this.subType = subType;
		this.spiceLevel = spiceLevel;
		this.specialNote = specialNote;
		this.addOnItemList = addOnItemList;
		this.isOnlyQuantity = isOnlyQuantity;
		this.amount = amount;
		this.image = image;
		this.email = email;
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

	public String getSpecialNote() {
		return specialNote;
	}

	public void setSpecialNote(String specialNote) {
		this.specialNote = specialNote;
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
   
    

}
