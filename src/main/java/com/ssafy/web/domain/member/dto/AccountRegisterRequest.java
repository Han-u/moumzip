package com.ssafy.web.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AccountRegisterRequest {
	@NotBlank
	private String accountNumber;
	@NotBlank
	private String bank;
}
