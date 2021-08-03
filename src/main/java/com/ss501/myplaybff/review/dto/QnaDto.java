package com.ss501.myplaybff.review.dto;

import lombok.Data;

@Data
public class QnaDto {

	private Long id;
	//private Long parentQnaId;
	private String questionerId;
	private String questionerName;
	private String answererId;
	private String answererName;
	private Long playgroundId;
	private String playgroundName;
	private String playgroundInfo;
	private String question;
	private String answer;
	private String createDateQ;
	private String createDateA;
//	private String contents;
//	private String createDate;
//	private CreateUser createUser;

}
