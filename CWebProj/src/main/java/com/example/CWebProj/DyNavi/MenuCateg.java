package com.example.CWebProj.DyNavi;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class MenuCateg {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String menuName;
	
	private String categoryName;
	
	private Integer menuRate;
	private Integer categoryRate;
	
	private String boardLink;
	
}
