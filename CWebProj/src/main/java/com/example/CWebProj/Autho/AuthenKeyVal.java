package com.example.CWebProj.Autho;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AuthenKeyVal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	 @Column(nullable = false)
    private String uuid;

	private String email;
	
    @Column(nullable = false)
    private LocalDateTime expiryDate;
	
}
