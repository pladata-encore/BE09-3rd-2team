package com.gp.nut.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;           // 장소명

    private String address;        // 주소
    private String priceRange;     // 가격대
    private String description;    // 설명

    private Long scheduleId;       // Schedule 서비스의 스케줄 ID
    private Long registeredBy;     // User 서비스의 사용자 ID

    @CreationTimestamp
    private LocalDateTime createdAt;

}
