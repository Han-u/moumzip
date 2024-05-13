package com.ssafy.web.global.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	// common
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C000", "서버에 오류로 요청을 처리할 수 없습니다."),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "C001", "잘못된 요청입니다."),
	INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "C002", "잘못된 요청 데이터 입니다."),
	UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "C003", "데이터 타입이 올바르지 않습니다"),

	// auth
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A001", "인증 토큰이 올바르지 않습니다."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "A002", "권한이 없습니다."),

	// user
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "유저를 찾을 수 없습니다."),
	USER_EMAIL_DUPLICATE(HttpStatus.CONFLICT, "U002", "사용중인 이메일 입니다."),
	USER_NICKNAME_DUPLICATE(HttpStatus.CONFLICT, "U003", "사용중인 닉네임 입니다.");


	private final HttpStatus status;
	private final String code;
	private final String message;
}
