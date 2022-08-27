package com.ssafy.happyhouse.model.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.ssafy.happyhouse.model.BookMarkHouseDto;

@Mapper
public interface BookMarkMapper {
	List<BookMarkHouseDto> selectBookmarkHouse(String mb_id);
	int insertBookmarkHouse(Map map);
	int deleteBookmarkHouse(Map map);
}
