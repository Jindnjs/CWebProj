package com.example.CWebProj.Board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findAll(Pageable pageable);
    Page<Board> findByMenuIdAndNoticeFalseOrderByCreateDateDesc(Integer menuId, Pageable pageable);
    List<Board> findByMenuIdAndNoticeTrueOrderByCreateDateDesc(Integer menuId);
}
