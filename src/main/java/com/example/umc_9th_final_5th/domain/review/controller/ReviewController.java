package com.example.umc_9th_final_5th.domain.review.controller;

import com.example.umc_9th_final_5th.domain.review.converter.ReviewConverter;
import com.example.umc_9th_final_5th.domain.review.dto.req.ReviewReqDTO;
import com.example.umc_9th_final_5th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc_9th_final_5th.domain.review.dto.res.ReviewResDTO;
import com.example.umc_9th_final_5th.domain.review.entity.Review;
import com.example.umc_9th_final_5th.domain.review.service.command.ReviewCommandService;
import com.example.umc_9th_final_5th.domain.review.service.query.ReviewQueryService;
import com.example.umc_9th_final_5th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review API", description = "APIs related to reviews")
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController implements ReviewControllerDocs {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    /**
     * 리뷰 생성 API
     * @param memberId 작성자 ID (임시로 쿼리 파라미터로 받음. 추후 인증 토큰에서 추출)
     * @param request 리뷰 생성 요청 DTO
     * @return 생성된 리뷰 정보
     */
    @Operation(summary = "리뷰 생성 API", description = "회원이 새로운 리뷰를 작성합니다.")
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
     * 내가 작성한 리뷰 목록 조회 API
     * @param memberId 회원 ID (PathVariable)
     * @param storeName 가게 이름 필터 (선택)
     * @param star 별점 필터 (선택) - 5, 4, 3, 2, 1
     * @param pageable 페이징 정보 (page, size, sort)
     * @return 페이징 처리된 리뷰 목록
     */
    @Operation(summary = "내가 작성한 리뷰 목록 조회 API", description = "회원이 작성한 리뷰 목록을 조회합니다.")
    @GetMapping("/members/{memberId}")
    @Override
    public ApiResponse<ReviewResDTO.ReviewPageDto> getMyReviewsByPath(
            @PathVariable Long memberId,
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) Integer star,
            @PageableDefault(sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Review> reviewPage = reviewQueryService.getMyReviews(memberId, storeName, star, pageable);
        ReviewResDTO.ReviewPageDto response = ReviewResDTO.ReviewPageDto.of(reviewPage);
        return ApiResponse.onSuccess(ReviewSuccessCode.MY_REVIEW_LIST_FOUND, response);
    }
}
