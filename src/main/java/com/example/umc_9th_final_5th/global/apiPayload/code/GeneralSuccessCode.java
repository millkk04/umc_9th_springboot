package com.example.umc_9th_final_5th.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralSuccessCode implements BaseSuccessCode {

    OK(HttpStatus.OK,
            "COMMON200_1",
            "성공했습니다."),
    CREATED(HttpStatus.CREATED,
            "COMMON201_1",
            "생성되었습니다."),
    ACCEPTED(HttpStatus.ACCEPTED,
            "COMMON202_1",
            "요청이 접수되었습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT,
            "COMMON204_1",
            "요청이 성공적으로 처리되었습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
