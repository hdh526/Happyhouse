package com.ssafy.happyhouse.model;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HouseInfoDto {
	private long aptCode;
	private int buildYear;
	private String dong;
	private String sigunguCode;
	private String dongCode;
	private String apartmentName;
	private String jibun;
	private String lng;
	private String lat;
	
	
}
