package com.example.CWebProj.Nav;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.ProjSelPrac.Menu.Menu;
import com.mysite.ProjSelPrac.Menu.MenuRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NavService {
	
	private final NavRepository menurepo;

    public List<MenuCateg> getAllMenuCategories() {
        return menurepo.findAllByOrderByMenurateAscCaterateAsc();
    }
    
    public int getMenuIdx(String menu) {
    	
    	List<MenuCateg> all = menurepo.findByMenu(menu);
        if(all.isEmpty()) {
        	all = menurepo.findAllByOrderByMenurateDesc();
        	if(all.isEmpty()) {
        		return 1;
        	}
        	return all.get(0).getMenurate()+1;
        }
    	
        MenuCateg m = all.get(0);
    	return m.getMenurate();
    }
    public int getCateIdx(String cate,String menu) {
    	
    	List<MenuCateg> all = menurepo.findByMenu(menu);
        if(all.isEmpty()) {
        	return 1;
        }
        all = menurepo.findAllByMenuOrderByCaterateDesc(menu);
        MenuCateg m = all.get(0);
    	return m.getCaterate()+1;
    }
    public void addMenu(String menu,String cate) {
    	MenuCateg m = new Menu();
    	m.setMenu(menu);
    	m.setCategoty(cate);
    	m.setMenurate(this.getMenuIdx(menu));
    	m.setCaterate(this.getCateIdx(cate,menu));
    	menurepo.save(m);
    	
    }
    public void delMenu(String menu,String cate) {
    	MenuCateg m = this.menurepo.findByMenuAndCategoty(menu, cate);
    	this.menurepo.delete(m);
    }
}