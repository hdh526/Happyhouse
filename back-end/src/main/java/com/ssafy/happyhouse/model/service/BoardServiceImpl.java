package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.CommentDto;
import com.ssafy.happyhouse.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardmapper;
	
	@Override
	public List<BoardDto> selectBoard(){
		return boardmapper.selectBoard();
	}

	@Override
	public BoardDto selectBoardByNo(int no){
		return boardmapper.selectBoardByNo(no);
	}

	@Override
	public List<BoardDto> selectBoardByTitle(String title){
		return boardmapper.selectBoardByTitle(title);
	}

	@Override
	@Transactional
	public boolean insertBoard(Map map){
		return boardmapper.insertBoard(map) == 1;
	}

	@Override
	@Transactional
	public boolean updateBoard(Map map){
		return boardmapper.updateBoard(map) == 1;
	}

	@Override
	@Transactional
	public boolean deleteBoard(int no){
		return boardmapper.deleteBoard(no) == 1;
	}
	
	@Override
	@Transactional
	public boolean increaseHit(int no) {
		return boardmapper.increaseHit(no) == 1;
	}

	@Override
	public List<BoardDto> selectBoardForMain() {
		return boardmapper.selectBoardForMain();
	}
	
	@Override
	public List<CommentDto> selectCommentByBoard(int no) {
		return boardmapper.selectCommentByBoard(no);
	}
	
	@Override
	@Transactional
	public CommentDto insertComment(CommentDto map) {
		boardmapper.insertComment(map);
		return boardmapper.selectCommentByIdx(map.getCm_idx());
	}
	
	@Override
	@Transactional
	public boolean deleteComment(int no) {
		return boardmapper.deleteComment(no) == 1;
	} 
	
	@Override
	@Transactional
	public boolean updateComment(Map map) {
		// TODO Auto-generated method stub
		return boardmapper.updateComment(map) == 1;
	}
	
	@Override
	public List<BoardDto> selectBoardById(String id) {
		return boardmapper.selectBoardById(id);
	}
	
	@Override
	public List<CommentDto> selectCommentById(String id) {
		return boardmapper.selectCommentById(id);
	}
	
	@Override
	public int countComment(int no) {
		// TODO Auto-generated method stub
		return boardmapper.countComment(no);
	}

}
