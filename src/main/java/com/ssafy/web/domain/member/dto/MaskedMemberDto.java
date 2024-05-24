package com.ssafy.web.domain.member.dto;

import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.global.util.DataMaskingUtil;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MaskedMemberDto {
	private String email;
	private String name;
	private String phone;

	@Builder
	public MaskedMemberDto(String email, String name, String phone) {
		this.email = email;
		this.name = name;
		this.phone = phone;
	}

	public static MaskedMemberDto of(Member member) {
		return MaskedMemberDto.builder()
			.email(DataMaskingUtil.maskEmail(member.getEmail()))
			.name(DataMaskingUtil.maskName(member.getName()))
			.phone(DataMaskingUtil.maskPhoneNumber(member.getPhone()))
			.build();
	}
}
