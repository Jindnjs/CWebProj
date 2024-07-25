package com.example.CWebProj.DyNavi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NavRepository extends JpaRepository<MenuCateg, Integer> {
	
	List<MenuCateg> findAllByOrderByMenuRateAscCategoryRateAsc();
	List<MenuCateg> findByMenuNameOrderByCategoryRate(String menuName);
	List<MenuCateg> findByCategoryName(String categoryName);
	MenuCateg findByMenuNameAndCategoryName(String menuName, String categoryName);

	List<MenuCateg> findByMenuName(String menuName);

	List<MenuCateg> findAllByOrderByMenuRateDesc();
	List<MenuCateg> findAllByMenuNameOrderByCategoryRateDesc(String menuName);
	
}
