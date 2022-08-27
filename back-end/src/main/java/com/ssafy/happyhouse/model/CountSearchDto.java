package com.ssafy.happyhouse.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CountSearchDto {
	int ct_idx;
	String dongcode;
	String guguncode;
	String sidocode;
	Long aptCode;
	int count;
}
