package com.example.service;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;

import com.example.entity.Persistence;
import com.example.entity.Request;
import com.example.entity.Response;
import com.example.exception.TransactionServiceException;
import com.example.repository.AccountRepo;
import com.example.Util.CommonUtil;

@DataJpaTest
public class AuthorizationLoadServiceTest {
	
	@Autowired
	AccountRepo repo;
	
	
	
	@Autowired
	AuthorizationLoadService service;

	
	@Test
	public void doLoadAndAuthorizationTest() throws Exception {
		try {
			Request reqLoad = CommonUtil.getReqObjFromFile("LoadReq.json");
			
			Persistence eventPersist = new Persistence();
			service.setAccountRepo(repo);
			
			
			Response resLoad = service.doLoad(reqLoad, eventPersist);
			assertEquals(Double.parseDouble(resLoad.getBalance().getAmount()),1500d); 
			
			Request reqAuthorization = CommonUtil.getReqObjFromFile("AuthorizationReq.json");
			
			Response resAuth = service.doAuthorization(reqAuthorization, eventPersist);
			assertEquals(Double.parseDouble(resAuth.getBalance().getAmount()), 500d);
			assertEquals(resAuth.getResponseCode(), "Approved");
			
			reqAuthorization.getTransactionAmount().setAmount("1000.00");
			Response resAuthDeclined = service.doAuthorization(reqAuthorization, eventPersist);
			assertEquals(Double.parseDouble(resAuthDeclined.getBalance().getAmount()), 500d);
			assertEquals(resAuthDeclined.getResponseCode(), "Declined");
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void roundTest() {
		double round = AuthorizationLoadService.round(100.34999d, 2);
		assertEquals(round, 100.35d);
	}
	
	@Test
	public void setInitialEventParameterTest() throws Exception {
		Request reqLoad = CommonUtil.getReqObjFromFile("LoadReq.json");
		Persistence eventPersistence = new Persistence();
		
		service.setInitialEventParameter(reqLoad, eventPersistence);
		
		assertThatNoException();
	}
	
	@Test
	public void setErrorDetailInEventParameterTest() throws Exception {
		TransactionServiceException e = new TransactionServiceException("Bad Req", HttpStatus.BAD_REQUEST);
		Persistence eventPersistence = new Persistence();
		
		service.setErrorDetailInEventParameter(eventPersistence,e,"400");
		
		assertThatNoException();
	}
	
	@Test
	public void setFinalSuccessEventParameterTest() throws Exception {
		Response res  = new Response();
		Persistence eventPersistence = new Persistence();
		service.setFinalSuccessEventParameter(res,eventPersistence);
		
		assertThatNoException();
	}
	
	

}
