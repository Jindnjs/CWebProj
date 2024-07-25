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
    
    
    public int getMenuIdx(String menu) {
    	
    	List<MenuCateg> all = navRepository.findByMenuName(menu);
        if(all.isEmpty()) {
        	all = navRepository.findAllByOrderByMenuRateDesc();
        	if(all.isEmpty()) {
        		return 1;
        	}
        	return all.get(0).getMenuRate()+1;
        }
    	
        MenuCateg m = all.get(0);
    	return m.getMenuRate();
    }
    public int getCateIdx(String cate,String menu) {
    	
    	List<MenuCateg> all = navRepository.findByMenuName(menu);
        if(all.isEmpty()) {
        	return 1;
        }
        all = navRepository.findAllByMenuNameOrderByCategoryRateDesc(menu);
        MenuCateg m = all.get(0);
    	return m.getCategoryRate()+1;
    }
    public void addMenu(String menu,String cate) {
    	MenuCateg m = new MenuCateg();
    	m.setMenuName(menu);
    	m.setCategoryName(cate);
    	m.setMenuRate(this.getMenuIdx(menu));
    	m.setCategoryRate(this.getCateIdx(cate,menu));
    	m.setBoardLink("/form2");
    	navRepository.save(m);
    	
    }
}