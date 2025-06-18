package com.gp.nut.usermanagement.command.controller;

import com.gp.nut.usermanagement.command.dto.BossRequest;
import com.gp.nut.usermanagement.command.dto.UserCreateRequest;
import com.gp.nut.usermanagement.command.dto.UserRoleUpdateRequest;
import com.gp.nut.usermanagement.command.entity.User;
import com.gp.nut.usermanagement.command.service.UserCommandService;
import com.gp.nut.usermanagement.common.ApiResponse;
import com.gp.nut.usermanagement.common.Errorcode;
import com.gp.nut.usermanagement.common.UserException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserCommandController {

    private final UserCommandService userCommandService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody UserCreateRequest request) {
        userCommandService.registerUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("회원가입이 완료되었습니다."));
    }

    @GetMapping("view/{userId}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long userId) {
        User user = userCommandService.getUserById(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(user));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse<String>> updateUser(@PathVariable Long userId, @RequestBody UserCreateRequest request) {
        userCommandService.updateUser(userId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("회원 정보가 업데이트되었습니다."));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long userId) {
        userCommandService.deleteUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("회원 정보가 삭제되었습니다."));
    }

    @PutMapping("{userId}/role")
    public ResponseEntity<ApiResponse<String>> updateUserRole(@PathVariable Long userId, @RequestBody UserRoleUpdateRequest request) {
        userCommandService.updateUserRole(userId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("사용자 권한이 업데이트되었습니다."));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<ApiResponse<String>> registerAdmin(@RequestBody UserCreateRequest request) {
        userCommandService.registerAdmin(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("관리자 회원가입이 완료되었습니다."));
    }

    @PostMapping("/bossrequest")
    public ResponseEntity<ApiResponse<String>> requestBossRole(
            @Valid @RequestBody BossRequest request) {
        if (!"0000".equals(request.getCertCode())) {
            throw new UserException(Errorcode.INVALID_CERT_CODE);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("권한 수정 요청이 완료되었습니다."));
    }





}
