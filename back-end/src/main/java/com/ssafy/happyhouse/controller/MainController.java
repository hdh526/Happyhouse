package com.ssafy.happyhouse.controller;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpSession session, HttpServletRequest request,HttpServletResponse response, Model model) throws Exception, NoSuchAlgorithmException{
		if(session.getAttribute("username")==null) {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(2048); // 키 사이즈 - 1024, 2048
			      
			KeyPair keyPair = generator.genKeyPair();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			  
			PublicKey publicKey = keyPair.getPublic();
			PrivateKey privateKey = keyPair.getPrivate();
			  
			// 개인 키 생성 후 세션에 저장
			session.setAttribute("__rsaPrivateKey__", privateKey);
			  
			// 공개키를 문자열로 변환
			RSAPublicKeySpec publicSpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
			 
			String publicKeyModulus = publicSpec.getModulus().toString(16);
			String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
			
			// 로그인 폼 Input hidden 값 설정 
			request.setAttribute("publicKeyModulus", publicKeyModulus);  
			request.setAttribute("publicKeyExponent", publicKeyExponent);
			
		}
		return "index";
	}
	
	@RequestMapping(value="/search", method= RequestMethod.GET)
	public String search() {
		return "search";
	}
	
	
}
