package com.example.CWebProj.Autho;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthenKeyValService {

	private final AuthenKeyValRepository authenKeyValRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenKeyVal.class);
	
	public void create(String uuid, String email, long ttl) {
		
		AuthenKeyVal authenKeyVal = new AuthenKeyVal();
		authenKeyVal.setEmail(email);
		authenKeyVal.setUuid(uuid);
		authenKeyVal.setExpiryDate(LocalDateTime.now().plus(ttl, ChronoUnit.MINUTES));
		authenKeyValRepository.save(authenKeyVal);
		logger.info("Saved data with TTL of {} minutes", ttl);
	}
	public String getValue(String uuid) {
		return authenKeyValRepository.findByUuid(uuid).getEmail();
	}
	public void delete(String uuid) {
		authenKeyValRepository.delete(authenKeyValRepository.findByUuid(uuid));
	}
	
}
