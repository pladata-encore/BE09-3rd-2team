package com.gp.nut.usermanagement.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class HeaderAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // permitAll 경로는 헤더 검증 건너뛰기
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        log.info("Request URI: {}, Method: {}", requestURI, method);

        // POST /goodplace/user-management/user/register와 POST /goodplace/user-management/auth/login은 건너뛰기
        if ("POST".equals(method) &&
                (requestURI.equals("/goodplace/user-management/user/register") ||
                        requestURI.equals("/goodplace/user-management/auth/login"))) {
            log.info("Skipping header authentication for permitAll path: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // API Gateway가 전달한 헤더 읽기
        String userId = request.getHeader("X-User-Id");
        String role = request.getHeader("X-User-Role");

        log.info("userId : {}", userId);
        log.info("role : {}", role);

        if (userId != null && role != null) {
            // 이미 Gateway에서 검증된 정보로 인증 객체 구성
            PreAuthenticatedAuthenticationToken authentication =
                    new PreAuthenticatedAuthenticationToken(userId, null,
                            List.of(new SimpleGrantedAuthority(role)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}