package com.highgarden.springboot_board.repository;

import com.highgarden.springboot_board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor

public class BoardRepository {
    private final SqlSessionTemplate sql;

    public void save(BoardDTO boardDTO){
        sql.insert("Board.save", boardDTO);
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
}
