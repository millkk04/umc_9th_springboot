package com.example.umc_9th_final_5th.domain.member.exception;

import com.example.umc_9th_final_5th.global.apiPayload.code.BaseErrorCode;
import com.example.umc_9th_final_5th.global.exception.GeneralException;

public class MemberException extends GeneralException {
    public MemberException(BaseErrorCode code) {
        super(code);
    }
}
