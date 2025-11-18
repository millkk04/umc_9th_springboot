package com.example.umc_9th_final_5th.domain.review.exception;

import com.example.umc_9th_final_5th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException {
    private final BaseErrorCode errorCode;

    public ReviewException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
