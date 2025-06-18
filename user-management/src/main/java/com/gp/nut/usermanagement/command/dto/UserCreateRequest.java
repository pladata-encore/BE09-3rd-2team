package com.gp.nut.usermanagement.command.dto;

import com.gp.nut.usermanagement.command.entity.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreateRequest {
    private final String username;
    private final String password;
    private final String name;
}
