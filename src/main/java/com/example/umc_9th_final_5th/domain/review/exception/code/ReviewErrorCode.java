package com.example.umc_9th_final_5th.domain.review.exception.code;

import com.example.umc_9th_final_5th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND,
            "REVIEW404_1",
            "존재하지 않는 리뷰입니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND,
            "REVIEW404_2",
            "존재하지 않는 가게입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,
            "REVIEW404_3",
            "존재하지 않는 회원입니다."),
    INVALID_STAR_RATING(HttpStatus.BAD_REQUEST,
            "REVIEW400_1",
            "별점은 0.0에서 5.0 사이여야 합니다."),
    REVIEW_CONTENT_TOO_SHORT(HttpStatus.BAD_REQUEST,
            "REVIEW400_2",
            "리뷰 내용은 최소 1자 이상이어야 합니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
