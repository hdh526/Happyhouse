package com.ssafy.happyhouse.model;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
	private String address;
	private String title;
	private String message;
}
