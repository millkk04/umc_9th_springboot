package com.example.umc_9th_final_5th.domain.review.dto.res;

import com.example.umc_9th_final_5th.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewResDTO {

    /**
     * 리뷰 생성 응답 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReviewResultDTO {
        private Long reviewId;
        private Long storeId;
        private String storeName;
        private Float star;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewListDto {
        private Integer totalCount;
        private List<ReviewDto> reviews;

        public static ReviewListDto of(List<Review> reviewList) {
            return ReviewListDto.builder()
                    .totalCount(reviewList.size())
                    .reviews(reviewList.stream()
                            .map(ReviewDto::of)
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewDto {
        private Long reviewId;
        private String storeName;
        private Float star;
        private String content;
        private LocalDateTime createdAt;
        private Integer photoCount;
        private Integer replyCount;

        public static ReviewDto of(Review review) {
            return ReviewDto.builder()
                    .reviewId(review.getId())
                    .storeName(review.getStore().getName())
                    .star(review.getStar())
                    .content(review.getContent())
                    .createdAt(review.getCreatedAt())
                    .photoCount(review.getPhotos() != null ? review.getPhotos().size() : 0)
                    .replyCount(review.getReplies() != null ? review.getReplies().size() : 0)
                    .build();
        }
    }
}

