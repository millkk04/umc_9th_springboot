package com.example.umc_9th_final_5th.domain.mission.dto.res;

import com.example.umc_9th_final_5th.domain.mission.entity.Mission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 특정 가게의 미션 목록 조회 응답 DTO (페이징)
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionPageDTO {
        private Integer currentPage;
        private Integer totalPages;
        private Long totalElements;
        private Integer pageSize;
        private Boolean hasNext;
        private Boolean hasPrevious;
        private List<MissionDTO> missions;

        public static MissionPageDTO of(Page<Mission> missionPage) {
            return MissionPageDTO.builder()
                    .currentPage(missionPage.getNumber())
                    .totalPages(missionPage.getTotalPages())
                    .totalElements(missionPage.getTotalElements())
                    .pageSize(missionPage.getSize())
                    .hasNext(missionPage.hasNext())
                    .hasPrevious(missionPage.hasPrevious())
                    .missions(missionPage.getContent().stream()
                            .map(MissionDTO::of)
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    /**
     * 개별 미션 정보 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionDTO {
        private Long missionId;
        private String conditional;
        private Integer point;
        private LocalDate deadline;
        private LocalDateTime createdAt;
        private Long participantCount;  // 도전 중인 인원 수

        public static MissionDTO of(Mission mission) {
            return MissionDTO.builder()
                    .missionId(mission.getId())
                    .conditional(mission.getConditional())
                    .point(mission.getPoint())
                    .deadline(mission.getDeadline())
                    .createdAt(mission.getCreatedAt())
                    .participantCount((long) (mission.getMemberMissions() != null ? mission.getMemberMissions().size() : 0))
                    .build();
        }
    }

    /**
     * 회원이 진행 중인 미션 목록 조회 응답 DTO (페이징)
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyMissionPageDTO {
        private Integer currentPage;
        private Integer totalPages;
        private Long totalElements;
        private Integer pageSize;
        private Boolean hasNext;
        private Boolean hasPrevious;
        private List<MyMissionDTO> missions;

        public static MyMissionPageDTO of(Page<MyMissionDTO> missionPage) {
            return MyMissionPageDTO.builder()
                    .currentPage(missionPage.getNumber())
                    .totalPages(missionPage.getTotalPages())
                    .totalElements(missionPage.getTotalElements())
                    .pageSize(missionPage.getSize())
                    .hasNext(missionPage.hasNext())
                    .hasPrevious(missionPage.hasPrevious())
                    .missions(missionPage.getContent())
                    .build();
        }
    }

    /**
     * 회원이 진행 중인 개별 미션 정보 DTO
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyMissionDTO {
        private Long memberMissionId;
        private Long missionId;
        private String storeName;
        private String conditional;
        private Integer point;
        private LocalDate deadline;
        private Boolean isComplete;
        private LocalDateTime startedAt;
    }
}
