package com.example.umc_9th_final_5th.domain.mission.controller;

import com.example.umc_9th_final_5th.domain.member.entity.mapping.MemberMission;
import com.example.umc_9th_final_5th.domain.mission.converter.MissionConverter;
import com.example.umc_9th_final_5th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc_9th_final_5th.domain.mission.dto.res.MissionResDTO;
import com.example.umc_9th_final_5th.domain.mission.entity.Mission;
import com.example.umc_9th_final_5th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc_9th_final_5th.domain.mission.service.command.MissionCommandService;
import com.example.umc_9th_final_5th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MissionController {

    private final MissionCommandService missionCommandService;

    /**
     * 미션 도전하기 API
     * @param memberId 회원 ID (PathVariable)
     * @param missionId 미션 ID (PathVariable)
     * @return 생성된 MemberMission 정보
     */
    @PostMapping("/api/members/{memberId}/missions/{missionId}")
    public ApiResponse<MissionResDTO.ChallengeMissionResultDTO> challengeMission(
            @PathVariable Long memberId,
            @PathVariable Long missionId
    ) {
        MemberMission memberMission = missionCommandService.challengeMission(memberId, missionId);
        MissionResDTO.ChallengeMissionResultDTO response =
                MissionConverter.toChallengeMissionResultDTO(memberMission);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_STARTED, response);
    }

    /**
     * 가게에 미션 추가하기 API
     * @param storeId 가게 ID (PathVariable)
     * @param request 미션 생성 요청 DTO
     * @return 생성된 미션 정보
     */
    @PostMapping("/api/stores/{storeId}/missions")
    public ApiResponse<MissionResDTO.CreateMissionResultDTO> createMission(
            @PathVariable Long storeId,
            @Valid @RequestBody MissionReqDTO.CreateMissionDTO request
    ) {
        Mission mission = missionCommandService.createMission(storeId, request);
        MissionResDTO.CreateMissionResultDTO response = MissionConverter.toCreateMissionResultDTO(mission);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_CREATED, response);
    }
}
