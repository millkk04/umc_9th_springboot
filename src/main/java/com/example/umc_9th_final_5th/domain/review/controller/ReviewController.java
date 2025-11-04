package com.example.umc_9th_final_5th.domain.review.controller;

import com.example.umc_9th_final_5th.domain.review.dto.ReviewResponseDto;
import com.example.umc_9th_final_5th.domain.review.entity.Review;
import com.example.umc_9th_final_5th.domain.review.service.ReviewFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewFilterService reviewFilterService;

    /**
     * 내가 작성한 리뷰 조회 API
     * @param memberId 회원 ID (PathVariable)
     * @param storeName 가게 이름 필터 (선택, QueryParam)
     * @param star 별점 필터 (선택, QueryParam) - 5, 4, 3, 2, 1
     * @return 필터링된 리뷰 목록
     */
    @GetMapping("/members/{memberId}")
    public ResponseEntity<ReviewResponseDto.ReviewListDto> getMyReviews(
            @PathVariable Long memberId,
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) Integer star
    ) {
        List<Review> reviews = reviewFilterService.getMyReviews(memberId, storeName, star);
        ReviewResponseDto.ReviewListDto response = ReviewResponseDto.ReviewListDto.of(reviews);

        return ResponseEntity.ok(response);
    }
}

