package com.example.CWebProj.AutoList;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoListRepository extends JpaRepository<AutoList, Integer> {
    List<AutoList> findByBoardId(Integer boardId);
    List<AutoList> findByFunc(String func);
}
