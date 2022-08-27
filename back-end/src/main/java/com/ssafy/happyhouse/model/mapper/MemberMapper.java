package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.RecommendDto;

@Mapper
public interface MemberMapper {
	
	int insert(MemberDto dto);
	int naverLogin(MemberDto dto);
	List<MemberDto> selectAll();
	MemberDto select(String id);
	MemberDto selectByEmail(String email);
	int updateUserPassword(MemberDto d);
	int update(MemberDto dto);
	int updateType(Map map);
	int delete(String id);
	int idCheck(String id);
	int Check(String id);
	public MemberDto login(Map map) throws SQLException;
	public MemberDto userInfo(String id) throws SQLException;
	RecommendDto getType(int idx) throws SQLException;
}
