package com.gp.nut.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LocationUpdateRequestDTO {

    @NotBlank(message = "장소명은 필수입니다.")
    private String name;
    private String address;
    private String priceRange;
    private String description;

}
