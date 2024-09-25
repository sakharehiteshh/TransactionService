package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account {

	@Id
    private String userID;
    
    private Double amount;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Account(String userID, Double amount) {
		super();
		this.userID = userID;
		this.amount = amount;
	}

	public Account() {
		super();
	}
    
    
}