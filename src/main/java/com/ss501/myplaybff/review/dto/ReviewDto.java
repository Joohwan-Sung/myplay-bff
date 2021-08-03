package com.ss501.myplaybff.review.dto;

import lombok.Data;

@Data
public class ReviewDto {

	private Long id;
	private String writerId;
	private String writerName;
	private Long playgroundId;
	private String contents;
	private String createDate;
	private Integer mark;
	private String reviewStatus;

}
