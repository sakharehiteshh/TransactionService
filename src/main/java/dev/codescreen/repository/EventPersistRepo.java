package dev.codescreen.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.codescreen.entity.Persistence;

public interface EventPersistRepo extends JpaRepository<Persistence, UUID> {

}
