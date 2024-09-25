package com.example.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Persistence;

public interface EventPersistRepo extends JpaRepository<Persistence, UUID> {

}
