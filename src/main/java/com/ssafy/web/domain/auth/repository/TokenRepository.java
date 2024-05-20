package com.ssafy.web.domain.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.web.domain.auth.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
	Optional<Token> findByMember_MemberId(Long memberId);
}
