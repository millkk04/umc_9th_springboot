package com.example.umc_9th_3week.domain.member.entity.mapping;

import com.example.umc_9th_3week.domain.member.entity.Member;
import com.example.umc_9th_3week.domain.mission.entity.Mission;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_points")
@Getter
@NoArgsConstructor
public class MemberPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Column(nullable = false)
    private Integer points;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
