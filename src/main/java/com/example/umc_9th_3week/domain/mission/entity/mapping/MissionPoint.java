package com.example.umc_9th_3week.domain.mission.entity.mapping;

import com.example.umc_9th_3week.domain.member.entity.mapping.MemberPoint;
import com.example.umc_9th_3week.domain.mission.entity.Mission;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "user_points")
@Getter
@NoArgsConstructor
public class MissionPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** FK: mission_id → missions.id */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    private List<MemberPoint> memberPoints;
}
