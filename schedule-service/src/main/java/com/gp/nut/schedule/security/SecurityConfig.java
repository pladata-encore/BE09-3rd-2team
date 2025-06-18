package com.gp.nut.schedule.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAccessDeniedHandler restAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session
                    -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(exception ->
                exception
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(restAccessDeniedHandler)
        )
        .authorizeHttpRequests(auth -> // BOSS, EMPLOYEE, ADMIN
                auth
                    // Swagger 경로 예외 처리
                    .requestMatchers(
                        "/favicon.ico",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/v2/api-docs",
                        "/webjars/**"
                    ).permitAll()
                    .requestMatchers(HttpMethod.POST, "/gatherings").permitAll() // BOSS, ADMIN
                    .requestMatchers(HttpMethod.PATCH, "/gatherings/*/date", "/gatherings/*/confirm", "gatherings/*/review").permitAll() // BOSS, ADMIN
                    .requestMatchers(HttpMethod.DELETE, "/gatherings/*").permitAll() // BOSS, ADMIN
                    .requestMatchers(HttpMethod.GET, "/gatherings/*").permitAll() // 전부...

                        .anyRequest().authenticated()
        )
        // 기존 JWT 검증 필터 대신, Gateway가 전달한 헤더를 이용하는 필터 추가
        .addFilterBefore(headerAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public HeaderAuthenticationFilter headerAuthenticationFilter() {
        return new HeaderAuthenticationFilter();
    }

}
