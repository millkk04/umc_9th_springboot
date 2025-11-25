package com.example.umc_9th_final_5th.domain.member.controller;

import com.example.umc_9th_final_5th.domain.member.dto.req.MemberReqDTO;
import com.example.umc_9th_final_5th.domain.member.dto.res.MemberResDTO;
import com.example.umc_9th_final_5th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc_9th_final_5th.domain.member.service.command.MemberCommandService;
import com.example.umc_9th_final_5th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member API", description = "APIs related to members")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberCommandService memberCommandService;

    // 회원가입
    @Operation(summary = "회원 가입 API", description = "새로운 회원을 등록합니다.")
    @PostMapping("/register")
    public ApiResponse<MemberResDTO.JoinDTO> register(
            @Valid @RequestBody MemberReqDTO.JoinDTO dto
    ){
        return ApiResponse.onSuccess(MemberSuccessCode.MEMBER_CREATED, memberCommandService.signup(dto));
    }
}
