package com.ssafy.web.domain.member.dto;

import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.domain.member.entity.Provider;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotBlank
	private String name;
	@NotBlank
	private String phone;

	@Builder
	public SignUpRequest(String email, String password, String name, String phone) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.phone = phone;
	}

	public Member toEntity(String salt){
		return Member.builder()
			.email(this.email)
			.password(this.password)
			.salt(salt)
			.provider(Provider.LOCAL)
			.name(this.name)
			.phone(this.phone)
			.build();
	}
}
