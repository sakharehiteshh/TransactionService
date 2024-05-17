package dev.codescreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import dev.codescreen.controller.TransactionController;
import dev.codescreen.repository.EventPersistRepo;
import dev.codescreen.service.AuthorizationLoadService;

@SpringBootApplication
@ComponentScan(basePackageClasses = {TransactionController.class,AuthorizationLoadService.class,EventPersistRepo.class} )
public class TransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionServiceApplication.class, args);
	}

}
