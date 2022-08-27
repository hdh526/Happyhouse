package com.ssafy.happyhouse.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.CommentDto;
import com.ssafy.happyhouse.model.NewsDto;
import com.ssafy.happyhouse.model.service.BoardService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/boardapi")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private BoardService boardService;

    @ApiOperation(value = "모든 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/board")
	public ResponseEntity<?> retrieveBoard(){

		logger.debug("retrieveBoard - 호출");
		try {
			List<BoardDto> boards = boardService.selectBoard();
			for(BoardDto b : boards) {
				b.setCount(boardService.countComment(b.getNo()));
			}
			return new ResponseEntity<List<BoardDto>>(boards, HttpStatus.OK);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)    
	@GetMapping("/board/{no}")
	public ResponseEntity<?> detailBoard(@PathVariable int no, @RequestParam boolean isWatch) throws Exception{
    	System.out.println(no);
    	System.out.println(isWatch);
		logger.debug("detailBoard - 호출");
		if(isWatch) {
			try {
				boardService.increaseHit(no);
			}catch(Exception e) {
				exceptionHandling(e);
			}
		}
		
		try {
			BoardDto board = boardService.selectBoardByNo(no);
			return new ResponseEntity<BoardDto>(board, HttpStatus.OK);
		}catch(Exception e) {
			return exceptionHandling(e);
		}
	}
    
    @ApiOperation(value = "검색 키워드를 포함하는 제목을 가지는 게시글의 정보를 반환한다.", response = BoardDto.class)    
	@GetMapping("/board/search")
	public ResponseEntity<?> searchTitle(@RequestParam String title) throws Exception {
		logger.debug("searchTitle - 호출");
		try {
			List<BoardDto> boards = boardService.selectBoardByTitle(title);
			if(boards != null && boards.size()>0) {
				return new ResponseEntity<List<BoardDto>>(boards, HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			return exceptionHandling(e);
		}
		
	}

    @GetMapping("/board/main")
    public ResponseEntity<?> searchForMain() throws Exception{
    	logger.debug("searchForMain - 호출");
    	try {
    		List<BoardDto> boards = boardService.selectBoardForMain();
    		if(boards != null && boards.size() > 0) {
    			return new ResponseEntity<List<BoardDto>>(boards, HttpStatus.OK);
    		}else {
    			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    		}
    	}catch(Exception e) {
    		return exceptionHandling(e);
    	}
    }

    @ApiOperation(value = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/board")
	public ResponseEntity<String> writeBoard(@RequestParam String title, @RequestParam String id, @RequestParam String content) throws Exception{
    	Map<String, String> map = new HashMap<String, String>();
		map.put("title", title);
		map.put("id", id);
		map.put("content", content);
		logger.debug("writeBoard - 호출");
		if (boardService.insertBoard(map)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/board/{no}")
	public ResponseEntity<String> updateBoard(@PathVariable int no, @RequestParam String title, @RequestParam String id, @RequestParam String content) throws Exception{
		logger.debug("updateBoard - 호출");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("title", title);
		map.put("id", id);
		map.put("content", content);
		
		if (boardService.updateBoard(map)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/board/{no}")
	public ResponseEntity<String> deleteBoard(@PathVariable int no) throws Exception{
		logger.debug("deleteBoard - 호출");
		if (boardService.deleteBoard(no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
    
    @GetMapping("/board/news")
    public ResponseEntity<List<NewsDto>> getNewsByCrowling() throws Exception{
    	logger.debug("뉴스 크롤링");
    	String url = "https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=101&sid2=260";
    	System.out.println("==========================");
    	System.out.println("url : " + url);
    	
    	Document doc = Jsoup.connect(url).get();
    	Elements element = doc.select("div.list_body");
    	//String title = element.select("h3").text();
    	//System.out.println(title);
    	
    	List<NewsDto> listNews = new ArrayList<NewsDto>();
    	
    	for(Element e : element.select("li")){
    		Elements tmp = e.select("dt a");
    		String title = tmp.text();
    		String link = tmp.attr("href");
    		tmp = e.select("span.writing");
    		String company = tmp.text();
    		tmp = e.select("span.date");
    		String date = tmp.text();
    		listNews.add(new NewsDto(title, link, company, date));
    	}

    	listNews = listNews.subList(0, 10);
    	for(NewsDto d : listNews) {
    		System.out.println(d.toString());
    	}
    	
  
		return new ResponseEntity<List<NewsDto>>(listNews, HttpStatus.OK);
    }
    
    @PostMapping("/board/commentR/{no}")
    public ResponseEntity<?> insertComment(@PathVariable("no") int no, @RequestParam("id") String id,  @RequestParam("content") String content){
    	CommentDto commentDto = new CommentDto();
    	commentDto.setWriter(id);
    	commentDto.setBd_idx(no);
    	commentDto.setContent(content);
    	
    	logger.debug("writeComment - 호출");
    	try {
    		CommentDto d = boardService.insertComment(commentDto);
    		return new ResponseEntity<CommentDto>(d, HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    	}
    }
    
    @PutMapping("/board/commentU/{no}")
    public ResponseEntity<String> updateComment(@PathVariable int no, @RequestParam("content") String content){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("no", no);
    	map.put("content", content);
    	logger.debug("updateComment 호출");
    	if (boardService.updateComment(map)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);	
    }
    
    @DeleteMapping("/board/commentD/{no}")
    public ResponseEntity<String> deleteComment(@PathVariable int no){
    	logger.debug("deleteBoard - 호출");
		if (boardService.deleteComment(no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	
    }
    
    @ApiOperation(value = "글번호에 해당하는 댓글의 정보를 반환한다.", response = BoardDto.class)    
	@GetMapping("/board/comment/{no}")
	public ResponseEntity<?> selectCommentByNo(@PathVariable int no) throws Exception{
    	System.out.println(no);
		logger.debug("detailComment - 호출");
		try {
			List<CommentDto> comments = boardService.selectCommentByBoard(no);
			System.out.println(comments);
			return new ResponseEntity<List<CommentDto>>(comments, HttpStatus.OK);
		}catch(Exception e) {
			return exceptionHandling(e);
		}
	}
    
    @ApiOperation(value = "글번호에 해당하는 댓글의 개수 반환한다.", response = BoardDto.class)    
	@GetMapping("/board/comments/{no}")
	public ResponseEntity<?> selectCommentNumber(@PathVariable int no) throws Exception{
    	System.out.println(no);
		logger.debug("댓글 수 조회 호출");
		
		try {
			int count = boardService.countComment(no);
			return new ResponseEntity<Integer>(count, HttpStatus.OK);
		}catch(Exception e) {
			return exceptionHandling(e);
		}
	}
    
    @GetMapping("/board/myboard/{id}")
    public ResponseEntity<?> selectMyBoard(@PathVariable String id) throws Exception{
    	System.out.println(id);
    	try {
    		List<BoardDto> mb = boardService.selectBoardById(id);
    		return new ResponseEntity<List<BoardDto>>(mb, HttpStatus.OK);
    	}catch(Exception e) {
    		return exceptionHandling(e);
    	}
    }
    
    @GetMapping("board/mycomment/{id}")
    public ResponseEntity<?> selectMyComment(@PathVariable String id) throws Exception{
    	try {
    		List<CommentDto> mc = boardService.selectCommentById(id);
    		return new ResponseEntity<List<CommentDto>>(mc, HttpStatus.OK);
    	}catch(Exception e) {
    		return exceptionHandling(e);
    	}
    }
    
    private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
    
}