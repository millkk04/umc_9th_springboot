package com.example.umc_9th_3week.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_locations")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLocation {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private Member member;

    @Column(nullable = false, length = 100)
    private String district;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
