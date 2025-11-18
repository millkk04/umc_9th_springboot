package com.example.umc_9th_final_5th.domain.review.controller;

import com.example.umc_9th_final_5th.domain.review.converter.ReviewConverter;
import com.example.umc_9th_final_5th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc_9th_final_5th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc_9th_final_5th.domain.review.dto.res.ReviewResDTO;
import com.example.umc_9th_final_5th.domain.review.entity.Review;
import com.example.umc_9th_final_5th.domain.review.service.ReviewFilterService;
import com.example.umc_9th_final_5th.domain.review.service.command.ReviewCommandService;
import com.example.umc_9th_final_5th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewFilterService reviewFilterService;
    private final ReviewCommandService reviewCommandService;

    /**
     * 리뷰 생성 API
     * @param memberId 작성자 ID (임시로 쿼리 파라미터로 받음. 추후 인증 토큰에서 추출)
     * @param request 리뷰 생성 요청 DTO
     * @return 생성된 리뷰 정보
     */
    @PostMapping
    public ApiResponse<ReviewResDTO.CreateReviewResultDTO> createReview(
            @RequestParam Long memberId,  // 추후 @AuthenticationPrincipal 등으로 변경
            @Valid @RequestBody ReviewReqDTO.CreateReviewDTO request
    ) {
        Review review = reviewCommandService.createReview(memberId, request);
        ReviewResDTO.CreateReviewResultDTO response = ReviewConverter.toCreateReviewResultDTO(review);
        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_CREATED, response);
    }

    /**
     * 내가 작성한 리뷰 조회 API
     * @param memberId 회원 ID (PathVariable)
     * @param storeName 가게 이름 필터 (선택, QueryParam)
     * @param star 별점 필터 (선택, QueryParam) - 5, 4, 3, 2, 1
     * @return 필터링된 리뷰 목록
     */
    @GetMapping("/members/{memberId}")
    public ApiResponse<ReviewResDTO.ReviewListDto> getMyReviews(
            @PathVariable Long memberId,
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) Integer star
    ) {
        List<Review> reviews = reviewFilterService.getMyReviews(memberId, storeName, star);
        ReviewResDTO.ReviewListDto response = ReviewResDTO.ReviewListDto.of(reviews);
        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_LIST_FOUND, response);
    }
}

