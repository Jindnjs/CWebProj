package com.example.CWebProj.AutoList;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoListService {

    @Autowired
    private AutoListRepository autoListRepository;

    public List<AutoList> getRolesByBoardId(Integer boardId) {
        return autoListRepository.findByBoardId(boardId);
    }

    public String getRolesAsStringByBoardId(Integer boardId) {
        List<AutoList> roles = getRolesByBoardId(boardId);
        return roles.stream()
                    .map(AutoList::getRole)
                    .collect(Collectors.joining("','", "'", "'"));
    }

    public String getAdminOrManagerRoles(Integer boardId) {
        List<AutoList> roles = getRolesByBoardId(boardId);
        return roles.stream()
                    .filter(role -> "ADMIN".equals(role.getRole()) || "MANAGER".equals(role.getRole()))
                    .map(AutoList::getRole)
                    .collect(Collectors.joining("','", "'", "'"));
    }
}
