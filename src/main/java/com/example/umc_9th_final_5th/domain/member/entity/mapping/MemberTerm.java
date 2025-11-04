package com.example.umc_9th_final_5th.domain.member.entity.mapping;

import com.example.umc_9th_final_5th.domain.member.entity.Member;
import com.example.umc_9th_final_5th.domain.member.entity.Term;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "member_term",
        uniqueConstraints = @UniqueConstraint(name="uk_member_term", columnNames = {"member_id","term_id"})
)
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberTerm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_term_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable=false,
            foreignKey = @ForeignKey(name="fk_member_term__member"))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="term_id", nullable=false,
            foreignKey = @ForeignKey(name="fk_member_term__term"))
    private Term term;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    @PrePersist void prePersist() { if (createdAt == null) createdAt = LocalDateTime.now(); }
}
