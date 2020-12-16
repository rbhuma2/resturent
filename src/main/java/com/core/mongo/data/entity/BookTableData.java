package com.core.mongo.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@Document(collection = "bookData")
public class BookTableData {

    @Id
    private String identifier;
    private String date;
    private String name;
    private String email;
    private String phoneNumber;
    private String bookTime;
    private String persons;
    private String status;

    @JsonCreator
    public BookTableData(@JsonProperty("id") String identifier, @JsonProperty("date") String date,
            @JsonProperty("name") String name, @JsonProperty("email") String email,
            @JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("bookTime") String bookTime,
            @JsonProperty("persons") String persons, @JsonProperty("status") String status) {
        super();
        this.identifier = identifier;
        this.date = date;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bookTime = bookTime;
        this.persons = persons;
        this.status = status;
    }

	public BookTableData() {
		super();
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getTime() {
		return bookTime;
	}

	public void setTime(String bookTime) {
		this.bookTime = bookTime;
	}

	public String getPersons() {
		return persons;
	}

	public void setPersons(String persons) {
		this.persons = persons;
	}

	public String getBookTime() {
		return bookTime;
	}

	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    

}
