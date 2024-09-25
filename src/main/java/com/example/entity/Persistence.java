package com.example.entity;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Persistence {
	@Id
	private final UUID eventId = UUID.randomUUID();
    private final Date timeStamp = new Date();
    private String requestPayload;
    private String messageId;
    private String userId;
    private String transactionAmount;
    private String accountBalanceAfter;
    private String accountBalanceBefore;
    private String eventType;
    private String authorizationStatus;
    private String responsePayload;
    private String statusCode;
    private String transactionStatus;
    private String errorReason;
    private String exception;

	public Persistence(String requestPayload, String messageId, String userId, String transactionAmount,
			String accountBalanceAfter, String accountBalanceBefore, String eventType, String authorizationStatus,
			String responsePayload, String statusCode, String transactionStatus, String errorReason, String exception) {
		super();
		this.requestPayload = requestPayload;
		this.messageId = messageId;
		this.userId = userId;
		this.transactionAmount = transactionAmount;
		this.accountBalanceAfter = accountBalanceAfter;
		this.accountBalanceBefore = accountBalanceBefore;
		this.eventType = eventType;
		this.authorizationStatus = authorizationStatus;
		this.responsePayload = responsePayload;
		this.statusCode = statusCode;
		this.transactionStatus = transactionStatus;
		this.errorReason = errorReason;
		this.exception = exception;
	}

	public Persistence() {
	}

	public String getRequestPayload() {
		return requestPayload;
	}

	public void setRequestPayload(String requestPayload) {
		this.requestPayload = requestPayload;
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

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getAuthorizationStatus() {
		return authorizationStatus;
	}

	public void setAuthorizationStatus(String authorizationStatus) {
		this.authorizationStatus = authorizationStatus;
	}

	public String getResponsePayload() {
		return responsePayload;
	}

	public void setResponsePayload(String responsePayload) {
		this.responsePayload = responsePayload;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
	
	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getAccountBalanceAfter() {
		return accountBalanceAfter;
	}

	public void setAccountBalanceAfter(String accountBalanceAfter) {
		this.accountBalanceAfter = accountBalanceAfter;
	}	
	
	public String getAccountBalanceBefore() {
		return accountBalanceBefore;
	}

	public void setAccountBalanceBefore(String accountBalanceBefore) {
		this.accountBalanceBefore = accountBalanceBefore;
	}  

	public UUID getEventId() {
		return eventId;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}
    
}
