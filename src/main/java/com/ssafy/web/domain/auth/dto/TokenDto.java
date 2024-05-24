package com.ssafy.web.domain.auth.dto;

import com.ssafy.web.domain.auth.entity.Token;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDto {
	private String accessToken;
	private String refreshToken;

	@Builder
	public TokenDto(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public static TokenDto of(Token token){
		return TokenDto.builder()
			.accessToken(token.getAccessToken())
			.refreshToken(token.getRefreshToken())
			.build();
	}
}
