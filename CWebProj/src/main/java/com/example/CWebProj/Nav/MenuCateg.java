package com.example.CWebProj.Nav;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MenuCateg {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String menuName;
	
	private String categoryName;
	
	private Integer menuRate;
	private Integer categoryRate;
	
}