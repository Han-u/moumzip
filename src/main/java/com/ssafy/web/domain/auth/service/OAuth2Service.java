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
		if (user == null) {
			throw new OAuthException(ErrorCode.OAUTH2_AUTHENTICATION_FAILED);
		}

		// check if signin or signup
		Member member = memberRepository.findByEmail(user.getEmail())
			.orElseGet(() -> Member.builder()
				.email(user.getEmail())
				.name(user.getName())
				.provider(provider)
				.build());

		// member update
		memberRepository.save(member);

		// token create or update
		String accessToken = jwtTokenProvider.createAccessToken(member);
		String refreshToken = jwtTokenProvider.createRefreshToken(member);

		Token token = tokenRepository.findByMember(member).orElseGet(() -> Token.builder()
			.member(member)
			.build());

		// token ip check --> 하려고 했지만 단순 IP check만으로는 의미 없다고 판단.
		token.updateToken(accessToken, refreshToken,null);
		tokenRepository.save(token);

		return TokenDto.of(token);

	}
}
