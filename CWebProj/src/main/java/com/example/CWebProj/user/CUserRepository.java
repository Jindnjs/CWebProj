package com.example.CWebProj.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CUserRepository extends JpaRepository<CUser, Integer> {
	Optional<CUser> findByusername(String username); // login check
}
