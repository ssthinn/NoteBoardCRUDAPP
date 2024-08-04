package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
	
	Board findByName(String name);
}
