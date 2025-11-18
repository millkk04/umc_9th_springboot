package com.example.umc_9th_final_5th.global.validator;

import com.example.umc_9th_final_5th.domain.member.exception.code.FoodErrorCode;
import com.example.umc_9th_final_5th.domain.member.repository.FoodRepository;
import com.example.umc_9th_final_5th.global.annotation.ExistFoods;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FoodExistValidator implements ConstraintValidator<ExistFoods, List<Long>> {

    private final FoodRepository foodRepository;

    @Override
    public boolean isValid(List<Long> values, ConstraintValidatorContext context) {
        // null이거나 빈 리스트는 다른 어노테이션(@NotNull, @NotEmpty)에서 처리
        if (values == null || values.isEmpty()) {
            return true;
        }

        boolean isValid = values.stream()
                .allMatch(value -> foodRepository.existsById(value));

        if (!isValid) {
            // 이 부분에서 아까 디폴트 메시지를 초기화 시키고, 새로운 메시지로 덮어씌우게 됩니다.
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(FoodErrorCode.NOT_FOUND.getMessage())
                    .addConstraintViolation();
        }

        return isValid;
    }
}

