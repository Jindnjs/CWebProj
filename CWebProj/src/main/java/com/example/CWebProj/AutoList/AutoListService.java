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

    public List<AutoList> getRolesByBoardId(Integer boardId) {
        return autoListRepository.findByBoardId(boardId);
    }

    public String getRolesAsStringByBoardId(Integer boardId) {
        List<AutoList> roles = getRolesByBoardId(boardId);
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
    
    public boolean checkRole(Integer boardId) {
    	
    	CUser currentUser = cuserService.authen();
    	String userRole;
    	if(currentUser == null)
    		userRole = "ROLE_ANONYMOUS";
    	else
    		userRole = currentUser.getRole();
    	String roles = getRolesAsStringByBoardId(boardId);
    	String[] roleArray = roles.replace("'", "").split(",");
    	for (String role : roleArray) {
            if (userRole.equals("ROLE_" + role.trim())) {
                return true;
            }
        }
    	return false;
    }
}
