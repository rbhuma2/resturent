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
@Document(collection = "user")
public class User {

    @Id
    private String identifier;
    @Indexed
    private String email;
    @Indexed
    private String phoneNumber;
    private String password;
    private String confirmedPassword;

    @JsonCreator
    public User(@JsonProperty("id") String identifier, @JsonProperty("email") String email,
            @JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("password") String password,
            @JsonProperty("confirmedPassword") String confirmedPassword) {
        super();
        this.identifier = identifier;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
    }

    public User() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}

	
    

}
