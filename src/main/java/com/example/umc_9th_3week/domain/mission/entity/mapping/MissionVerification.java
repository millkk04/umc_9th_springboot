package com.example.umc_9th_3week.domain.mission.entity.mapping;

import com.example.umc_9th_3week.domain.member.entity.mapping.MemberMission;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "mission_verifications")
@Getter
@NoArgsConstructor
public class MissionVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** FK: user_mission_id → user_missions.id */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_mission_id", nullable = false)
    private MemberMission userMission;

    @Column(name = "store_owner_id", nullable = false)
    private String storeOwnerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private com.example.umc_9th_3week.domain.mission.enums.VerificationStatus status
            = com.example.umc_9th_3week.domain.mission.enums.VerificationStatus.요청;

    @CreationTimestamp
    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;
}
