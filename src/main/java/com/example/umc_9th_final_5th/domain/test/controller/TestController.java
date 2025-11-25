package com.example.umc_9th_final_5th.domain.test.controller;

import com.example.umc_9th_final_5th.domain.test.converter.TestConverter;
import com.example.umc_9th_final_5th.domain.test.dto.res.TestResDTO;
import com.example.umc_9th_final_5th.domain.test.service.query.TestQueryService;
import com.example.umc_9th_final_5th.global.apiPayload.ApiResponse;
import com.example.umc_9th_final_5th.global.apiPayload.code.GeneralSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Test API", description = "APIs for testing purposes")
@RestController
@RequiredArgsConstructor
@RequestMapping("/temp")
public class TestController {

    private final TestQueryService testQueryService;

    @Operation(summary = "테스트 API", description = "테스트용 API입니다.")
    @GetMapping("/test")
    public ApiResponse<TestResDTO.Testing> test() throws Exception {
        // 응답 코드 정의
        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(
                code,
                TestConverter.toTestingDTO("This is Test!")
        );


    }

    @Operation(summary = "예외 테스트 API", description = "예외 상황을 테스트합니다.")
    // 예외 상황
    @GetMapping("/exception")
    public ApiResponse<TestResDTO.Exception> exception(
            @RequestParam Long flag
    ) {
        testQueryService.checkFlag(flag);

        // 응답 코드 정의
        GeneralSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, TestConverter.toExceptionDTO("This is Test!"));
    }
}
