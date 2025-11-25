package com.example.umc_9th_final_5th.domain.mission.controller;

import com.example.umc_9th_final_5th.domain.member.entity.mapping.MemberMission;
import com.example.umc_9th_final_5th.domain.mission.converter.MissionConverter;
import com.example.umc_9th_final_5th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc_9th_final_5th.domain.mission.dto.res.MissionResDTO;
import com.example.umc_9th_final_5th.domain.mission.entity.Mission;
import com.example.umc_9th_final_5th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc_9th_final_5th.domain.mission.service.command.MissionCommandService;
import com.example.umc_9th_final_5th.domain.mission.service.query.MissionQueryService;
import com.example.umc_9th_final_5th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mission API", description = "APIs related to missions")
@RestController
@RequiredArgsConstructor
public class MissionController {

    private final MissionCommandService missionCommandService;
    private final MissionQueryService missionQueryService;

    /**
     * 미션 도전하기 API
     * @param memberId 회원 ID (PathVariable)
     * @param missionId 미션 ID (PathVariable)
     * @return 생성된 MemberMission 정보
     */
    @Operation(summary = "미션 도전하기 API", description = "회원이 특정 미션에 도전합니다.")
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
    @Operation(summary = "가게에 미션 추가하기 API", description = "특정 가게에 새로운 미션을 추가합니다.")
    @PostMapping("/api/stores/{storeId}/missions")
    public ApiResponse<MissionResDTO.CreateMissionResultDTO> createMission(
            @PathVariable Long storeId,
            @Valid @RequestBody MissionReqDTO.CreateMissionDTO request
    ) {
        Mission mission = missionCommandService.createMission(storeId, request);
        MissionResDTO.CreateMissionResultDTO response = MissionConverter.toCreateMissionResultDTO(mission);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_CREATED, response);
    }

    /**
     * 특정 가게의 미션 목록 조회 API (페이징)
     * @param storeId 가게 ID (PathVariable)
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 페이징된 미션 목록
     */
    @GetMapping("/api/stores/{storeId}/missions")
    public ApiResponse<MissionResDTO.MissionPageDTO> getMissionsByStore(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Page<Mission> missionPage = missionQueryService.getMissionsByStore(storeId, page, size);
        MissionResDTO.MissionPageDTO response = MissionConverter.toMissionPageDTO(missionPage);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_RETRIEVED, response);
    }

    /**
     * 내가 진행 중인 미션 목록 조회 API (페이징)
     * @param memberId 회원 ID (PathVariable)
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 페이징된 내 미션 목록
     */
    @GetMapping("/api/members/{memberId}/missions")
    public ApiResponse<MissionResDTO.MyMissionPageDTO> getMyMissions(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Page<MemberMission> memberMissionPage = missionQueryService.getMyMissions(memberId, page, size);
        MissionResDTO.MyMissionPageDTO response = MissionConverter.toMyMissionPageDTO(memberMissionPage);
        return ApiResponse.onSuccess(MissionSuccessCode.MY_MISSION_LIST_RETRIEVED, response);
    }
}
