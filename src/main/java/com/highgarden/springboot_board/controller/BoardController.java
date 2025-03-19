package com.highgarden.springboot_board.controller;

import com.highgarden.springboot_board.dto.BoardDTO;
import com.highgarden.springboot_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor // 초기화되지 않은 final 필드를 자동으로 생성자 주입

public class BoardController {
    private final BoardService boardService;

    @GetMapping("/save")
    public String save(){
        return "save";
    }

    @PostMapping("/save")
    public String save(BoardDTO boardDTO){
        boardService.save(boardDTO);
        return "redirect:/list";
        // 리다이렉션을 통해 글작성이 성공하면 localhost:8080/list로 페이지가 넘어간다.
    }

    @GetMapping("/list")
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

}
