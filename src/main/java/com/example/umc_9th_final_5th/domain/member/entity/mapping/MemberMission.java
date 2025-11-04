package com.example.umc_9th_final_5th.domain.member.entity.mapping;

import com.example.umc_9th_final_5th.domain.member.entity.Member;
import com.example.umc_9th_final_5th.domain.mission.entity.Mission;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="member_mission",
        uniqueConstraints = @UniqueConstraint(name="uk_member_mission", columnNames = {"member_id","mission_id"})
)
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMission {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_mission_id")
    private Long id;

    @Column(name="is_complete", nullable=false)
    private Boolean isComplete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mission_id", nullable=false,
            foreignKey=@ForeignKey(name="fk_member_mission__mission"))
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable=false,
            foreignKey=@ForeignKey(name="fk_member_mission__member"))
    private Member member;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (isComplete == null) isComplete = false;
    }
}
