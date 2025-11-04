package com.example.umc_9th_final_5th.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "term")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Term {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private TermName name; // 아래 enum

    public enum TermName {
        NONE, AGE, SERVICE, PRIVACY, LOCATION, MARKETING
    }
}
