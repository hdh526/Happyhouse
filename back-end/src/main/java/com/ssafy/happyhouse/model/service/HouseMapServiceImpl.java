package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.DealAmountDto;
import com.ssafy.happyhouse.model.HouseDealDto;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.mapper.HouseMapMapper;

@Service
public class HouseMapServiceImpl implements HouseMapService {
	
	@Autowired
	private HouseMapMapper houseMapMapper;
	
	
	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return houseMapMapper.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return houseMapMapper.getGugunInSido(sido);
	}

	@Override
	public List<SidoGugunCodeDto> getDongInGugun(String gugun) throws Exception {
		//System.out.println(houseMapMapper.getDongInGugun(gugun).get(0));
		return houseMapMapper.getDongInGugun(gugun);
	}

	@Override
	public List<HouseInfoDto> getAptInDong(String dong, String lat, String lon, String sort, String base) throws Exception {
		Double latd = Double.parseDouble(lat) * Math.pow(10, 7);
		Double lond = Double.parseDouble(lon) * Math.pow(10, 7);
		
		List<HouseInfoDto> hd = houseMapMapper.getAptInDong(dong);
		PriorityQueue<HouseInfoDto> pq;
		System.out.println(sort);
		System.out.println(base);
		
		if(base.equals("dist") && sort.equals("asc")) {
			pq = new PriorityQueue<HouseInfoDto>(
				(o1, o2) -> (int)(distance(o1.getLat(), o1.getLng(), latd, lond, "m") - distance(o2.getLat(), o2.getLng(), latd, lond, "m"))
				);
		}else if (base.equals("dist") && sort.equals("desc")) {
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> (int)(distance(o2.getLat(), o2.getLng(), latd, lond, "m") - distance(o1.getLat(), o1.getLng(), latd, lond, "m"))
					);
		}else if(base.equals("build") && sort.equals("asc")) {
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> o1.getBuildYear() - o2.getBuildYear()
				);
		}else if(base.equals("build") && sort.equals("desc")){
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> o2.getBuildYear() - o1.getBuildYear() 
				);
		}else if(base.equals("name") && sort.equals("asc")) {
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> o1.getApartmentName().compareTo(o2.getApartmentName())
				);
		}else{
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2)-> o2.getApartmentName().compareTo(o1.getApartmentName())
				);
		}
		
		for(HouseInfoDto hh : hd) {
			pq.add(hh);
			System.out.print(hh.getApartmentName() + " : ");
			System.out.println(distance(hh.getLat(), hh.getLng(), latd, lond, "m"));
		}
		
		List<HouseInfoDto> newh = new ArrayList<HouseInfoDto>();
		while(!pq.isEmpty()) {
			HouseInfoDto tmp = pq.poll();
			newh.add(tmp);
		}
		
		return newh;
	}

	@Override
	public List<HouseInfoDto> getAptByName(Map map) throws Exception {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("name", (String)map.get("name"));
		
		Double latd = Double.parseDouble((String)map.get("lat"));
		Double lond = Double.parseDouble((String)map.get("lon"));
		String sort = (String)map.get("sort");
		String base = (String)map.get("base");
		
		System.out.println(sort);
		System.out.println(base);
		
		List<HouseInfoDto> hd = houseMapMapper.getAptByName(map1);
		PriorityQueue<HouseInfoDto> pq;
		if(base.equals("dist") && sort.equals("asc")) {
			pq = new PriorityQueue<HouseInfoDto>(
				(o1, o2) -> (int)(distance(o1.getLat(), o1.getLng(), latd, lond, "m") - distance(o2.getLat(), o2.getLng(), latd, lond, "m"))
				);
		}else if (base.equals("dist") && sort.equals("desc")) {
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> (int)(distance(o2.getLat(), o2.getLng(), latd, lond, "m") - distance(o1.getLat(), o1.getLng(), latd, lond, "m"))
					);
		}else if(base.equals("build") && sort.equals("asc")) {
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> o1.getBuildYear() - o2.getBuildYear()
				);
		}else if(base.equals("build") && sort.equals("desc")){
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> o2.getBuildYear() - o1.getBuildYear() 
				);
		}else if(base.equals("name") && sort.equals("asc")) {
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> o1.getApartmentName().compareTo(o2.getApartmentName())
				);
		}else{
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2)-> o2.getApartmentName().compareTo(o1.getApartmentName())
				);
		}
			
		for(HouseInfoDto hh : hd) {
			pq.add(hh);
			System.out.print(hh.getApartmentName() + " : ");
			System.out.println(distance(hh.getLat(), hh.getLng(), latd, lond, "m"));
		}
		
		List<HouseInfoDto> newh = new ArrayList<HouseInfoDto>();
		while(!pq.isEmpty()) {
			HouseInfoDto tmp = pq.poll();
			newh.add(tmp);
			
		}
		
		return newh;
	}

	@Override
	public List<HouseInfoDto> getAptByNamSido(Map map) throws SQLException {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("name", (String)map.get("name"));
		map1.put("sido", (String)map.get("sido"));
		
		Double latd = Double.parseDouble((String)map.get("lat"));
		Double lond = Double.parseDouble((String)map.get("lon"));
		String sort = (String)map.get("sort");
		String base = (String)map.get("base");
		
		System.out.println(sort);
		System.out.println(base);
		
		List<HouseInfoDto> hd = houseMapMapper.getAptByNamSido(map1);
		PriorityQueue<HouseInfoDto> pq;
		if(base.equals("dist") && sort.equals("asc")) {
			pq = new PriorityQueue<HouseInfoDto>(
				(o1, o2) -> (int)(distance(o1.getLat(), o1.getLng(), latd, lond, "m") - distance(o2.getLat(), o2.getLng(), latd, lond, "m"))
				);
		}else if (base.equals("dist") && sort.equals("desc")) {
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> (int)(distance(o2.getLat(), o2.getLng(), latd, lond, "m") - distance(o1.getLat(), o1.getLng(), latd, lond, "m"))
					);
		}else if(base.equals("build") && sort.equals("asc")) {
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> o1.getBuildYear() - o2.getBuildYear()
				);
		}else if(base.equals("build") && sort.equals("desc")){
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> o2.getBuildYear() - o1.getBuildYear() 
				);
		}else if(base.equals("name") && sort.equals("asc")) {
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> o1.getApartmentName().compareTo(o2.getApartmentName())
				);
		}else{
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2)-> o2.getApartmentName().compareTo(o1.getApartmentName())
				);
		}
			
		for(HouseInfoDto hh : hd) {
			pq.add(hh);
			System.out.print(hh.getApartmentName() + " : ");
			System.out.println(distance(hh.getLat(), hh.getLng(), latd, lond, "m"));
		}
		
		List<HouseInfoDto> newh = new ArrayList<HouseInfoDto>();
		while(!pq.isEmpty()) {
			HouseInfoDto tmp = pq.poll();
			newh.add(tmp);
			
		}
		
		return newh;
	}

	@Override
	public List<HouseInfoDto> getAptByNameGugun(Map map) throws SQLException {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("name", (String)map.get("name"));
		map1.put("sido", (String)map.get("sido"));
		map1.put("gugun", (String)map.get("gugun"));

		Double latd = Double.parseDouble((String)map.get("lat"));
		Double lond = Double.parseDouble((String)map.get("lon"));
		String sort = (String)map.get("sort");
		String base = (String)map.get("base");
		
		System.out.println(sort);
		System.out.println(base);
		
		List<HouseInfoDto> hd = houseMapMapper.getAptByNameGugun(map1);
		PriorityQueue<HouseInfoDto> pq;
		if(base.equals("dist") && sort.equals("asc")) {
			pq = new PriorityQueue<HouseInfoDto>(
				(o1, o2) -> (int)(distance(o1.getLat(), o1.getLng(), latd, lond, "m") - distance(o2.getLat(), o2.getLng(), latd, lond, "m"))
				);
		}else if (base.equals("dist") && sort.equals("desc")) {
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> (int)(distance(o2.getLat(), o2.getLng(), latd, lond, "m") - distance(o1.getLat(), o1.getLng(), latd, lond, "m"))
					);
		}else if(base.equals("build") && sort.equals("asc")) {
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> o1.getBuildYear() - o2.getBuildYear()
				);
		}else if(base.equals("build") && sort.equals("desc")){
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> o2.getBuildYear() - o1.getBuildYear() 
				);
		}else if(base.equals("name") && sort.equals("asc")) {
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> o1.getApartmentName().compareTo(o2.getApartmentName())
				);
		}else{
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2)-> o2.getApartmentName().compareTo(o1.getApartmentName())
				);
		}
			
		for(HouseInfoDto hh : hd) {
			pq.add(hh);
			System.out.print(hh.getApartmentName() + " : ");
			System.out.println(distance(hh.getLat(), hh.getLng(), latd, lond, "m"));
		}
		
		List<HouseInfoDto> newh = new ArrayList<HouseInfoDto>();
		while(!pq.isEmpty()) {
			HouseInfoDto tmp = pq.poll();
			newh.add(tmp);
			
		}
		
		return newh;
	}

	@Override
	public List<HouseInfoDto> getAptByNameDong(Map map) throws SQLException {
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("name", (String)map.get("name"));		
		map1.put("sido", (String)map.get("sido"));
		map1.put("gugun", (String)map.get("gugun"));
		map1.put("dong", (String)map.get("dong"));

		
		Double latd = Double.parseDouble((String)map.get("lat"));
		Double lond = Double.parseDouble((String)map.get("lon"));
		String sort = (String)map.get("sort");
		String base = (String)map.get("base");
		
		System.out.println(sort);
		System.out.println(base);
		
		List<HouseInfoDto> hd = houseMapMapper.getAptByNameDong(map1);
		PriorityQueue<HouseInfoDto> pq;
		if(base.equals("dist") && sort.equals("asc")) {
			pq = new PriorityQueue<HouseInfoDto>(
				(o1, o2) -> (int)(distance(o1.getLat(), o1.getLng(), latd, lond, "m") - distance(o2.getLat(), o2.getLng(), latd, lond, "m"))
				);
		}else if (base.equals("dist") && sort.equals("desc")) {
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> (int)(distance(o2.getLat(), o2.getLng(), latd, lond, "m") - distance(o1.getLat(), o1.getLng(), latd, lond, "m"))
					);
		}else if(base.equals("build") && sort.equals("asc")) {
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> o1.getBuildYear() - o2.getBuildYear()
				);
		}else if(base.equals("build") && sort.equals("desc")){
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> o2.getBuildYear() - o1.getBuildYear() 
				);
		}else if(base.equals("name") && sort.equals("asc")) {
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2) -> o1.getApartmentName().compareTo(o2.getApartmentName())
				);
		}else{
			pq = new PriorityQueue<HouseInfoDto>(
					(o1, o2)-> o2.getApartmentName().compareTo(o1.getApartmentName())
				);
		}
			
		for(HouseInfoDto hh : hd) {
			pq.add(hh);
			System.out.print(hh.getApartmentName() + " : ");
			System.out.println(distance(hh.getLat(), hh.getLng(), latd, lond, "m"));
		}
		
		List<HouseInfoDto> newh = new ArrayList<HouseInfoDto>();
		while(!pq.isEmpty()) {
			HouseInfoDto tmp = pq.poll();
			newh.add(tmp);
			
		}
		
		return newh;
	}
	
	@Override
	public List<HouseDealDto> getAptDeal(Long aptCode) throws SQLException {
		return houseMapMapper.getAptDeal(aptCode);
	}
	
	@Override
	public HouseInfoDto getHouseByAptCode(Long aptCode) throws SQLException {
		return houseMapMapper.getHouseByAptCode(aptCode);
	}

	@Override
	public SidoGugunCodeDto getLocationName(String dong) throws SQLException {
		System.out.println(houseMapMapper.getLocationName(dong));
		return houseMapMapper.getLocationName(dong);
	}
	
	@Override
	public List<DealAmountDto> getAvgByArea(Long aptCode) throws SQLException {
		return houseMapMapper.getAvgByArea(aptCode);
	}

	@Override
	public List<DealAmountDto> getAvgByDong(String dong) throws SQLException {
		return houseMapMapper.getAvgByDong(dong);
	}
	
	@Override
	public List<DealAmountDto> getAvgByYear(Long aptCode) throws SQLException {
		return houseMapMapper.getAvgByYear(aptCode);
	}
	
	
	
	private static double distance(String lat1, String lon1, double lat2, double lon2, String unit) {
		Double lat = Double.parseDouble(lat1);
		Double lng = Double.parseDouble(lon1);
		
		double theta = lng - lon2;
		double dist = Math.sin(deg2rad(lat)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if(unit == "k") {
			dist = dist * 1.609344;	
		}else {
			dist = dist * 1609.344;
		}
		
		return dist;
	}
	
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	
	private static int calcPrice(String price) {
		String tmp = "";
		StringTokenizer st = new StringTokenizer(price.trim(), ",");
		while(st.hasMoreTokens()) {
			tmp += st.nextToken();
		}
		return Integer.parseInt(tmp);
	}

	@Override
	public int selectIsSearchApt(Long aptCode) throws SQLException {
		return houseMapMapper.selectIsSearchApt(aptCode);
	}

	@Override
	@Transactional
	public int updateIsSearchApt(Long aptCode) throws SQLException {
		return houseMapMapper.updateIsSearchApt(aptCode);
	}

	@Override
	@Transactional
	public int insertIsSearchApt(Long aptCode) throws SQLException {
		return houseMapMapper.insertIsSearchApt(aptCode);
	}

	@Override
	public List<HouseInfoDto> getTopApt() throws SQLException {
		return houseMapMapper.getTopApt();
	}
	
	@Override
	public List<SidoGugunCodeDto> getRecommend(String idx) throws SQLException {
		return houseMapMapper.getRecommend(idx);
	}
}
