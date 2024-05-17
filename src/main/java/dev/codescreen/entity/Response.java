package dev.codescreen.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Response {
	
	private String messageId;
	
	private String userId;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String responseCode;
	
	private TransactionAmount balance;

	public Response() {
	}

	public Response(String messageId, String userId, TransactionAmount transactionAmount,String responseCode) {
		this.messageId = messageId;
		this.userId = userId;
		this.balance = transactionAmount;
		this.responseCode = responseCode;
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

	public TransactionAmount getBalance() {
		return balance;
	}

	public void setBalance(TransactionAmount transactionAmount) {
		this.balance = transactionAmount;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	

}