package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.MailDto;
import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.RecommendDto;

public interface MemberService {
	
	int insert(MemberDto dto) throws Exception;
	int naverLogin(MemberDto dto) throws Exception;
	List<MemberDto> selectAll() throws Exception;
	MemberDto select(String id) throws Exception;
	int update(MemberDto dto) throws Exception;
	MemberDto selectByEmail(String email) throws SQLException;
	int updateUserPassword(String id, String pw) throws SQLException;
	int delete(String id) throws Exception;
	int idCheck(String checkId) throws Exception;
	int Check(String id) throws Exception;
	public MemberDto login(String id, String password) throws Exception;
	public MemberDto userInfo(String id) throws Exception;
	int updateType(Map map);
	RecommendDto getType(int idx) throws Exception;
	MailDto createMailAndChangePassword(String memberEmail) throws Exception;
	void updatePassword(String str, String userEmail) throws Exception;
	String getTempPassword() throws Exception;
	void mailSend(MailDto mailDto) throws Exception;
}
