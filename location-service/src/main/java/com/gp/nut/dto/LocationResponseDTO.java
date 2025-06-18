package com.gp.nut.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LocationResponseDTO {

    private Long id;
    private String name;
    private String address;
    private String priceRange;
    private String description;
    private Long scheduleId;
    private Long registeredBy;
    private LocalDateTime createdAt;

}
