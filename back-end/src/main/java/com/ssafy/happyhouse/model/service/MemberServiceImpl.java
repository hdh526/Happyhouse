package com.ssafy.happyhouse.model.service;

import java.lang.reflect.Member;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.MailDto;
import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.RecommendDto;
import com.ssafy.happyhouse.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	private static final String FROM_ADDRESS = "hdh526@naver.com";
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public int insert(MemberDto dto) throws Exception{
		return memberMapper.insert(dto);
	}
	
	@Override
	public int naverLogin(MemberDto dto) throws Exception{
		return memberMapper.insert(dto);
	}

	@Override
	public List<MemberDto> selectAll() throws Exception{
		return memberMapper.selectAll();
	}

	@Override
	public MemberDto select(String id) throws Exception{
		return memberMapper.select(id);
	}

	@Override
	public int update(MemberDto dto) throws Exception{
		return memberMapper.update(dto);
	}

	@Override
	public int delete(String id) throws Exception{
		return memberMapper.delete(id);
	}
	
	@Override
	public int idCheck(String checkId) throws Exception{
		return memberMapper.idCheck(checkId);
	}
	
	@Override
	public int Check(String check) throws Exception{
		return memberMapper.Check(check);
	}
	
	@Override
	public MemberDto login(String id, String password) throws Exception {
		if(id == null || password == null)
			return null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("password", password);
		
		return memberMapper.login(map);
	}

	@Override
	public MemberDto userInfo(String id) throws Exception {
		return memberMapper.userInfo(id);
	}
	
	@Override
	public int updateType(Map map){
		return memberMapper.updateType(map);
	}
	
	@Override
	public RecommendDto getType(int idx) throws SQLException {
		return memberMapper.getType(idx);
	}
	
	@Override
	@Transactional
	public int updateUserPassword(String id, String pw) throws SQLException{
		MemberDto d = new MemberDto();
		d.setPassword(pw);
		d.setId(id);
		return memberMapper.updateUserPassword(d);
	};
	
	
	@Override
	public MailDto createMailAndChangePassword(String memberEmail) throws Exception {
		String str = getTempPassword();
		MailDto dto = new MailDto();
		dto.setAddress(memberEmail);
		dto.setTitle("HaeBang 임시 비밀번호 안내 이메일입니다.");
		dto.setMessage("안녕하세요, HeaBang 임시비밀번호 안내 관련 이메일입니다." + "회원님의 임시 비밀번호는 " + str + "입니다. " + "로그인 후에 비밀번호를 변경해주세요.");
		updatePassword(str, memberEmail);
		return dto;
	}
	
	@Override
	public void updatePassword(String str, String userEmail) throws Exception {
		String password = str;
		String memberId = memberMapper.selectByEmail(userEmail).getId();
		updateUserPassword(memberId, password);
	}
	
	@Override
	public String getTempPassword() throws Exception {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
	}
	
	@Override
	public void mailSend(MailDto mailDto) throws Exception {
		System.out.println("이메일 전송 완료");
		SimpleMailMessage message = new SimpleMailMessage();
		System.out.println(mailDto.toString());
		message.setTo(mailDto.getAddress());
		message.setFrom(FROM_ADDRESS);
		message.setSubject(mailDto.getTitle());
		message.setText(mailDto.getMessage());

		mailSender.send(message);
	}

	@Override
	public MemberDto selectByEmail(String email) throws SQLException {
		MemberDto d = memberMapper.selectByEmail(email);
		return d;
	}
	
}
