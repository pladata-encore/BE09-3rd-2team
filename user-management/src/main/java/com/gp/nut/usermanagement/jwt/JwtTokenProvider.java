package com.gp.nut.usermanagement.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    /* yml config파일에 */
    /*
    * jwt:
      secret: LH9QZL8upsPBfuDY+Dkb1kT9DZIIUSuA2u4O6Lfi3mkEfeWtETpVTcR/8SMZdJWn/xNTuCQBE6rBvDXgnVmscQ==
      expiration: 1800000
      refresh-expiration: 604800000
    위 코드를 @Value가 값을 자동으로 주입
    * */
    @Value("${jwt.secret}")
    private String jwtSecret;  // 🔹 JWT 서명에 사용할 비밀 키 (BASE64 인코딩된 값)

    @Value("${jwt.expiration}")
    private long jwtExpiration;  // 🔹 Access Token의 만료 시간 (밀리초 단위)

    @Value("${jwt.refresh-expiration}")
    private long jwtRefreshExpiration;  // 🔹 Refresh Token의 만료 시간 (밀리초 단위)

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        secretKey = Keys.hmacShaKeyFor(keyBytes);
        /*
        * 즉, jwtSecret이 Base64로 인코딩된 문자열이라면, 이를 디코딩하여 원래의 바이트 배열로 변환한 후,
            해당 바이트 배열을 기반으로 HMAC SHA 알고리즘을 위한 비밀 키(secretKey)를 생성하는 역할
        * */
    }

    // access token 생성 메소드 (claim에 userId 추가)
    public String createToken(String username, String role, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    // refresh token 생성 메소드 (claim에 userId 추가)
    public String createRefreshToken(String username, String role, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtRefreshExpiration);
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public long getRefreshExpiration() {
        return jwtRefreshExpiration;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new BadCredentialsException("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            throw new BadCredentialsException("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            throw new BadCredentialsException("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("JWT Token claims empty", e);
        }
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()   // JWT를 파싱할 준비
                .verifyWith(secretKey)  // 서명을 검증하기 위해 secretKey 사용
                .build()                // 빌드하여 분석 도구 생성
                .parseSignedClaims(token)   // JWT를 해석하여 claims(내용) 추출
                .getPayload();              // claims의 payload 부분을 가져옴
        return claims.getSubject(); // subject(주체) 값을 반환 (일반적으로 username을 저장)
    }
}
