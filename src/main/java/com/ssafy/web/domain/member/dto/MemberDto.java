package com.ssafy.web.domain.member.dto;

import com.ssafy.web.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDto {
	private String email;
	private String name;
	private String phone;

	@Builder
	public MemberDto(String email, String name, String phone) {
		this.email = email;
		this.name = name;
		this.phone = phone;
	}

	public static MemberDto of(Member member) {
		return MemberDto.builder()
			.email(member.getEmail())
			.name(member.getName())
			.phone(member.getPhone())
			.build();
	}
}
