package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.example.controller.TransactionController;
import com.example.repository.EventPersistRepo;
import com.example.service.AuthorizationLoadService;

@SpringBootApplication
@ComponentScan(basePackageClasses = {TransactionController.class,AuthorizationLoadService.class,EventPersistRepo.class} )
public class TransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionServiceApplication.class, args);
	}

}
