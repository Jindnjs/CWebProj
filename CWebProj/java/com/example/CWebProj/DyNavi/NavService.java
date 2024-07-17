package com.example.CWebProj.DyNavi;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NavService {
	
	private final NavRepository navRepository;

    public List<MenuCateg> getAllMenuCategories() {
        return navRepository.findAllByOrderByMenuRateAscCategoryRateAsc();
    }
    public MenuCateg getMenu(Integer id) {
    	
    	Optional<MenuCateg> om = navRepository.findById(id);
    	return om.get();
    }
    public List<MenuCateg> getSidebar(Integer id){
    	String menuName = this.getMenu(id).getMenuName();
    	return navRepository.findByMenuNameOrderByCategoryRate(menuName);

    }
}