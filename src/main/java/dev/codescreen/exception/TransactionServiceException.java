package dev.codescreen.exception;

import org.springframework.http.HttpStatus;

public class TransactionServiceException extends Exception {

	private static final long serialVersionUID = 1043809345439136951L;
	
	private HttpStatus httpStatus;

	public TransactionServiceException() {
	}

	public TransactionServiceException(String message , HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}