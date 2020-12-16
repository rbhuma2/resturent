package com.core.mongo.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@Document(collection = "itemData")
public class MenuItemData {

    @Id
    private String identifier;
    @Indexed
    private String name;
    @Indexed
    private String category;
    private String subType;
    private String type;
    private String description;
    private double amount;
    private boolean isSpiceLevel;
    

    @JsonCreator
    public MenuItemData(@JsonProperty("id") String identifier, @JsonProperty("name") String name,
            @JsonProperty("description") String description, @JsonProperty("amount") double amount,
            @JsonProperty("type") String type, @JsonProperty("category") String category,
            @JsonProperty("subType") String subType, @JsonProperty("isSpiceLevel") boolean isSpiceLevel) {
        super();
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.subType = subType;
        this.isSpiceLevel = isSpiceLevel;
    }

	public MenuItemData() {
		super();
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public boolean isSpiceLevel() {
		return isSpiceLevel;
	}

	public void setSpiceLevel(boolean isSpiceLevel) {
		this.isSpiceLevel = isSpiceLevel;
	}
	
	
}
