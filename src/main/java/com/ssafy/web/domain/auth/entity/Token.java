package com.ssafy.web.domain.auth.entity;

import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.global.common.entity.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
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
	private Member memberId;

	@NotNull
	private String accessToken;

	@NotNull
	private String refreshToken;

	private String ipAddress;
}
