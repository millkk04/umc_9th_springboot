package com.example.umc_9th_3week.domain.mission.entity.mapping;

import com.example.umc_9th_3week.domain.mission.entity.Mission;
import com.example.umc_9th_3week.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "missions")
@Getter
@NoArgsConstructor
public class MissionStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** FK: store_id → stores.id */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "mission_info", nullable = false, length = 255)
    private String missionInfo;

    @Column(nullable = false)
    private Integer reward = 500;

    @Column(name = "due_date", nullable = false)
    private java.time.LocalDateTime dueDate;

    @Column(name = "created_at", updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private java.time.LocalDateTime createdAt;

    /** Mission ←→ MemberMission (1:N) */
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<com.example.umc_9th_3week.domain.member.entity.mapping.MemberMission> userMissions;

    /** Mission ←→ MissionVerification (1:N) */
    @OneToMany(mappedBy = "userMission", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<com.example.umc_9th_3week.domain.mission.entity.mapping.MissionVerification> verifications;
}
