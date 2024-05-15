package com.ssafy.web.domain.deposit.entity;

import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.global.common.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deposit extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long depositId;
	private long amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@NotNull
	private Member member;
}
