package com.example.umc_9th_final_5th.domain.store.exception;

import com.example.umc_9th_final_5th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class StoreException extends RuntimeException {
    private final BaseErrorCode errorCode;

    public StoreException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
