package com.core.model;

import com.core.model.ChargeRequest.Currency;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class CheckOut {
	
	private Double amount;
	private Currency currency;
	private String stripePublicKey;
	
	@JsonCreator
    public CheckOut(@JsonProperty("amount") Double amount, @JsonProperty("currency") Currency currency, 
    		@JsonProperty("stripePublicKey") String stripePublicKey) {
        super();
        this.amount = amount;
        this.currency = currency;
        this.stripePublicKey = stripePublicKey;
        
    }

	public CheckOut() {
		super();
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getStripePublicKey() {
		return stripePublicKey;
	}

	public void setStripePublicKey(String stripePublicKey) {
		this.stripePublicKey = stripePublicKey;
	}
	
	
}
