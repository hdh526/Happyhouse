package com.ssafy.happyhouse.model;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	int cm_idx;
	String writer;
	int bd_idx;
	String content;
	String time;
	String title;
}
