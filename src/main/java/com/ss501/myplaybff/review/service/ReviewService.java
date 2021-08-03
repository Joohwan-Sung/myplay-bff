package com.ss501.myplaybff.review.service;

import java.util.List;

import com.ss501.myplaybff.review.dto.Qna;
import com.ss501.myplaybff.review.dto.QnaDto;
import com.ss501.myplaybff.review.dto.Review;
import com.ss501.myplaybff.review.dto.ReviewDto;


public interface ReviewService {
	List<ReviewDto> findAllReviews();
	List<Review> findReviewsByPlaygroundId(Long playgroundId);
	ReviewDto findReviewByReviewId(Long reviewId);

	ReviewDto saveReview(ReviewDto review);
	QnaDto saveQuestion(QnaDto qnaDto);
	QnaDto saveAnswer(String id, QnaDto qnaDto);
	List<Qna> findQnaByQuestionerId(String questionerId);
	List<Qna> findQnaByPlaygroundIds(List<Long> playgroundIdList);
}
