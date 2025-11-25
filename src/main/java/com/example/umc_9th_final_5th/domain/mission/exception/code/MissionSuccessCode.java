package com.example.umc_9th_final_5th.domain.mission.exception.code;

import com.example.umc_9th_final_5th.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    MISSION_CREATED(HttpStatus.CREATED,
            "MISSION201_1",
            "미션이 성공적으로 생성되었습니다."),
    MISSION_FOUND(HttpStatus.OK,
            "MISSION200_1",
            "미션 조회에 성공했습니다."),
    MISSION_LIST_FOUND(HttpStatus.OK,
            "MISSION200_2",
            "미션 목록 조회에 성공했습니다."),
    MISSION_LIST_RETRIEVED(HttpStatus.OK,
            "MISSION200_7",
            "특정 가게의 미션 목록 조회에 성공했습니다."),
    MY_MISSION_LIST_RETRIEVED(HttpStatus.OK,
            "MISSION200_8",
            "내가 진행 중인 미션 목록 조회에 성공했습니다."),
    MISSION_STARTED(HttpStatus.OK,
            "MISSION200_3",
            "미션 도전이 시작되었습니다."),
    MISSION_COMPLETED(HttpStatus.OK,
            "MISSION200_4",
            "미션이 완료되었습니다."),
    MISSION_UPDATED(HttpStatus.OK,
            "MISSION200_5",
            "미션이 수정되었습니다."),
    MISSION_DELETED(HttpStatus.OK,
            "MISSION200_6",
            "미션이 삭제되었습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}

