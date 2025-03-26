package com.highgarden.springboot_board.controller;

import com.highgarden.springboot_board.dto.BoardDTO;
import com.highgarden.springboot_board.dto.BoardFileDTO;
import com.highgarden.springboot_board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
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
    public String save(BoardDTO boardDTO) throws IOException { //IOException이 발생할 수 있으므로 예외를 throws로 던짐.
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

    @GetMapping("/{id}")
    public String findById(@PathVariable("id")Long id, Model model){ //PathVariable : 자동으로 url요청을 변수에 담을 수 있다.
        // 조회수 처리
        boardService.updateHits(id);
        // 상세내용 가져오기
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        // 파일첨부 추가, 사진파일이 있다면 파일 리스트를 찾아 model에 함께 담아주는 로직 추가
        if(boardDTO.getFileAttached() == 1){
            List<BoardFileDTO> boardFileDTOList = boardService.findFile(id);
            model.addAttribute("boardFileDTOList", boardFileDTOList);
        }
        return "detail";
    }

    // 수정버튼 클릭시 수정화면으로 넘어가도록 하는 메서드(GET)
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id")Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "update";
    }
    // DB에 실질적으로 수정내용을 요청하는 메서드(POST)
    @PostMapping("/update/{id}")
    public String update(BoardDTO boardDTO, Model model){
        // update 요청
        boardService.update(boardDTO);
        // findById로 수정된 내용을 다시조회
        BoardDTO dto = boardService.findById(boardDTO.getId());
        model.addAttribute("board", dto);
        return "detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        boardService.delete(id);
        return "redirect:/list"; // redirect:/list로 하는 이유는 /list로 요청하게 되면 정적인 페이지를 반환하여 글목록이 보이지 않기 때문
    }

}
