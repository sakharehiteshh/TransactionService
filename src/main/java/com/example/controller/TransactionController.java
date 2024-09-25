package com.example.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.ErrorResponse;
import com.example.entity.Persistence;
import com.example.entity.Ping;
import com.example.entity.Request;
import com.example.entity.Response;
import com.example.exception.TransactionServiceException;
import com.example.repository.EventPersistRepo;
import com.example.service.AuthorizationLoadService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class TransactionController {
	
	@Autowired
	private AuthorizationLoadService service;
	
	@Autowired
	private EventPersistRepo eventPersistRepo;
	
	@GetMapping("/ping")
	public ResponseEntity<Object> ping() {
		
		try {
			ZoneId eastern = ZoneId.of("America/New_York");
		    ZonedDateTime now = ZonedDateTime.now(eastern);
			Ping ping = new Ping(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));
			return ResponseEntity.ok(ping);
		} catch (Exception e) {
			return	new ResponseEntity<Object>(new ErrorResponse(e.getMessage(),"500"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/authorization/{messageId}")
	public ResponseEntity<Object> authorization(@PathVariable(name = "messageId") String messageId , @RequestBody Request req ,HttpServletRequest httpServletRequest ) throws Exception {
		Persistence eventPersist = new Persistence();
		try {
			
			eventPersist.setEventType("authorization");
			service.setInitialEventParameter(req, eventPersist);
			validateReq(req, httpServletRequest , eventPersist);
			Response res =  service.doAuthorization(req,eventPersist);
			service.setFinalSuccessEventParameter(res, eventPersist);
			return ResponseEntity.ok(res);
		}
		catch(TransactionServiceException e) {
			String statusCode = Integer.toString(e.getHttpStatus().value());
			service.setErrorDetailInEventParameter(eventPersist,e,statusCode);
			return	new ResponseEntity<Object>(new ErrorResponse(e.getMessage(),statusCode), e.getHttpStatus());
		}
		catch(Exception e){
			service.setErrorDetailInEventParameter(eventPersist,e,"500");
			return new ResponseEntity<Object>(new ErrorResponse(e.getMessage(), "500"), HttpStatusCode.valueOf(500));
		}
		finally {
			eventPersistRepo.save(eventPersist);
		}
	}
	
	@PutMapping("/load/{messageId}")
	public ResponseEntity<Object> load(@PathVariable(name = "messageId") String messageId ,@Validated @RequestBody Request req , HttpServletRequest httpServletRequest) throws Exception {
		Persistence eventPersist = new Persistence();
		try {
			eventPersist.setEventType("load");
			validateReq(req , httpServletRequest,eventPersist);
			service.setInitialEventParameter(req, eventPersist);
			Response res = service.doLoad(req,eventPersist);
			service.setFinalSuccessEventParameter(res, eventPersist);
			return ResponseEntity.ok(res);
		}
		catch(TransactionServiceException e) {
			String statusCode = Integer.toString(e.getHttpStatus().value());
			service.setErrorDetailInEventParameter(eventPersist,e,statusCode);
			return	new ResponseEntity<Object>(new ErrorResponse(e.getMessage(),Integer.toString(e.getHttpStatus().value())), e.getHttpStatus());
		}
		catch(Exception e){
			service.setErrorDetailInEventParameter(eventPersist,e,"500");
			return new ResponseEntity<Object>(new ErrorResponse(e.getMessage(), "500"), HttpStatusCode.valueOf(500));
		}
		finally {
			eventPersistRepo.save(eventPersist);
		}
		
	}
	
	public void validateReq(Request req ,HttpServletRequest httpServletRequest , Persistence eventPersist) throws TransactionServiceException {
		TransactionServiceException transactionServiceBadReqException = new TransactionServiceException("Bad Request",HttpStatus.BAD_REQUEST);
		TransactionServiceException  transactionServiceBadReqAmountException = new TransactionServiceException("Bad Request , Invalid Amount",HttpStatus.BAD_REQUEST);
		
		boolean reqFieldCheck = req.getMessageId() == null || req.getMessageId().isEmpty() || req.getUserId() == null || req.getUserId().isEmpty() || req.getTransactionAmount() == null || req.getTransactionAmount().getAmount() == null || req.getTransactionAmount().getCurrency() == null || req.getTransactionAmount().getDebitOrCredit() == null;
		if(reqFieldCheck){
			throw transactionServiceBadReqException;
		}
		
		boolean negativeAmountCheck = false;
		if(req.getTransactionAmount().getAmount().contains(".") && req.getTransactionAmount().getAmount().split("[.]")[1].length()>2) {
			throw transactionServiceBadReqAmountException;
		}
		try {
			Double.parseDouble(req.getTransactionAmount().getAmount());
			 negativeAmountCheck = Double.parseDouble(req.getTransactionAmount().getAmount()) <0;
			
		}catch (Exception e) {
			throw transactionServiceBadReqAmountException;
		}
		boolean currencyConstraintCheck = !req.getTransactionAmount().getCurrency().equalsIgnoreCase("USD");
		boolean loadCreditConstaintCheck = httpServletRequest.getRequestURI().contains("load") && !req.getTransactionAmount().getDebitOrCredit().equalsIgnoreCase("credit");
		boolean authorizationDebitConstraintCheck = httpServletRequest.getRequestURI().contains("authorization") && !req.getTransactionAmount().getDebitOrCredit().equalsIgnoreCase("debit");
		
		
		
		if(negativeAmountCheck) {
			throw transactionServiceBadReqAmountException;
		}
		
		if( currencyConstraintCheck || loadCreditConstaintCheck || authorizationDebitConstraintCheck){
			throw transactionServiceBadReqException;
		}
		
	}

}

