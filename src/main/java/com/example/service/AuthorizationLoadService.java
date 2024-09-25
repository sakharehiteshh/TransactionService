package com.example.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.entity.Account;
import com.example.entity.Persistence;
import com.example.entity.Request;
import com.example.entity.Response;
import com.example.entity.TransactionAmount;
import com.example.exception.TransactionServiceException;
import com.example.repository.AccountRepo;

@Service
public class AuthorizationLoadService {
	
	@Autowired
	private AccountRepo accountRepo;
	
	public void setAccountRepo(AccountRepo accountRepo) {
		this.accountRepo = accountRepo;
	}
	
	public Response doAuthorization(Request req,Persistence eventPersist) throws Exception {
		Optional<Account> acc = accountRepo.findById(req.getUserId());
		Response res = new Response();
		res.setResponseCode("Declined");
		eventPersist.setAuthorizationStatus("Declined");
		if(acc.isEmpty()) {
			throw new TransactionServiceException("UserId not found", HttpStatus.NOT_FOUND);
		}
		if(acc.get().getAmount()>=Double.parseDouble(req.getTransactionAmount().getAmount())) {
			eventPersist.setAccountBalanceBefore(Double.toString(acc.get().getAmount()));
			acc.get().setAmount(round(acc.get().getAmount()-Double.parseDouble(req.getTransactionAmount().getAmount()), 2));
			res.setResponseCode("Approved");
			eventPersist.setAuthorizationStatus("Approved");
			accountRepo.save(acc.get());
		}
		eventPersist.setAccountBalanceAfter(Double.toString(acc.get().getAmount()));
		res.setMessageId(req.getMessageId());
		res.setUserId(req.getUserId());
		res.setBalance(new TransactionAmount(Double.toString(acc.get().getAmount()), req.getTransactionAmount().getCurrency(), req.getTransactionAmount().getDebitOrCredit()));
		return res;
	}

	public Response doLoad(Request req,Persistence eventPersist) throws Exception {
		// TODO Auto-generated method stub
		Optional<Account> acc = accountRepo.findById(req.getUserId());
		if(acc.isEmpty()) {
			throw new TransactionServiceException("UserId not found", HttpStatus.NOT_FOUND);
		}
		eventPersist.setAccountBalanceBefore(Double.toString(acc.get().getAmount()));
		acc.get().setAmount(acc.get().getAmount()+Double.parseDouble(req.getTransactionAmount().getAmount()));
		eventPersist.setAccountBalanceAfter(Double.toString(acc.get().getAmount()));
		accountRepo.save(acc.get());
		Response res = new Response();
		res.setMessageId(req.getMessageId());
		res.setUserId(req.getUserId());
		res.setBalance(new TransactionAmount(Double.toString(acc.get().getAmount()), req.getTransactionAmount().getCurrency(), req.getTransactionAmount().getDebitOrCredit()));
		return res;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public void setInitialEventParameter(Request req , Persistence eventPersist) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		eventPersist.setUserId(req.getUserId());
		eventPersist.setMessageId(req.getMessageId());
		eventPersist.setTransactionAmount(req.getTransactionAmount().getAmount());
		eventPersist.setRequestPayload(mapper.writeValueAsString(req));
	}
	
	public void setErrorDetailInEventParameter(Persistence eventPersist , Exception e , String  statusCode) throws Exception {
		eventPersist.setErrorReason(e.getMessage());
		eventPersist.setTransactionStatus("Failed");
		eventPersist.setStatusCode(statusCode);
		eventPersist.setException(e.getClass().getSimpleName());
	}
	
	public void setFinalSuccessEventParameter(Response res , Persistence eventPersist) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		eventPersist.setResponsePayload(mapper.writeValueAsString(res));
		eventPersist.setStatusCode("200");
		eventPersist.setTransactionStatus("Success");
	}

}