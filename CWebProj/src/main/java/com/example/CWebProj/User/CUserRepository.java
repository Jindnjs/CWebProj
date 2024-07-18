package com.example.CWebProj.User;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface CUserRepository extends JpaRepository<CUser, Integer> {
	Optional<CUser> findByUsername(String username); // login check
}
