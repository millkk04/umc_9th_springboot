package com.example.umc_9th_final_5th.domain.store.exception.code;

import com.example.umc_9th_final_5th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StoreErrorCode implements BaseErrorCode {

    STORE_NOT_FOUND(HttpStatus.NOT_FOUND,
            "STORE404_1",
            "존재하지 않는 가게입니다."),
    LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND,
            "STORE404_2",
            "존재하지 않는 지역입니다."),
    INVALID_MANAGER_NUMBER(HttpStatus.BAD_REQUEST,
            "STORE400_1",
            "올바르지 않은 관리자 전화번호 형식입니다."),
    STORE_NAME_EMPTY(HttpStatus.BAD_REQUEST,
            "STORE400_2",
            "가게 이름은 필수입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
