package com.example.CWebProj.AutoList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CWebProj.User.CUser;
import com.example.CWebProj.User.CUserService;

@Service
public class AutoListService {

    @Autowired
    private AutoListRepository autoListRepository;
    
    @Autowired
    private CUserService cuserService;

    public String getRolesAsStringByBoardId(Integer menuId) {
        List<AutoList> roles = autoListRepository.findByBoardId(menuId);
        return roles.stream()
                    .map(AutoList::getRole)
                    .collect(Collectors.joining("','", "'", "'"));
    }
    
    public String getRolesByFunction(String func) {
        List<AutoList> roles = autoListRepository.findByFunc(func);
        return roles.stream()
                    .map(AutoList::getRole)
                    .collect(Collectors.joining("','", "'", "'"));
    }
    
    public String getRolesByIdAndFunc(Integer menuId, String func) {
        List<AutoList> roles = autoListRepository.findByBoardIdAndFunc(menuId, func);
        return roles.stream()
                    .map(AutoList::getRole)
                    .collect(Collectors.joining("','", "'", "'"));
    }
    
    public boolean checkRoleByFunc(String func) {
    	
    	CUser currentUser = cuserService.authen();
    	String userRole;
    	if(currentUser == null)
    		userRole = "ROLE_ANONYMOUS";
    	else
    		userRole = currentUser.getRole();
    	String roles = getRolesByFunction(func);
    	String[] roleArray = roles.replace("'", "").split(",");
    	for (String role : roleArray) {
            if (userRole.equals("ROLE_" + role.trim())) {
                return true;
            }
        }
    	return false;
    }
    
public boolean checkRoleByIdAndFunc(Integer menuId, String func) {
    	
    	CUser currentUser = cuserService.authen();
    	String userRole;
    	if(currentUser == null)
    		userRole = "ROLE_ANONYMOUS";
    	else
    		userRole = currentUser.getRole();
    	String roles = getRolesByIdAndFunc(menuId, func);
    	String[] roleArray = roles.replace("'", "").split(",");
    	for (String role : roleArray) {
            if (userRole.equals("ROLE_" + role.trim())) {
                return true;
            }
        }
    	return false;
    }
}
