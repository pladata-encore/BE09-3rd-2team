package com.gp.nut.schedule.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;

public class SecurityUtil {

  // 인스턴스화 방지용 private 생성자
  private SecurityUtil() {
  }

  // 현재 인증된 사용자 ID를 반환
  public static String getCurrentUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      return null; // 또는 throw new SomeException("인증되지 않은 사용자입니다");
    }
    return auth.getName();
  }

  // 현재 인증된 사용자의 권한 리스트 반환
  public static Collection<? extends GrantedAuthority> getCurrentUserRoles() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      return Collections.emptyList();
    }
    return auth.getAuthorities();
  }
}

