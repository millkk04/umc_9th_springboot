package com.example.umc_9th_final_5th.domain.review.service.command;

import com.example.umc_9th_final_5th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc_9th_final_5th.domain.review.entity.Review;

public interface ReviewCommandService {

    /**
     * 리뷰 생성
     * @param memberId 작성자 ID
     * @param request 리뷰 생성 요청 DTO
     * @return 생성된 리뷰 엔티티
     */
    Review createReview(Long memberId, ReviewReqDTO.CreateReviewDTO request);
}
