package com.example.umc_9th_final_5th.domain.review.service;

import com.example.umc_9th_final_5th.domain.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 테스트용 서비스 - 실제 사용 예시
 */
@Service
@RequiredArgsConstructor
public class ReviewTestService {

    private final ReviewFilterService reviewFilterService;

    /**
     * 사용 예시 1: 모든 리뷰 조회
     */
    public void getAllMyReviews(Long memberId) {
        List<Review> reviews = reviewFilterService.getMyReviews(memberId, null, null);
        System.out.println("=== 모든 리뷰 조회 ===");
        reviewFilterService.printReviews(reviews);
    }

    /**
     * 사용 예시 2: 특정 가게 리뷰만 조회
     */
    public void getReviewsByStore(Long memberId, String storeName) {
        List<Review> reviews = reviewFilterService.getMyReviews(memberId, storeName, null);
        System.out.println("=== " + storeName + " 가게 리뷰 조회 ===");
        reviewFilterService.printReviews(reviews);
    }

    /**
     * 사용 예시 3: 5점 리뷰만 조회
     */
    public void getFiveStarReviews(Long memberId) {
        List<Review> reviews = reviewFilterService.getMyReviews(memberId, null, 5);
        System.out.println("=== 5점 리뷰만 조회 ===");
        reviewFilterService.printReviews(reviews);
    }

    /**
     * 사용 예시 4: 4점대 리뷰만 조회
     */
    public void getFourStarReviews(Long memberId) {
        List<Review> reviews = reviewFilterService.getMyReviews(memberId, null, 4);
        System.out.println("=== 4점대 리뷰만 조회 ===");
        reviewFilterService.printReviews(reviews);
    }

    /**
     * 사용 예시 5: 특정 가게의 5점 리뷰만 조회
     */
    public void getStoreReviewsWithFiveStar(Long memberId, String storeName) {
        List<Review> reviews = reviewFilterService.getMyReviews(memberId, storeName, 5);
        System.out.println("=== " + storeName + " 가게의 5점 리뷰만 조회 ===");
        reviewFilterService.printReviews(reviews);
    }

    /**
     * 사용 예시 6: 반이학생마라탕마라반 가게의 4점대 리뷰만 조회
     */
    public void getMaratangFourStarReviews(Long memberId) {
        List<Review> reviews = reviewFilterService.getMyReviews(memberId, "반이학생마라탕마라반", 4);
        System.out.println("=== 반이학생마라탕마라반 가게의 4점대 리뷰만 조회 ===");
        reviewFilterService.printReviews(reviews);
    }
}
