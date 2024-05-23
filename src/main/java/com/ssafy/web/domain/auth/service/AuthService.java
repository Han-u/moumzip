package com.ssafy.web.domain.auth.service;

import org.springframework.stereotype.Service;

import com.ssafy.web.domain.auth.dto.TokenDto;
import com.ssafy.web.domain.auth.entity.Token;
import com.ssafy.web.domain.auth.repository.TokenRepository;
import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.domain.member.repository.MemberRepository;
import com.ssafy.web.global.common.auth.jwt.JwtTokenProvider;
import com.ssafy.web.global.error.ErrorCode;
import com.ssafy.web.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;
	private final TokenRepository tokenRepository;

	public Member getMemberById(Long memberId){
		return memberRepository.findByMemberId(memberId).orElseThrow(() ->{
			throw new BusinessException(ErrorCode.USER_NOT_FOUND);
		});
	}

	public TokenDto getToken(String refreshToken) {
		if(!jwtTokenProvider.validateToken(refreshToken)){
			throw new BusinessException(ErrorCode.BAD_REQUEST);
		}

		Member requestMember = jwtTokenProvider.extractMember(refreshToken);

		Member member = memberRepository.findByEmail(requestMember.getEmail()).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
		Token token = tokenRepository.findByMember_MemberId(member.getMemberId()).orElseThrow(() -> new BusinessException(ErrorCode.BAD_REQUEST));

		// FIXME: token에 accessToken, refreshToken 정보 확인하여 일치하지 않으면 토큰 없애버리기

		String newAccessToken = jwtTokenProvider.createAccessToken(member);
		String newRefreshToken = jwtTokenProvider.createRefreshToken(member);

		token.updateToken(newAccessToken, newRefreshToken, null);
		tokenRepository.save(token);
		return TokenDto.of(token);
	}
}
