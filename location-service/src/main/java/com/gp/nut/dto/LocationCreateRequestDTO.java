package com.gp.nut.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocationCreateRequestDTO {

    @NotBlank(message = "장소명은 필수입니다.")
    private String name;

    @NotNull(message = "주소는 필수입니다.")
    private String address;

    private String priceRange;
    private String description;

    @NotNull(message = "스케줄 ID는 필수입니다.")
    private Long scheduleId;

    @NotNull(message = "등록자 ID는 필수입니다.")
    private Long registeredBy;

}
