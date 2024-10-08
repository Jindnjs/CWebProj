package com.example.CWebProj.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CUserRepository extends JpaRepository<CUser, Integer> {
	Optional<CUser> findByUsername(String username);
	List<CUser> findByRoleNot(String role);
}
