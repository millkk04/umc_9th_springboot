package com.example.umc_9th_final_5th.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewListResponse {
        private List<ReviewInfo> reviews;
        private Long totalCount;
        private Boolean hasNext;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewInfo {
        private Long reviewId;
        private String content;
        private Float star;
        private LocalDateTime createdAt;
        private String storeName;
        private List<String> photoUrls;
        private String writerName;
    }
}
