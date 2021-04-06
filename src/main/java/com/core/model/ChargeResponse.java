package com.core.model;

import com.core.model.ChargeRequest.Currency;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class ChargeResponse {
	
	private String transactionId;
	private double amount;
	
	
	@JsonCreator
    public ChargeResponse(@JsonProperty("transactionId") String transactionId, @JsonProperty("currency") Currency currency, 
    		@JsonProperty("amount") double amount) {
        super();
        this.transactionId = transactionId;
        this.amount = amount;
        
    }


	public ChargeResponse() {
		super();
	}


	public String getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	

}
