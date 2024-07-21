package com.example.CWebProj.Autho;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthenKeyValScheduler {

	private final AuthenKeyValRepository authenKeyValRepository;
	private static final Logger logger = LoggerFactory.getLogger(AuthenKeyVal.class);
	@Scheduled(fixedRate = 60000*10) // 60초마다 실행
	@Transactional
    public void removeExpiredData() {
        LocalDateTime now = LocalDateTime.now();
        long deletedCount = authenKeyValRepository.deleteByExpiryDateBefore(now);
        logger.info("Deleted {} expired records", deletedCount);
    }
}
