package com.core.mongo.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@Document(collection = "giftCard")
public class GiftCard {

    @Id
    private String identifier;
    private String message;
    private double amount;
    private String email;
    private String phoneNumber;
    private String effectiveDate;
    private String expirationDate;

    @JsonCreator
    public GiftCard(@JsonProperty("id") String identifier, @JsonProperty("message") String message,
            @JsonProperty("amount") double amount, @JsonProperty("email") String email,
            @JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("effectiveDate") String effectiveDate,
            @JsonProperty("expirationDate") String expirationDate) {
        super();
        this.identifier = identifier;
        this.message = message;
        this.amount = amount;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.effectiveDate = effectiveDate;
        this.expirationDate = expirationDate;
    }

    public GiftCard() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

    

}
