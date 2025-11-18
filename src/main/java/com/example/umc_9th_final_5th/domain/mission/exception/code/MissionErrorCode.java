package com.example.umc_9th_final_5th.domain.mission.exception.code;

import com.example.umc_9th_final_5th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND,
            "MISSION404_1",
            "존재하지 않는 미션입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,
            "MISSION404_2",
            "존재하지 않는 회원입니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND,
            "MISSION404_3",
            "존재하지 않는 가게입니다."),
    MISSION_ALREADY_CHALLENGING(HttpStatus.CONFLICT,
            "MISSION409_1",
            "이미 도전 중인 미션입니다."),
    MISSION_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST,
            "MISSION400_1",
            "이미 완료한 미션입니다."),
    MISSION_EXPIRED(HttpStatus.BAD_REQUEST,
            "MISSION400_2",
            "마감된 미션입니다."),
    INVALID_DEADLINE(HttpStatus.BAD_REQUEST,
            "MISSION400_3",
            "미션 마감일은 현재 날짜 이후여야 합니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
