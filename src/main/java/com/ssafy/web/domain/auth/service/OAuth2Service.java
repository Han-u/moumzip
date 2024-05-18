package com.ssafy.web.domain.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.web.global.common.auth.oauth2.OAuth2User;
import com.ssafy.web.domain.auth.dto.TokenDto;
import com.ssafy.web.domain.auth.entity.Token;
import com.ssafy.web.domain.auth.repository.TokenRepository;
import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.domain.member.entity.Provider;
import com.ssafy.web.domain.member.repository.MemberRepository;
import com.ssafy.web.global.common.auth.jwt.JwtTokenProvider;
import com.ssafy.web.global.common.auth.oauth2.OAuth2Client;
import com.ssafy.web.global.common.auth.oauth2.OAuth2ClientManager;
import com.ssafy.web.global.error.ErrorCode;
import com.ssafy.web.global.error.exception.OAuthException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2Service {
	private final OAuth2ClientManager manager;
	private final MemberRepository memberRepository;
	private final TokenRepository tokenRepository;
	private final JwtTokenProvider jwtTokenProvider;

	public String getAuthCodeUrl(Provider provider) {
		OAuth2Client client = manager.getClient(provider.getName());
		if (client == null) {
			throw new OAuthException(ErrorCode.OAUTH2_PROVIDER_NOT_FOUND);
		}
		return client.getRedirectUri();
	}

	@Transactional
	public TokenDto getToken(Provider provider, String authCode) {
		OAuth2Client client = manager.getClient(provider.getName());
		if (client == null) {
			throw new OAuthException(ErrorCode.OAUTH2_PROVIDER_NOT_FOUND);
		}

		OAuth2User user = client.getOAuthUserFromCode(authCode);
		if (user == null){
			throw new OAuthException(ErrorCode.OAUTH2_AUTHENTICATION_FAILED);
		}

		System.out.println(user);
		// check if signin or signup
		Member member = memberRepository.findByEmail(user.getEmail())
			.orElseGet(() -> Member.builder().email(user.getEmail())
				.name(user.getName())
				.provider(provider).build());

		// member update

		// token create or update
		Token token = tokenRepository.findById(2L).orElseGet(() -> Token.builder().build());
		token.updateToken(null, null, null, null);
		tokenRepository.save(token);

		// token ip check

		return null;

	}
}
