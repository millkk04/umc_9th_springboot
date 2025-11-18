package com.example.umc_9th_final_5th.domain.member.controller;

import com.example.umc_9th_final_5th.domain.member.dto.req.MemberReqDTO;
import com.example.umc_9th_final_5th.domain.member.dto.res.MemberResDTO;
import com.example.umc_9th_final_5th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc_9th_final_5th.domain.member.service.command.MemberCommandService;
import com.example.umc_9th_final_5th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberCommandService memberCommandService;

    // 회원가입
    @PostMapping("/register")
    public ApiResponse<MemberResDTO.JoinDTO> register(
            @Valid @RequestBody MemberReqDTO.JoinDTO dto
    ){
        return ApiResponse.onSuccess(MemberSuccessCode.MEMBER_CREATED, memberCommandService.signup(dto));
    }
}

