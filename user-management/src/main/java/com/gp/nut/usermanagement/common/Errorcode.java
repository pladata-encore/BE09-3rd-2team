package com.gp.nut.usermanagement.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum Errorcode {
        DUPLICATE_USERNAME("E001", "이미 존재하는 아이디입니다.", HttpStatus.BAD_REQUEST),
        USER_NOT_FOUND("E002", "유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
        INVALID_CERT_CODE("E003", "인증 코드가 유효하지 않습니다.", HttpStatus.FORBIDDEN),
        VALIDATION_ERROR("E004", "유효성 검사 오류입니다.", HttpStatus.BAD_REQUEST),
        ACCESS_DENIED("E005", "권한이 없습니다.", HttpStatus.FORBIDDEN);

        private final String code;
        private final String message;
        private final HttpStatus httpStatus;
}

