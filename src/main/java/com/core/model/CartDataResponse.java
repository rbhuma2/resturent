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
    private String orderPlaceDate;
    private double tax;
    private double subTotal;
    private String name;
    private String phoneNumber;

    @JsonCreator
    public CartDataResponse(@JsonProperty("id") String identifier, @JsonProperty("totalAmount") double totalAmount,
            @JsonProperty("email") String email, @JsonProperty("totalItems") int totalItems, @JsonProperty("itemList") List<ItemData> itemList,
            @JsonProperty("orderPlaceDate") String orderPlaceDate, @JsonProperty("tax") double tax, @JsonProperty("subTotal") double subTotal, 
            @JsonProperty("name") String name, @JsonProperty("phoneNumber") String phoneNumber) {
        super();
        this.identifier = identifier;
        this.totalAmount = totalAmount;
        this.email = email;
        this.totalItems = totalItems;
        this.itemList = itemList;
        this.orderPlaceDate = orderPlaceDate;
        this.tax = tax;
        this.subTotal = subTotal;
        this.name = name;
        this.phoneNumber = phoneNumber;
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

	public String getOrderPlaceDate() {
		return orderPlaceDate;
	}

	public void setOrderPlaceDate(String orderPlaceDate) {
		this.orderPlaceDate = orderPlaceDate;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
