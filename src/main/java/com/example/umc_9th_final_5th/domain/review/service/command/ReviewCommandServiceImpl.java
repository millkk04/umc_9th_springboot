package com.example.umc_9th_final_5th.domain.review.service.command;

import com.example.umc_9th_final_5th.domain.member.entity.Member;
import com.example.umc_9th_final_5th.domain.member.repository.MemberRepository;
import com.example.umc_9th_final_5th.domain.review.converter.ReviewConverter;
import com.example.umc_9th_final_5th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc_9th_final_5th.domain.review.entity.Review;
import com.example.umc_9th_final_5th.domain.review.exception.ReviewException;
import com.example.umc_9th_final_5th.domain.review.exception.code.ReviewErrorCode;
import com.example.umc_9th_final_5th.domain.review.repository.ReviewRepository;
import com.example.umc_9th_final_5th.domain.store.entity.Store;
import com.example.umc_9th_final_5th.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Override
    public Review createReview(Long memberId, ReviewReqDTO.CreateReviewDTO request) {
        // 1. 가게 존재 여부 확인
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.STORE_NOT_FOUND));

        // 2. 회원 존재 여부 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.MEMBER_NOT_FOUND));

        // 3. Review 엔티티 생성
        Review review = ReviewConverter.toReview(request, store, member);

        // 4. 저장 및 반환
        return reviewRepository.save(review);
    }
}
