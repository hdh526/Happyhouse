package com.ssafy.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.*;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HouseMapMapper {
	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<SidoGugunCodeDto> getDongInGugun(String gugun) throws SQLException;
	List<HouseInfoDto> getAptInDong(String dong) throws SQLException;
	List<HouseInfoDto> getAptByName(Map map) throws SQLException;
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
