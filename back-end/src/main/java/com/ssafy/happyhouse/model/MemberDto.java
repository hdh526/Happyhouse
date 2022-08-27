package com.ssafy.happyhouse.model;

import lombok.*;
/*
회원 테이블
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MemberDto {
	private String id;
	private String password;
	private String name;
	private String phone;
	private String email;
	private int type;
}
