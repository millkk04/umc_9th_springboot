package com.example.umc_9th_final_5th.domain.mission.entity;

import com.example.umc_9th_final_5th.domain.member.entity.mapping.MemberMission;
import com.example.umc_9th_final_5th.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="mission")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Mission {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mission_id")
    private Long id;

    @Column(name="deadline", nullable=false)
    private LocalDate deadline;

    @Column(name="conditional", nullable=false, length=255)
    private String conditional;

    @Column(name="point", nullable=false)
    private Integer point;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id", nullable=false,
            foreignKey=@ForeignKey(name="fk_mission__store"))
    private Store store;

    @OneToMany(mappedBy = "mission", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<MemberMission> memberMissions = new ArrayList<>();

    @PrePersist void prePersist() { if (createdAt == null) createdAt = LocalDateTime.now(); }
}
