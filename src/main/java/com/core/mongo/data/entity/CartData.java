package com.core.mongo.data.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@Document(collection = "cartData")
public class CartData {

    @Id
    private String identifier;
    private double subTotalAmount;
    private double totalAmount;
    private String email;
    private int totalItems;
    private List<String> itemList;
    private String orderPlaceDate;
    private String paymentDate;
    private double tax;
    private boolean isProcessed;
    private String transactionId;
    
    

    @JsonCreator
    public CartData(@JsonProperty("id") String identifier, @JsonProperty("totalAmount") double totalAmount, @JsonProperty("subTotalAmount") double subTotalAmount,
            @JsonProperty("email") String email, @JsonProperty("totalItems") int totalItems, @JsonProperty("itemList") List<String> itemList,
            @JsonProperty("orderPlaceDate") String orderPlaceDate, @JsonProperty("tax") double tax, @JsonProperty("isProcessed") boolean isProcessed,
            @JsonProperty("transactionId") String transactionId, @JsonProperty("paymentDate") String paymentDate) {
        super();
        this.identifier = identifier;
        this.totalAmount = totalAmount;
        this.subTotalAmount = subTotalAmount;
        this.email = email;
        this.totalItems = totalItems;
        this.itemList = itemList;
        this.orderPlaceDate = orderPlaceDate;
        this.tax = tax;
        this.isProcessed = isProcessed;
        this.transactionId = transactionId;
        this.paymentDate = paymentDate;
       
    }
    
    public CartData() {
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

	public List<String> getItemList() {
		return itemList;
	}

	public void setItemList(List<String> itemList) {
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

	public double getSubTotalAmount() {
		return subTotalAmount;
	}

	public void setSubTotalAmount(double subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}

	public boolean isProcessed() {
		return isProcessed;
	}

	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	
	
}
