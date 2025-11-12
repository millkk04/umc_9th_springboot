package com.example.umc_9th_final_5th.domain.test.exception;
import com.example.umc_9th_final_5th.global.apiPayload.code.BaseErrorCode;
import com.example.umc_9th_final_5th.global.exception.GeneralException;

public class TestException extends GeneralException {
  public TestException(BaseErrorCode code) {
    super(code);
  }
}
