package com.ssafy.web.global.common.auth.jwt;

import org.springframework.beans.factory.annotation.Value;

import com.ssafy.web.domain.member.entity.Member;

import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenProvider {
	@Value("${spring.jwt.token.secret-key}")
	private String secretKey;
	public String resolveToken(HttpServletRequest request) {
		return null;
	}

	public boolean validateToken(String token) {
		return true;
	}

	public Member extractMember(String token) {
		return null;
	}
}
