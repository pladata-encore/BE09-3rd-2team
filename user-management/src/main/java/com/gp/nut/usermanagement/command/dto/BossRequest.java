package com.gp.nut.usermanagement.command.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BossRequest {
    @NotNull(message = "코드는 필수입니다.")
    @Pattern(regexp = "^\\d{4}$", message = "코드는 4자리 숫자여야 합니다.")
    private String certCode;
}
