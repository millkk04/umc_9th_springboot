package com.example.umc_9th_final_5th.domain.member.service.command;

import com.example.umc_9th_final_5th.domain.member.dto.req.MemberReqDTO;
import com.example.umc_9th_final_5th.domain.member.dto.res.MemberResDTO;

public interface MemberCommandService {
    MemberResDTO.JoinDTO signup(MemberReqDTO.JoinDTO dto);
}
