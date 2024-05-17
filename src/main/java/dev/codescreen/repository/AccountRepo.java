package dev.codescreen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.codescreen.entity.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, String>{
	
	

}