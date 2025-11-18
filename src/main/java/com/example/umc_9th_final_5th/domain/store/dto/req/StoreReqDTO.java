package com.example.umc_9th_final_5th.domain.store.dto.req;

import jakarta.validation.constraints.*;

public class StoreReqDTO {

    /**
     * 가게 생성 요청 DTO
     */
    public record CreateStoreDTO(
            @NotBlank(message = "가게 이름은 필수입니다")
            @Size(max = 100, message = "가게 이름은 100자 이내여야 합니다")
            String name,

            @NotNull(message = "관리자 전화번호는 필수입니다")
            @Min(value = 1000000000L, message = "올바른 전화번호를 입력해주세요")
            @Max(value = 9999999999L, message = "올바른 전화번호를 입력해주세요")
            Long managerNumber,

            @NotBlank(message = "상세 주소는 필수입니다")
            @Size(max = 255, message = "상세 주소는 255자 이내여야 합니다")
            String detailAddress,

            @NotNull(message = "지역 ID는 필수입니다")
            Long locationId
    ) {}
}
