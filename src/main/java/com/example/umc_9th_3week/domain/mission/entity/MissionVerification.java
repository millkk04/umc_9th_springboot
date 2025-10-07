package com.example.umc_9th_3week.domain.mission.entity;

import com.example.umc_9th_3week.domain.member.entity.UserMission;
import com.example.umc_9th_3week.domain.mission.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "mission_verifications")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_mission_id", nullable = false)
    private UserMission userMission;

    @Column(name = "store_owner_id", nullable = false, length = 50)
    private String storeOwnerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus status = VerificationStatus.요청;

    @CreationTimestamp
    @Column(name = "requested_at", updatable = false)
    private LocalDateTime requestedAt;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;
}
