package com.example.umc_9th_final_5th.domain.store.entity;

import com.example.umc_9th_final_5th.domain.mission.entity.Mission;
import com.example.umc_9th_final_5th.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="store")
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="store_id")
    private Long id;

    @Column(name="name", nullable=false, length=100)
    private String name;

    @Column(name="manager_number", nullable=false)
    private Long managerNumber;

    @Column(name="detail_address", nullable=false, length=255)
    private String detailAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="location_id", nullable=false,
            foreignKey=@ForeignKey(name="fk_store__location"))
    private Location location;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable=false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "store", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Mission> missions = new ArrayList<>();

    @OneToMany(mappedBy = "store", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) createdAt = now;
        if (updatedAt == null) updatedAt = now;
    }
    @PreUpdate void preUpdate() { updatedAt = LocalDateTime.now(); }
}
