package com.example.umc_9th_final_5th.domain.member.code;

import com.example.umc_9th_final_5th.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    MEMBER_CREATED(HttpStatus.CREATED,
            "MEMBER201_1",
            "회원가입이 완료되었습니다."),
    MEMBER_FOUND(HttpStatus.OK,
            "MEMBER200_1",
            "회원 조회에 성공했습니다."),
    MEMBER_UPDATED(HttpStatus.OK,
            "MEMBER200_2",
            "회원 정보가 수정되었습니다."),
    MEMBER_DELETED(HttpStatus.OK,
            "MEMBER200_3",
            "회원 탈퇴가 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK,
            "MEMBER200_4",
            "로그인에 성공했습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK,
            "MEMBER200_5",
            "로그아웃에 성공했습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}

