package com.example.umc_9th_final_5th.domain.review.controller;

import com.example.umc_9th_final_5th.domain.review.dto.res.ReviewResDTO;
import com.example.umc_9th_final_5th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@Tag(name = "Review", description = "리뷰 관련 API")
public interface ReviewControllerDocs {

    @Operation(
            summary = "내가 작성한 리뷰 목록 조회 API",
            description = "회원 ID에 해당하는 회원이 작성한 리뷰 목록을 페이징 처리하여 조회합니다. " +
                         "한 페이지당 10개씩 조회되며, 가게 이름과 별점으로 필터링할 수 있습니다."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "내가 작성한 리뷰 목록 조회 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "회원을 찾을 수 없음"
            )
    })
    ApiResponse<ReviewResDTO.ReviewPageDto> getMyReviewsByPath(
            @Parameter(description = "회원 ID", required = true, example = "11")
            Long memberId,

            @Parameter(description = "가게 이름 필터 (부분 일치)", example = "맛집")
            String storeName,

            @Parameter(description = "별점 필터 (1~5)", example = "5")
            Integer star,

            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @PageableDefault(size = 10, sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC)
            Pageable pageable
    );
}
