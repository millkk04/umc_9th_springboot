package com.example.umc_9th_3week.domain.store.entity.mapping;

import com.example.umc_9th_3week.domain.mission.entity.Mission;
import com.example.umc_9th_3week.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "missions")
@Getter
@NoArgsConstructor
public class StoreMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** FK: store_id → stores.id */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "mission_info", nullable = false)
    private String missionInfo;

    @Column(nullable = false)
    private Integer reward = 500;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** Mission → UserMission (1:N) */
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.example.umc_9th_3week.domain.member.entity.mapping.MemberMission> userMissions = new ArrayList<>();
}
