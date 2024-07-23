package com.example.CWebProj.Board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findAll(Pageable pageable);
    Page<Board> findByMenuIdAndNoticeFalseOrderByCreateDateDesc(Integer menuId, Pageable pageable);
    List<Board> findByMenuIdAndNoticeTrueOrderByCreateDateDesc(Integer menuId);
    Page<Board> findByMenuId(Integer menuId, Pageable pageable);
    Optional<Board> findByMenuIdAndId(Integer menuId, Integer boardId);
    
    List<Board> findTop4ByMenuIdOrderByCreateDateDesc(Integer menuId);
    Optional<Board> findByMenuId(Integer menuId);
}
