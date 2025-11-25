package com.example.umc_9th_final_5th.domain.store.controller;

import com.example.umc_9th_final_5th.domain.store.converter.StoreConverter;
import com.example.umc_9th_final_5th.domain.store.dto.req.StoreReqDTO;
import com.example.umc_9th_final_5th.domain.store.dto.res.StoreResDTO;
import com.example.umc_9th_final_5th.domain.store.entity.Store;
import com.example.umc_9th_final_5th.domain.store.exception.code.StoreSuccessCode;
import com.example.umc_9th_final_5th.domain.store.service.command.StoreCommandService;
import com.example.umc_9th_final_5th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Store API", description = "APIs related to stores")
@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreCommandService storeCommandService;

    /**
     * 가게 생성 API
     * @param request 가게 생성 요청 DTO
     * @return 생성된 가게 정보
     */
    @Operation(summary = "가게 생성 API", description = "새로운 가게를 생성합니다.")
    @PostMapping
    public ApiResponse<StoreResDTO.CreateStoreResultDTO> createStore(
            @Valid @RequestBody StoreReqDTO.CreateStoreDTO request
    ) {
        Store store = storeCommandService.createStore(request);
        StoreResDTO.CreateStoreResultDTO response = StoreConverter.toCreateStoreResultDTO(store);
        return ApiResponse.onSuccess(StoreSuccessCode.STORE_CREATED, response);
    }
}
