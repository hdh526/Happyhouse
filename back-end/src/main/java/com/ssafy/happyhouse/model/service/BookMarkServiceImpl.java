package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.BookMarkHouseDto;
import com.ssafy.happyhouse.model.mapper.BookMarkMapper;

@Service
public class BookMarkServiceImpl implements BookMarkService {
	
	@Autowired
	private BookMarkMapper bookmapper;

	@Override
	@Transactional
	public boolean insertBookmark(Map map){
		return bookmapper.insertBookmarkHouse(map)==1;
	}
	
	@Override
	public List<BookMarkHouseDto> selectBookmark(String mb_id){
		return bookmapper.selectBookmarkHouse(mb_id);
	}
	
	@Override
	public boolean deleteBookmark(Map map) {
		return bookmapper.deleteBookmarkHouse(map) == 1;
	}

}
