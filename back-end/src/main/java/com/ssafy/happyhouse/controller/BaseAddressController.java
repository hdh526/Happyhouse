package com.ssafy.happyhouse.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.happyhouse.model.DealAmountDto;
import com.ssafy.happyhouse.model.HouseDealDto;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.service.HouseMapService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/address")
@CrossOrigin("*")
@RestController
public class BaseAddressController extends HttpServlet {
	
	@Autowired
	private HouseMapService haHouseMapService;
	
	
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		log.debug("sido : {}", haHouseMapService.getSido());
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getSido(), HttpStatus.OK);
	}
	
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(@RequestParam("sido") String sido) throws Exception {
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@GetMapping("/dong")
	public ResponseEntity<List<SidoGugunCodeDto>> dong(@RequestParam("gugun") String gugun) throws Exception {
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getDongInGugun(gugun), HttpStatus.OK);
	}
	
	@GetMapping("dong2")
	public ResponseEntity<SidoGugunCodeDto> dong2(@RequestParam("dong") String dong) throws Exception{
		System.out.println(haHouseMapService.getLocationName(dong));
		return new ResponseEntity<SidoGugunCodeDto>(haHouseMapService.getLocationName(dong), HttpStatus.OK);
	}
	
	@GetMapping("/apt")
	public ResponseEntity<List<HouseInfoDto>> apt(@RequestParam("dong") String dong, @RequestParam("lat") String lat, @RequestParam("lon") String lon, @RequestParam("sort") String sort, @RequestParam("base") String base) throws Exception {
		System.out.println("Request Get" + dong);
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptInDong(dong, lat, lon, sort, base), HttpStatus.OK);
	}
	
	@GetMapping("/deal")
	public ResponseEntity<List<HouseDealDto>> deal(@RequestParam("aptCode") Long aptCode) throws Exception
	{
		int isAlreadySearch = haHouseMapService.selectIsSearchApt(aptCode);
		if(isAlreadySearch > 0) haHouseMapService.updateIsSearchApt(aptCode);
		else haHouseMapService.insertIsSearchApt(aptCode);
		
		return new ResponseEntity<List<HouseDealDto>>(haHouseMapService.getAptDeal(aptCode), HttpStatus.OK);
	}
	
	@GetMapping("/aptN")
	public ResponseEntity<List<HouseInfoDto>> aptN(@RequestParam("apt") String apt, @RequestParam("sido") String sido, @RequestParam("gugun") String gugun, @RequestParam("dong") String dong, @RequestParam("lat") String lat, @RequestParam("lon") String lon, @RequestParam("sort") String sort, @RequestParam("base") String base) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", apt);
		map.put("lat", lat);
		map.put("lon", lon);
		map.put("sort", sort);
		map.put("base", base);
		if(sido == null || sido == "" || sido.equals("")) {
			log.debug("아파트명 검색");
			log.debug("탐색 정보 : " + map);
			return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptByName(map), HttpStatus.OK);
			
		}else if(gugun == null || gugun == "" || gugun.equals("")) {
			map.put("sido", sido);
			log.debug("시도 아파트명 검색");
			log.debug("탐색 정보 : " + map);
			return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptByNamSido(map), HttpStatus.OK);
			
		}else if(dong == null || dong == "" || dong.equals("")) {
			map.put("gugun", gugun);
			log.debug("시군구 아파트명 검색");
			log.debug("탐색 정보 : " + map);
			return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptByNameGugun(map), HttpStatus.OK);
			
		}else {
			map.put("dong", dong);
			log.debug("시군구동 아파트명 검색");
			log.debug("탐색 정보 : " + map);
			return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptByNameDong(map), HttpStatus.OK);
			
		}
	
	}
	
	@GetMapping("/aptC")
	public ResponseEntity<HouseInfoDto> aptC(@RequestParam("aptCode") Long aptCode){
		HouseInfoDto h = null;
		try{
			h = haHouseMapService.getHouseByAptCode(aptCode);
			System.out.println(h.toString());
			return new ResponseEntity<HouseInfoDto>(h, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<HouseInfoDto>(h, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/avgArea")
	public ResponseEntity<?> avgArea(@RequestParam("aptCode") Long aptCode){
		List<DealAmountDto> d = null;
		try {
			d = haHouseMapService.getAvgByArea(aptCode);
			System.out.println(d.toString());
			return new ResponseEntity<List<DealAmountDto>>(d, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<DealAmountDto>>(d, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/avgYear")
	public ResponseEntity<?> avgYear(@RequestParam("aptCode") Long aptCode){
		List<DealAmountDto> d = null;
		try {
			d = haHouseMapService.getAvgByYear(aptCode);
			System.out.println(d.toString());
			return new ResponseEntity<List<DealAmountDto>>(d, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<DealAmountDto>>(d, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/avgDong")
	public ResponseEntity<?> avgDong(@RequestParam("dong") String dong){
		List<DealAmountDto> d = null;
		try {
			d = haHouseMapService.getAvgByDong(dong);
			System.out.println(d.toString());
			return new ResponseEntity<List<DealAmountDto>>(d, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<DealAmountDto>>(d, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/top")
	public ResponseEntity<?> getTop(){
		List<HouseInfoDto> d = null;
		try {
			d = haHouseMapService.getTopApt();
			return new ResponseEntity<List<HouseInfoDto>>(d, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<HouseInfoDto>>(d, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/rec/{idx}")
	public ResponseEntity<?> getRecArea(@PathVariable String idx){
		List<SidoGugunCodeDto> s = null;
		try{
			s = haHouseMapService.getRecommend(idx);
			return new ResponseEntity<List<SidoGugunCodeDto>>(s, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<List<SidoGugunCodeDto>>(s, HttpStatus.NOT_FOUND);
		}
	}
 //	BaseAddressService bsvc = BaseAddressServiceImpl.getInstance();
       
	
	/*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		process(request, response);
	}
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "search": search(request, response); break;
		}
	}
	protected void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<String> sidos = bsvc.findSiDo();
			List<BaseAddressDto> bases = bsvc.findAll();
			request.setAttribute("sidos", sidos);
			request.setAttribute("bases", bases);
			request.getRequestDispatcher("/search.jsp").forward(request, response);
		} catch (SQLException e) {
			request.setAttribute("msg", "주소정보 로딩에 문제가 생겼습니다.");
			response.sendRedirect(request.getContextPath() + "/error.jsp");
		}
	}
	*/
}
