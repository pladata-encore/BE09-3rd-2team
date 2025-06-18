package com.gp.nut.usermanagement.auth.controller;

import com.gp.nut.usermanagement.auth.dto.LoginRequest;
import com.gp.nut.usermanagement.auth.dto.RefreshTokenRequest;
import com.gp.nut.usermanagement.auth.dto.TokenResponse;
import com.gp.nut.usermanagement.auth.service.AuthService;
import com.gp.nut.usermanagement.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginRequest request) {
        System.out.println("AuthController.login() called with username: " + request.getUsername());
        try {
            TokenResponse token = authService.login(request);
            System.out.println("Login successful, token generated");
            return ResponseEntity.ok(ApiResponse.success(token));
        } catch (Exception e) {
            System.out.println("Login failed with error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenResponse response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /* 로그아웃 기능 작동 원리
    토큰은 유효기간이 있어 사라지기 때문에 리프레시 토큰만 가져와서 삭제해주는 것
    */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody RefreshTokenRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(null));
        // Spring Boot에서 HTTP응답을 생성하는 메서드(HTTP 200 OK 상태코드를 반환)
        // 응답을 표준화하기 위한 메서드
    }



}
