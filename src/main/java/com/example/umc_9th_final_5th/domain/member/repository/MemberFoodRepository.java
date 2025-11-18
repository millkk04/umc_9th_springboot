package com.example.umc_9th_final_5th.domain.member.repository;

import com.example.umc_9th_final_5th.domain.member.entity.mapping.MemberFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFoodRepository extends JpaRepository<MemberFood, Long> {
}

