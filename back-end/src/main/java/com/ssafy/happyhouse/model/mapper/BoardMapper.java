package com.ssafy.happyhouse.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.CommentDto;

@Mapper
public interface BoardMapper {
	List<BoardDto> selectBoard();
	BoardDto selectBoardByNo(int no);
	List<BoardDto> selectBoardByTitle(String title);
	List<BoardDto> selectBoardForMain();
	List<CommentDto> selectCommentByBoard(int no);
	int insertComment(CommentDto map);
	int deleteComment(int no);
	int updateComment(Map map);
	int countComment(int no);
	int insertBoard(Map map);
	int updateBoard(Map map);
	int deleteBoard(int no);
	int increaseHit(int no);
	
	
	List<BoardDto> selectBoardById(String id);
	List<CommentDto> selectCommentById(String id);
	CommentDto selectCommentByIdx(int idx);
	
}
