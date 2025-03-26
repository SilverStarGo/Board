package com.highgarden.springboot_board.service;

import com.highgarden.springboot_board.dto.BoardDTO;
import com.highgarden.springboot_board.dto.BoardFileDTO;
import com.highgarden.springboot_board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor

public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        if(boardDTO.getBoardFile().get(0).isEmpty()){
            // 파일이 없을 때
            boardDTO.setFileAttached(0);
            boardRepository.save(boardDTO);
        }else {
            // 파일이 존재할 때
            boardDTO.setFileAttached(1);
            // board를 먼저 insert
            BoardDTO savedBoard = boardRepository.save(boardDTO);
            // 파일처리 후 boardfile insert
            for(MultipartFile boardFile : boardDTO.getBoardFile()){
                String originalFileName = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis()+ "_" + originalFileName;

                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFileName);
                boardFileDTO.setStoredFileName(storedFileName);
                boardFileDTO.setBoardId(savedBoard.getId());

                String savePath = "D:/Board/springboot-board/src/main/resources/upload_files/" + storedFileName;
                boardFile.transferTo(new File(savePath)); // 실질적으로 파일이 저장되는 코드
                boardRepository.saveFile(boardFileDTO);
            }
        }
    }
    public List<BoardFileDTO> findFile(Long id) {
        return boardRepository.findFile(id);
    }

    public List<BoardDTO> findAll(){
        return boardRepository.findAll();
    }

    public void updateHits(Long id){
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id){
        return boardRepository.findById(id);
    }

    public void update(BoardDTO boardDTO){
        boardRepository.update(boardDTO);
    }

    public void delete(Long id){
        boardRepository.delete(id);
    }

}
