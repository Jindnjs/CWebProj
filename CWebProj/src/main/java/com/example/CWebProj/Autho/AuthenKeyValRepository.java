package com.example.CWebProj.Autho;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenKeyValRepository extends JpaRepository<AuthenKeyVal, Integer> {
	long deleteByExpiryDateBefore(LocalDateTime expiryDate);
	AuthenKeyVal findByUuid(String uuid);
}
