package com.example.umc_9th_final_5th.domain.review.dto.req;

import jakarta.validation.constraints.*;

public class ReviewReqDTO {

    /**
     * 리뷰 생성 요청 DTO
     */
    public record CreateReviewDTO(
            @NotNull(message = "가게 ID는 필수입니다")
            Long storeId,

            @NotNull(message = "별점은 필수입니다")
            @DecimalMin(value = "0.0", message = "별점은 0.0 이상이어야 합니다")
            @DecimalMax(value = "5.0", message = "별점은 5.0 이하여야 합니다")
            Float star,

            @NotBlank(message = "리뷰 내용은 필수입니다")
            @Size(min = 1, max = 1000, message = "리뷰 내용은 1자 이상 1000자 이하여야 합니다")
            String content
    ) {}
}


