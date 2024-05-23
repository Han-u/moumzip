package com.ssafy.web.domain.auth.entity;

import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.global.common.entity.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tokenId;

	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@NotNull
	private String accessToken;

	@NotNull
	private String refreshToken;

	private String ipAddress; // 원래 IP 검증 로직을 넣으려고 했으나, 단순하게 IP만 확인하는 것은 어렵다고 판단.

	@Builder
	public Token(Long tokenId, Member member, String accessToken, String refreshToken, String ipAddress) {
		this.tokenId = tokenId;
		this.member = member;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.ipAddress = ipAddress;
	}

	public void updateToken(String accessToken, String refreshToken, String ipAddress){
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.ipAddress = ipAddress;
	}
}
