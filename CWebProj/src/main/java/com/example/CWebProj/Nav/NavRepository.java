package com.example.CWebProj.Nav;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavRepository extends JpaRepository<MenuCateg, Integer> {
	
	List<MenuCateg> findAllByOrderByMenuRateAscCategoryRateAsc();
	List<MenuCateg> findByMenuName(String menuName);
	List<MenuCateg> findByCategoryName(String categoryName);
	MenuCateg findByMenuNameAndCategoryName(String menuName, String categoryName);

}
