package com.core.model;

import java.util.List;

import com.core.mongo.data.entity.ItemData;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class CartDataResponse {
	
	private String identifier;
    private double totalAmount;
    private String email;
    private int totalItems;
    private List<ItemData> itemList;
    private String expirationDate;
    private double tax;
    private double subTotal;

    @JsonCreator
    public CartDataResponse(@JsonProperty("id") String identifier, @JsonProperty("totalAmount") double totalAmount,
            @JsonProperty("email") String email, @JsonProperty("totalItems") int totalItems, @JsonProperty("itemList") List<ItemData> itemList,
            @JsonProperty("expirationDate") String expirationDate, @JsonProperty("tax") double tax, @JsonProperty("subTotal") double subTotal) {
        super();
        this.identifier = identifier;
        this.totalAmount = totalAmount;
        this.email = email;
        this.totalItems = totalItems;
        this.itemList = itemList;
        this.expirationDate = expirationDate;
        this.tax = tax;
        this.subTotal = subTotal;
    }
    
    public CartDataResponse() {
		super();
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public List<ItemData> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemData> itemList) {
		this.itemList = itemList;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
	

}
