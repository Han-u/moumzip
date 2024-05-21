package com.ssafy.web.domain.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.web.domain.auth.entity.Token;
import com.ssafy.web.domain.member.entity.Member;

public interface TokenRepository extends JpaRepository<Token, Long> {
	Optional<Token> findByMember_MemberId(Long memberId);
	Optional<Token> findByMember(Member member);
}
