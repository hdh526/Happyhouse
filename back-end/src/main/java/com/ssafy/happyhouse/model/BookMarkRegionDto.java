package com.ssafy.happyhouse.model;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookMarkRegionDto {
	int br_idx;
	String mb_id;
	String sidocode;
	String sidoname;
	String guguncode;
	String gugunname;
	String dongcode;
	String dongname;
	
}
