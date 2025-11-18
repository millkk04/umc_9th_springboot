package com.example.umc_9th_final_5th.domain.mission.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MissionResDTO {

    /**
     * 미션 도전 응답 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChallengeMissionResultDTO {
        private Long memberMissionId;
        private Long memberId;
        private Long missionId;
        private String storeName;
        private String conditional;
        private Integer point;
        private LocalDate deadline;
        private Boolean isComplete;
        private LocalDateTime createdAt;
    }

    /**
     * 미션 생성 응답 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateMissionResultDTO {
        private Long missionId;
        private LocalDate deadline;
        private String conditional;
        private Integer point;
        private LocalDateTime createdAt;
        private Long storeId;
    }
}
