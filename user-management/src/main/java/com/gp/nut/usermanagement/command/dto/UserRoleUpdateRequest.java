package com.gp.nut.usermanagement.command.dto;

import com.gp.nut.usermanagement.command.entity.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRoleUpdateRequest {
    private UserRole role;
}
