package com.gp.nut.usermanagement.auth.repository;

import com.gp.nut.usermanagement.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
