package com.example.umc_9th_final_5th.domain.review.service.query;

import com.example.umc_9th_final_5th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewQueryService {
    /**
     * 특정 회원이 작성한 리뷰 목록을 필터링 + 페이징 처리하여 조회
     * @param memberId 회원 ID
     * @param storeName 가게 이름 필터 (선택, null 가능)
     * @param star 별점 필터 (선택, null 가능)
     * @param pageable 페이징 정보
     * @return 페이징 처리된 리뷰 목록
     */
    Page<Review> getMyReviews(Long memberId, String storeName, Integer star, Pageable pageable);
}
