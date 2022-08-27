package com.ssafy.happyhouse.model;

public class DealAmountDto {
	Long aptCode;
	Long avgAmount;
	int dealYear;
	String area;
	String dongCode;
	public Long getAptCode() {
		return aptCode;
	}
	public void setAptCode(Long aptCode) {
		this.aptCode = aptCode;
	}
	public Long getAvgAmount() {
		return avgAmount;
	}
	public void setAvgAmount(Long avgAmount) {
		this.avgAmount = avgAmount;
	}
	public int getDealYear() {
		return dealYear;
	}
	public void setDealYear(int dealYear) {
		this.dealYear = dealYear;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	
}
