package com.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.app.entity.Board;
import com.app.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Value("${com.app.page.size}")
	private int pgsize;

	@GetMapping("/list")
	public String loadBoardList(Model model, @RequestParam("page") Optional<Integer> page) {

		int currentPage = page.orElse(1);
		var boards = this.boardService.findAll(currentPage - 1, pgsize);
		model.addAttribute("boardList", boards);
		model.addAttribute("currentPage", currentPage);

		int totalPages = boards.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		return "list";
	}

	@GetMapping("/post")
	public ModelAndView showCreateForm() {
		return new ModelAndView("create", "board", new Board());
	}

	@PostMapping("create")
	public String save(Model model, @ModelAttribute("board") Board board) {
		var result = this.boardService.create(board);
		if (null == result) {
			model.addAttribute("errorMsg", "Unable to post a new board, please try again.");
			return "create";
		}
		return "redirect:/list";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(Model model, @PathVariable("id") Optional<Long> id) {

		if (id.isPresent()) {
			var resObj = this.boardService.findById(id.get());
			model.addAttribute("board", resObj);
		} else {
			model.addAttribute("board", new Board());
		}
		return "update";
	}

	@PostMapping("update")
	public String update(Model model, @ModelAttribute("board") Board board) {

		var result = this.boardService.update(board);
		if (null == result) {
			model.addAttribute("errorMsg", "Unable to update a record, please try again.");
			return "update";
		}
		return "redirect:/list";
	}

	@GetMapping("/del/{id}")
	public String showDeleteForm(Model model, @PathVariable("id") Optional<Long> id) {

		if (id.isPresent()) {
			var resObj = this.boardService.findById(id.get());
			model.addAttribute("board", resObj);
		} else {
			model.addAttribute("board", new Board());
		}
		return "delete";
	}

	@PostMapping("delete")
	public String delete(Model model, @ModelAttribute("board") Board board) {

		var result = this.boardService.delete(board.getId());
		if (null == result) {
			model.addAttribute("errorMsg", "Unable to delete a record, please try again.");
			return "delete";
		}
		return "redirect:/list";
	}

}
