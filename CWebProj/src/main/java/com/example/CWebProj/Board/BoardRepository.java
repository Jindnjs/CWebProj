package com.example.CWebProj.Board;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findByMenuId(Integer menuId, Pageable pageable);
    Optional<Board> findByMenuIdAndId(Integer menuId, Integer boardId);
}
