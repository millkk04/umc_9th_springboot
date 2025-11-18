package com.example.umc_9th_final_5th.domain.mission.dto.req;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class MissionReqDTO {

    /**
     * 미션 도전 요청 DTO
     * memberId와 missionId는 PathVariable로 받으므로 요청 본문 없음
     */
    public record ChallengeMissionDTO() {
        // PathVariable로 memberId, missionId를 받으므로 body는 비어있음
    }

    /**
     * 미션 생성 요청 DTO
     */
    public record CreateMissionDTO(
            @NotNull(message = "미션 마감일은 필수입니다")
            @Future(message = "미션 마감일은 현재 날짜 이후여야 합니다")
            LocalDate deadline,

            @NotBlank(message = "미션 조건은 필수입니다")
            @Size(max = 255, message = "미션 조건은 255자 이내여야 합니다")
            String conditional,

            @NotNull(message = "포인트는 필수입니다")
            @Min(value = 0, message = "포인트는 0 이상이어야 합니다")
            @Max(value = 100000, message = "포인트는 100000 이하여야 합니다")
            Integer point
    ) {}
}
