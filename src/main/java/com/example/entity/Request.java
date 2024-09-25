package com.example.entity;

public class Request {
	
	private String messageId;
	
	private String userId;
	
	private TransactionAmount transactionAmount;

	public Request() {
	}

	public Request(String messageId, String userId, TransactionAmount transactionAmount) {
		super();
		this.messageId = messageId;
		this.userId = userId;
		this.transactionAmount = transactionAmount;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public TransactionAmount getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(TransactionAmount transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	

}