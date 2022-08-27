package com.ssafy.happyhouse.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.DealAmountDto;
import com.ssafy.happyhouse.model.HouseDealDto;
import com.ssafy.happyhouse.model.HouseInfoDto;
import com.ssafy.happyhouse.model.SidoGugunCodeDto;

public interface HouseMapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<SidoGugunCodeDto> getDongInGugun(String gugun) throws Exception;
	List<HouseInfoDto> getAptInDong(String dong, String lat, String lon, String sort, String base) throws Exception;
	List<HouseInfoDto> getAptByName(Map map) throws Exception;
	List<HouseInfoDto> getAptByNamSido(Map map) throws SQLException;
	List<HouseInfoDto> getAptByNameGugun(Map map) throws SQLException;
	List<HouseInfoDto> getAptByNameDong(Map map) throws SQLException;
	List<HouseDealDto> getAptDeal(Long aptCode) throws SQLException;
	HouseInfoDto getHouseByAptCode(Long aptCode) throws SQLException;	
	SidoGugunCodeDto getLocationName(String dong) throws SQLException;
	List<DealAmountDto> getAvgByArea(Long aptCode) throws SQLException;
	List<DealAmountDto> getAvgByYear(Long aptCode) throws SQLException;
	List<DealAmountDto> getAvgByDong(String dong) throws SQLException;
	

	int selectIsSearchApt(Long aptCode) throws SQLException;
	int updateIsSearchApt(Long aptCode) throws SQLException;
	int insertIsSearchApt(Long aptCode) throws SQLException;
	List<HouseInfoDto> getTopApt() throws SQLException;
	
	List<SidoGugunCodeDto> getRecommend(String idx) throws SQLException;
}
