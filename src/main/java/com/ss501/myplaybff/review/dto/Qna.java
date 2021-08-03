package com.ss501.myplaybff.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Qna {

	private Long id;
	// private Long parentQnaId;
//	private String questionerId;
//	private String questionerName;
	private String answererId;
	private String answererName;
	private Questioner questioner;
	private Answerer answerer;
	private Playground playground;
//	private Long playgroundId;
//	private String playgroundName;
	private String question;
	private String answer;
	private String createDateQ;
	private String createDateA;
//	private String contents;
//	private String createDate;
//	private CreateUser createUser;
}
