package com.example.umc_9th_final_5th.domain.member.dto.req;

import com.example.umc_9th_final_5th.domain.member.enums.Gender;
import com.example.umc_9th_final_5th.domain.store.enums.Address;
import com.example.umc_9th_final_5th.global.annotation.ExistFoods;
import com.example.umc_9th_final_5th.global.annotation.UniqueEmail;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {

    public record JoinDTO(
            @NotBlank(message = "이름은 필수입니다")
            @Size(min = 2, max = 50, message = "이름은 2-50자 이내여야 합니다")
            String name,

            @NotNull(message = "성별은 필수입니다")
            Gender gender,

            @NotNull(message = "생년월일은 필수입니다")
            @Past(message = "생년월일은 과거 날짜여야 합니다")
            LocalDate birth,

            @NotNull(message = "주소는 필수입니다")
            Address address,

            @NotBlank(message = "상세주소는 필수입니다")
            @Size(max = 255, message = "상세주소는 255자 이내여야 합니다")
            String specAddress,

            @NotBlank(message = "이메일은 필수입니다")
            @Email(message = "올바른 이메일 형식이 아닙니다")
            @UniqueEmail  // 커스텀 검증: 이메일 중복 확인
            String email,

            @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "휴대폰 번호 형식: 010-XXXX-XXXX")
            String phoneNumber,

            @NotNull(message = "선호 카테고리는 필수입니다")
            @ExistFoods  // 커스텀 검증: 음식 카테고리 존재 여부 확인
            List<Long> preferCategory
    ){}
}
