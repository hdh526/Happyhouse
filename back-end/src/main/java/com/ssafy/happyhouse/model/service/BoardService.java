package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.CommentDto;

public interface BoardService {
	List<BoardDto> selectBoard();
	BoardDto selectBoardByNo(int no);
	List<BoardDto> selectBoardByTitle(String title);
	List<BoardDto> selectBoardForMain();
	List<CommentDto> selectCommentByBoard(int no);
	CommentDto insertComment(CommentDto map);
	boolean deleteComment(int no);
	boolean updateComment(Map map);

	int countComment(int no);
	boolean insertBoard(Map map);
	boolean updateBoard(Map map);
	boolean deleteBoard(int no);
	boolean increaseHit(int no);
	
	List<BoardDto> selectBoardById(String id);
	List<CommentDto> selectCommentById(String id);
}