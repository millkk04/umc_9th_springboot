package com.example.umc_9th_final_5th.domain.store.code;

import com.example.umc_9th_final_5th.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StoreSuccessCode implements BaseSuccessCode {

    STORE_CREATED(HttpStatus.CREATED,
            "STORE201_1",
            "가게가 성공적으로 등록되었습니다."),
    STORE_FOUND(HttpStatus.OK,
            "STORE200_1",
            "가게 조회에 성공했습니다."),
    STORE_LIST_FOUND(HttpStatus.OK,
            "STORE200_2",
            "가게 목록 조회에 성공했습니다."),
    STORE_UPDATED(HttpStatus.OK,
            "STORE200_3",
            "가게 정보가 수정되었습니다."),
    STORE_DELETED(HttpStatus.OK,
            "STORE200_4",
            "가게가 삭제되었습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}

