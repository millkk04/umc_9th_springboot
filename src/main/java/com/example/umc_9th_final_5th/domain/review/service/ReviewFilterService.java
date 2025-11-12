package com.example.umc_9th_final_5th.domain.review.service;

import com.example.umc_9th_final_5th.domain.review.entity.Review;
import com.example.umc_9th_final_5th.domain.review.repository.ReviewQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewFilterService {

    private final ReviewQueryRepository reviewQueryRepository;

    /**
     * 내가 작성한 리뷰 조회 (필터링 옵션 포함)
     * @param memberId 사용자 ID
     * @param storeName 가게 이름 (선택적)
     * @param starFilter 별점 필터 (5, 4, 3, 2, 1) (선택적)
     * @return 필터링된 리뷰 목록
     */
    public List<Review> getMyReviews(Long memberId, String storeName, Integer starFilter) {

        // 필터 조건에 따라 다른 메서드 호출
        if (storeName != null && !storeName.trim().isEmpty() && starFilter != null) {
            // 가게 이름 + 별점 둘 다 필터링
            return getReviewsByStoreNameAndStar(memberId, storeName, starFilter);
        } else if (storeName != null && !storeName.trim().isEmpty()) {
            // 가게 이름만 필터링
            return reviewQueryRepository.findByMemberIdAndStoreNameContaining(memberId, storeName);
        } else if (starFilter != null) {
            // 별점만 필터링
            return getReviewsByStar(memberId, starFilter);
        } else {
            // 필터 없음 - 모든 리뷰
            return reviewQueryRepository.findByWriter_IdOrderByCreatedAtDesc(memberId);
        }
    }

    /**
     * 별점으로 필터링
     */
    private List<Review> getReviewsByStar(Long memberId, Integer starFilter) {
        if (starFilter == 5) {
            // 5점 정확히
            return reviewQueryRepository.findByMemberIdAndStar(memberId, 5.0f);
        } else if (starFilter == 4) {
            // 4점대 (4.0 이상 5.0 미만)
            return reviewQueryRepository.findByMemberIdAndStarBetween(memberId, 4.0f, 5.0f);
        } else if (starFilter == 3) {
            // 3점대 (3.0 이상 4.0 미만)
            return reviewQueryRepository.findByMemberIdAndStarBetween(memberId, 3.0f, 4.0f);
        } else if (starFilter == 2) {
            // 2점대 (2.0 이상 3.0 미만)
            return reviewQueryRepository.findByMemberIdAndStarBetween(memberId, 2.0f, 3.0f);
        } else if (starFilter == 1) {
            // 1점대 (1.0 이상 2.0 미만)
            return reviewQueryRepository.findByMemberIdAndStarBetween(memberId, 1.0f, 2.0f);
        }

        // 잘못된 starFilter 값인 경우 모든 리뷰 반환
        return reviewQueryRepository.findByWriter_IdOrderByCreatedAtDesc(memberId);
    }

    /**
     * 가게 이름 + 별점으로 필터링
     */
    private List<Review> getReviewsByStoreNameAndStar(Long memberId, String storeName, Integer starFilter) {
        if (starFilter == 5) {
            // 5점 정확히
            return reviewQueryRepository.findByMemberIdAndStoreNameAndStar(memberId, storeName, 5.0f);
        } else if (starFilter == 4) {
            // 4점대 (4.0 이상 5.0 미만)
            return reviewQueryRepository.findByMemberIdAndStoreNameAndStarBetween(memberId, storeName, 4.0f, 5.0f);
        } else if (starFilter == 3) {
            // 3점대 (3.0 이상 4.0 미만)
            return reviewQueryRepository.findByMemberIdAndStoreNameAndStarBetween(memberId, storeName, 3.0f, 4.0f);
        } else if (starFilter == 2) {
            // 2점대 (2.0 이상 3.0 미만)
            return reviewQueryRepository.findByMemberIdAndStoreNameAndStarBetween(memberId, storeName, 2.0f, 3.0f);
        } else if (starFilter == 1) {
            // 1점대 (1.0 이상 2.0 미만)
            return reviewQueryRepository.findByMemberIdAndStoreNameAndStarBetween(memberId, storeName, 1.0f, 2.0f);
        }

        // 잘못된 starFilter 값인 경우 가게 이름만으로 필터링
        return reviewQueryRepository.findByMemberIdAndStoreNameContaining(memberId, storeName);
    }

    /**
     * 리뷰 정보를 콘솔에 출력하는 메서드 (테스트용)
     */
    public void printReviews(List<Review> reviews) {
        System.out.println("=== 내가 작성한 리뷰 목록 ===");
        System.out.println("총 " + reviews.size() + "개의 리뷰");

        for (Review review : reviews) {
            System.out.println("------------------");
            System.out.println("가게: " + review.getStore().getName());
            System.out.println("별점: " + review.getStar() + "점");
            System.out.println("내용: " + review.getContent());
            System.out.println("작성일: " + review.getCreatedAt());
        }
    }
}
