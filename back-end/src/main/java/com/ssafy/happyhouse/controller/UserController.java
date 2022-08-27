package com.ssafy.happyhouse.controller;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.MailDto;
import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.RecommendDto;
import com.ssafy.happyhouse.model.service.MemberService;
import com.ssafy.happyhouse.controller.UserController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.ssafy.happyhouse.model.service.JwtServiceImpl;

@Api("MemberController V1")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")
public class UserController {
	
	// 복호화 함수 정의 
//	private String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
//        System.out.println("will decrypt : " + securedValue);
//        Cipher cipher = Cipher.getInstance("RSA");
//        byte[] encryptedBytes = hexToByteArray(securedValue);
//        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
//        String decryptedValue = new String(decryptedBytes, "utf-8"); // 문자 인코딩 
//        return decryptedValue;
//    }
//
//    // 16진수 문자열을 바이트 배열로 변환   
//    public static byte[] hexToByteArray(String hex) {
//        if (hex == null || hex.length() % 2 != 0) {
//            return new byte[]{};
//        }
//        byte[] bytes = new byte[hex.length() / 2];
//        for (int i = 0; i < hex.length(); i += 2) {
//            byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
//            bytes[(int) Math.floor(i / 2)] = value;
//        }
//        return bytes;
//    }
	
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
    
    @Autowired
	private JwtServiceImpl jwtService;
    
	@Autowired
	private MemberService msvc;
	
	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody MemberDto memberdto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = msvc.login(memberdto.getId(), memberdto.getPassword());
			if (loginUser != null) {
				String token = jwtService.create("id", loginUser.getId(), "access-token");// key, data, subject
				logger.debug("로그인 토큰정보 : {}", token );
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/naverLogin")
	public ResponseEntity<String> naverLogin(MemberDto memberDto, Model m) throws Exception {
		System.out.println(memberDto);
		if(msvc.insert(memberDto)==1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		};
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	

	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{id}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("id") @ApiParam(value = "인증할 회원의 아이디.", required = true) String id,
			HttpServletRequest request) {
//		logger.debug("id : {} ", id);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = msvc.userInfo(id);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
       
//	@PostMapping("/login")
//	public String login(HttpSession session, Model m, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		MemberDto memberDto = new MemberDto();
//		memberDto.setId(request.getParameter("securedUsername"));
//		memberDto.setPassword(request.getParameter("securedPassword"));
//
//		// 세션에 저장된 개인키를 불러온다. 
//	    PrivateKey privateKey = (PrivateKey)session.getAttribute("__rsaPrivateKey__");
//        session.removeAttribute("__rsaPrivateKey__"); // 키 재사용 방지
//        if (privateKey == null) {
//            throw new RuntimeException("암호화 비밀키 정보를 찾을 수 없습니다.");
//        }
//        try {
//            // 개인키로 데이터를 복호화한다.
//            String username = decryptRsa(privateKey, memberDto.getId());
//            String password = decryptRsa(privateKey, memberDto.getPassword());
//            System.out.println(username);
//            request.setAttribute("username", username);
//            request.setAttribute("password", password);
//            
//            MemberDto tmp = msvc.select(username);
//            if(tmp!=null && password.equals(tmp.getPassword())) {
//            	session.setAttribute("userinfo", tmp);
//            	return "redirect:/";
//            }else {
//    			m.addAttribute("msg", "로그인 실패");
//    			return "redirect:/";
//            }
//        } catch (Exception ex) {
//            throw new ServletException(ex.getMessage(), ex);
//        }
//        
////		MemberDto tmp = msvc.select(memberDto.getId());
////		if(tmp!=null && memberDto.getPassword().equals(tmp.getPassword())) {
////			session.setAttribute("userinfo", tmp);
////			return "redirect:/";
////		}
////		else{
////			m.addAttribute("msg", "로그인 실패");
////			return "redirect:/";
////		}
//		
//	}
//	 
//	@GetMapping("/")
//	public String Login(HttpSession session, HttpServletRequest request,HttpServletResponse response, Model model) throws Exception, NoSuchAlgorithmException {
//		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
//		generator.initialize(2048); // 키 사이즈 - 1024, 2048
//		      
//		KeyPair keyPair = generator.genKeyPair();
//		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//		  
//		PublicKey publicKey = keyPair.getPublic();
//		PrivateKey privateKey = keyPair.getPrivate();
//		  
//		// 개인 키 생성 후 세션에 저장
//		session.setAttribute("__rsaPrivateKey__", privateKey);
//		  
//		// 공개키를 문자열로 변환
//		RSAPublicKeySpec publicSpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
//		 
//		String publicKeyModulus = publicSpec.getModulus().toString(16);
//		String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
//		
//		// 로그인 폼 Input hidden 값 설정 
//		request.setAttribute("publicKeyModulus", publicKeyModulus);  
//		request.setAttribute("publicKeyExponent", publicKeyExponent); 
//		
//		return "redirect:/";
//	}

	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@PostMapping("/regist")
	public ResponseEntity<String> regist(MemberDto memberDto, Model m) throws Exception {
		System.out.println(memberDto);
		if(msvc.insert(memberDto)==1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		};
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/view")
	public String view(HttpSession session, Model m) throws Exception {
//		MemberDto tmp = (MemberDto)session.getAttribute("userinfo");
//		System.out.println(tmp.toString());
		return "view";
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") String id, Model m, HttpSession session) throws Exception {
		if (msvc.delete(id)==1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		session.invalidate();
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//		msvc.delete(id);
//		return "redirect:/";
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> update(MemberDto memberDto, Model m, HttpSession session) throws Exception {
//		System.out.println(memberDto.toString());
		if(msvc.update(memberDto)==1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		};
		session.setAttribute("userinfo", memberDto);
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//		return "redirect:/";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/idcheck")
    public ResponseEntity<?> idCheck(@RequestParam("ckid") String checkId) throws Exception{
        int idCount = msvc.idCheck(checkId);
        return new ResponseEntity<Integer>(idCount, HttpStatus.OK);
    }
	
	@GetMapping("/check/{id}")
    public ResponseEntity<?> Check(@PathVariable("id") String check) throws Exception{
        int idck = msvc.Check(check);
        Map<String, Object> resultMap = new HashMap<>();
        if(idck==0) {
        	resultMap.put("message", SUCCESS);
        	return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
        }
        resultMap.put("message", FAIL);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
    }
	
	@PutMapping("/done/{id}")
	public ResponseEntity<?> done(@PathVariable("id") String id, @RequestParam("type") int type){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("type", type);
		if(msvc.updateType(map)==1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<String>(FAIL, HttpStatus.ACCEPTED);
		}
	}
	
	@GetMapping("/checkType/{id}")
	public ResponseEntity<?> checkType(@PathVariable("idx") String id){
		try {
			MemberDto m = msvc.select(id);
			System.out.println("get Type IDx" + m.getType());
			RecommendDto rd = msvc.getType(m.getType());
			return new ResponseEntity<RecommendDto>(rd, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
		
	}
	
	@GetMapping("/checkType")
	public ResponseEntity<?> checkType(@RequestParam("idx") int idx){
		try {
			RecommendDto rd = msvc.getType(idx);
			return new ResponseEntity<RecommendDto>(rd, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping("/sendEmail")
	public ResponseEntity<?> sendEmail(@RequestParam("email") String memberEmail) {
		MailDto dto;
		try {
			dto = msvc.createMailAndChangePassword(memberEmail);
			try {
				msvc.mailSend(dto);
				return new ResponseEntity<String>(SUCCESS, HttpStatus.ACCEPTED);
			}catch(SQLException e) {
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
}
