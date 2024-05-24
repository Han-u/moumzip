package com.ssafy.web.domain.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.domain.auth.dto.RefreshTokenRequest;
import com.ssafy.web.domain.auth.dto.TokenDto;
import com.ssafy.web.domain.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest tokenRequest){
		TokenDto dto = authService.getToken(tokenRequest.getRefreshToken());
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

	/*
	Todo: 일반 회원 로직 --> 일반 회원 가입시 비밀번호 검증, 비정상 로그인 시도 감지 및 차단
	 */
}
