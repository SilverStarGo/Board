package com.highgarden.springboot_board.repository;

import com.highgarden.springboot_board.dto.BoardDTO;
import com.highgarden.springboot_board.dto.BoardFileDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor

public class BoardRepository {
    private final SqlSessionTemplate sql;

    public BoardDTO save(BoardDTO boardDTO){
        sql.insert("Board.save", boardDTO);
        return boardDTO;
    }

    public List<BoardDTO> findAll(){
        System.out.println("findAll");
        return sql.selectList("Board.findAll");
    }

    public void updateHits(Long id){
        sql.update("Board.updateHits", id);
    }
    public BoardDTO findById(Long id){ //조회수 기능은 반환값이 따로 필요없지만
        // 상세조회는 글내용에 대한 데이터를 반환해야 하기 때문에 sql.selectOne으로 데이터를 받아 BoardDTO객체에 담아 반환
        return sql.selectOne("Board.findById", id);
    }

    public void update(BoardDTO boardDTO){
        sql.update("Board.update", boardDTO);
    }

    public void delete(Long id){
        sql.delete("Board.delete", id);
    }

    // saveFile 메서드는 save 메서드를 처리할 때만 사용되는 메서드이기 때문에 controller 단에서 생성할 필요가 없음
    // repository 단에서만 선언하고 파일이 있는 경우에만 사용
    public void saveFile(BoardFileDTO boardFileDTO){
        sql.insert("Board.saveFile", boardFileDTO);
    }
    public List<BoardFileDTO> findFile(Long id){
        return sql.selectList("Board.findFile", id);
    }
}
