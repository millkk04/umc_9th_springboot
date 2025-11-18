package com.example.umc_9th_final_5th.global.validator;

import com.example.umc_9th_final_5th.domain.member.repository.MemberRepository;
import com.example.umc_9th_final_5th.global.annotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final MemberRepository memberRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // null이나 빈 문자열은 @NotBlank에서 처리
        if (email == null || email.isBlank()) {
            return true;
        }

        // 이메일이 이미 존재하면 false 반환 (검증 실패)
        return !memberRepository.existsByEmail(email);
    }
}

