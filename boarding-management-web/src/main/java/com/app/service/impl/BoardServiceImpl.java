package com.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.entity.Board;
import com.app.repository.BoardRepository;
import com.app.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardRepository boardRepo;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Long create(Board board) {
		try {
			logger.info("Post Board Request : {}", board.toString());

			var resObj = this.boardRepo.save(board);

			logger.info("Board posting is successful with Id : {}", resObj.getId());
			return resObj.getId();

		} catch (Exception e) {

			logger.debug("Fail to post a board. Error : {}", e.getMessage());

		}
		return null;
	}

	@Override
	public Long update(Board board) {
		try {
			logger.info("Update Board Request : {}", board.toString());
			this.boardRepo.save(board);

			logger.info("Board updating is successful with Id : {}", board.getId());
			return board.getId();

		} catch (Exception e) {
			logger.debug("Fail to update a board. Error : {}", e.getMessage());
		}
		return null;
	}

	@Override
	public Long delete(Long id) {
		try {
			logger.info("Delete Board Request by Id : {}", id);
			this.boardRepo.deleteById(id);
			logger.info("Board deleting is successful with Id : {}", id);
			return id;

		} catch (Exception e) {
			logger.debug("Fail to delete a board. Error : {}", e.getMessage());
		}
		return null;
	}

	@Override
	public Board findById(Long id) {
		try {
			logger.info("Retrieve Board Request by Id : {}", id);
			var resObj = this.boardRepo.findById(id);

			if (resObj.isEmpty()) {
				logger.info("No board entity was found to retrieve for Id : {} ", id);
				return new Board();
			}
			logger.info("Board retrieve is successful with Id : {}", id);
			return resObj.get();

		} catch (Exception e) {
			logger.debug("Fail to retrieve board by Id : Error {}", e.getMessage());
		}
		return null;
	}

	@Override
	public Page<Board> findAll(int page, int size) {
		try {

			logger.info("Retrieve All Boards Request ...");
			Pageable pageable = PageRequest.of(page, size);
			var boards = this.boardRepo.findAll(pageable);

			logger.info("All boards retrieving is successful.");
			return boards;

		} catch (Exception e) {
			logger.debug("Fail to retrieve all boards. Error : {}", e.getMessage());
		}
		return null;
	}

}
