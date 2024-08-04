package com.app.service;

import org.springframework.data.domain.Page;

import com.app.entity.Board;

public interface BoardService {

	Long create(Board board);

	Long update(Board board);

	Long delete(Long id);

	Board findById(Long id);

	Page<Board> findAll(int page, int size);
}
