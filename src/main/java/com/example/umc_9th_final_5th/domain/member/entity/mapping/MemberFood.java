package com.example.umc_9th_final_5th.domain.member.entity.mapping;

import com.example.umc_9th_final_5th.domain.member.entity.Food;
import com.example.umc_9th_final_5th.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "member_food",
        uniqueConstraints = @UniqueConstraint(name="uk_member_food", columnNames = {"member_id","food_id"})
)
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberFood {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_food_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable=false,
            foreignKey=@ForeignKey(name="fk_member_food__member"))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="food_id", nullable=false,
            foreignKey=@ForeignKey(name="fk_member_food__food"))
    private Food food;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    @PrePersist void prePersist() { if (createdAt == null) createdAt = LocalDateTime.now(); }
}
