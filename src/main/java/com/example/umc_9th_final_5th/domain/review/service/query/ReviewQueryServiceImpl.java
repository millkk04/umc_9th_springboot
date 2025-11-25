package com.example.umc_9th_final_5th.domain.review.service.query;

import com.example.umc_9th_final_5th.domain.member.repository.MemberRepository;
import com.example.umc_9th_final_5th.domain.review.entity.Review;
import com.example.umc_9th_final_5th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc_9th_final_5th.domain.review.repository.ReviewQueryRepository;
import com.example.umc_9th_final_5th.domain.review.repository.ReviewRepository;
import com.example.umc_9th_final_5th.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;
    private final MemberRepository memberRepository;


    @Override
    public Page<Review> getMyReviews(Long memberId, String storeName, Integer star, Pageable pageable) {
        // 회원 존재 여부 확인
        memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ReviewErrorCode.MEMBER_NOT_FOUND));

        // 필터 조건에 따라 다른 메서드 호출
        if (storeName != null && !storeName.trim().isEmpty() && star != null) {
            // 가게 이름 + 별점 둘 다 필터링
            return getReviewsByStoreNameAndStar(memberId, storeName, star, pageable);
        } else if (storeName != null && !storeName.trim().isEmpty()) {
            // 가게 이름만 필터링
            return reviewQueryRepository.findByMemberIdAndStoreNameContaining(memberId, storeName, pageable);
        } else if (star != null) {
            // 별점만 필터링
            return getReviewsByStar(memberId, star, pageable);
        } else {
            // 필터 없음 - 모든 리뷰
            return reviewRepository.findByWriterId(memberId, pageable);
        }
    }

    /**
     * 별점으로 필터링 (페이징)
     */
    private Page<Review> getReviewsByStar(Long memberId, Integer starFilter, Pageable pageable) {
        if (starFilter == 5) {
            // 5점 정확히
            return reviewQueryRepository.findByMemberIdAndStar(memberId, 5.0f, pageable);
        } else if (starFilter == 4) {
            // 4점대 (4.0 이상 5.0 미만)
            return reviewQueryRepository.findByMemberIdAndStarBetween(memberId, 4.0f, 5.0f, pageable);
        } else if (starFilter == 3) {
            // 3점대 (3.0 이상 4.0 미만)
            return reviewQueryRepository.findByMemberIdAndStarBetween(memberId, 3.0f, 4.0f, pageable);
        } else if (starFilter == 2) {
            // 2점대 (2.0 이상 3.0 미만)
            return reviewQueryRepository.findByMemberIdAndStarBetween(memberId, 2.0f, 3.0f, pageable);
        } else if (starFilter == 1) {
            // 1점대 (1.0 이상 2.0 미만)
            return reviewQueryRepository.findByMemberIdAndStarBetween(memberId, 1.0f, 2.0f, pageable);
        }

        // 잘못된 starFilter 값인 경우 모든 리뷰 반환
        return reviewRepository.findByWriterId(memberId, pageable);
    }

    /**
     * 가게 이름 + 별점으로 필터링 (페이징)
     */
    private Page<Review> getReviewsByStoreNameAndStar(Long memberId, String storeName, Integer starFilter, Pageable pageable) {
        if (starFilter == 5) {
            // 5점 정확히
            return reviewQueryRepository.findByMemberIdAndStoreNameAndStar(memberId, storeName, 5.0f, pageable);
        } else if (starFilter == 4) {
            // 4점대 (4.0 이상 5.0 미만)
            return reviewQueryRepository.findByMemberIdAndStoreNameAndStarBetween(memberId, storeName, 4.0f, 5.0f, pageable);
        } else if (starFilter == 3) {
            // 3점대 (3.0 이상 4.0 미만)
            return reviewQueryRepository.findByMemberIdAndStoreNameAndStarBetween(memberId, storeName, 3.0f, 4.0f, pageable);
        } else if (starFilter == 2) {
            // 2점대 (2.0 이상 3.0 미만)
            return reviewQueryRepository.findByMemberIdAndStoreNameAndStarBetween(memberId, storeName, 2.0f, 3.0f, pageable);
        } else if (starFilter == 1) {
            // 1점대 (1.0 이상 2.0 미만)
            return reviewQueryRepository.findByMemberIdAndStoreNameAndStarBetween(memberId, storeName, 1.0f, 2.0f, pageable);
        }

        // 잘못된 starFilter 값인 경우 가게 이름만으로 필터링
        return reviewQueryRepository.findByMemberIdAndStoreNameContaining(memberId, storeName, pageable);
    }
}

