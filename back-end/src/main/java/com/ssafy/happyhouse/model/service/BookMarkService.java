package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.BookMarkHouseDto;

public interface BookMarkService {

	List<BookMarkHouseDto> selectBookmark(String id);
	boolean insertBookmark(Map map);
	boolean deleteBookmark(Map map);
}
