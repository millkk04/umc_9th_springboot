package com.example.umc_9th_final_5th.domain.member.exception;

import com.example.umc_9th_final_5th.global.apiPayload.code.BaseErrorCode;
import com.example.umc_9th_final_5th.global.exception.GeneralException;

public class FoodException extends GeneralException {
    public FoodException(BaseErrorCode code) {
        super(code);
    }
}

