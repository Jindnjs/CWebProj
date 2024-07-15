package com.example.CWebProj.Nav;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NavService {
	
	private final NavRepository navRepository;

    public List<MenuCateg> getAllMenuCategories() {
        return navRepository.findAllByOrderByMenuRateAscCategoryRateAsc();
    }
}