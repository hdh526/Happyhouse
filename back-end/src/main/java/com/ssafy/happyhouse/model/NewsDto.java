package com.ssafy.happyhouse.model;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {
	String title;
	String link;
	String company;
	String time;
}
