package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.BookMarkHouseDto;
import com.ssafy.happyhouse.model.service.BoardService;
import com.ssafy.happyhouse.model.service.BookMarkService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/bookmark")
@CrossOrigin("*")
@RestController
public class BookmarkController extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private BookMarkService bs;
	
	@PostMapping("/add")
	public ResponseEntity<?> insertBookmark(@RequestParam String mb_id, @RequestParam Long apt_code) throws Exception{
		logger.debug("insertBookmark - ");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mb_id", mb_id);
			map.put("apt_code", apt_code);
			
			if(bs.insertBookmark(map)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}else {
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> getBookmark(@RequestParam String mb_id){
		logger.debug("*** getBookmark ***");
		try {
			List<BookMarkHouseDto> bookmark = bs.selectBookmark(mb_id);
			if(bookmark!=null) {
				return new ResponseEntity<List<BookMarkHouseDto>>(bookmark, HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			return exceptionHandling(e);
		}
		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteBookmark(@RequestParam String mb_id, @RequestParam Long apt_code){
		logger.debug("deleteBookmark");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mb_id", mb_id);
			map.put("apt_code", apt_code);
			
			if(bs.deleteBookmark(map)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}else {
				return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
