package com.example.umc_9th_final_5th.domain.review.converter;

import com.example.umc_9th_final_5th.domain.member.entity.Member;
import com.example.umc_9th_final_5th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc_9th_final_5th.domain.review.dto.res.ReviewResDTO;
import com.example.umc_9th_final_5th.domain.review.entity.Review;
import com.example.umc_9th_final_5th.domain.store.entity.Store;

public class ReviewConverter {

    /**
     * CreateReviewDTO -> Review 엔티티 변환
     */
    public static Review toReview(ReviewReqDTO.CreateReviewDTO request, Store store, Member member) {
        return Review.builder()
                .content(request.content())
                .star(request.star())
                .store(store)
                .writer(member)
                .build();
    }

    /**
     * Review 엔티티 -> CreateReviewResultDTO 변환
     */
    public static ReviewResDTO.CreateReviewResultDTO toCreateReviewResultDTO(Review review) {
        return ReviewResDTO.CreateReviewResultDTO.builder()
                .reviewId(review.getId())
                .storeId(review.getStore().getId())
                .storeName(review.getStore().getName())
                .star(review.getStar())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
