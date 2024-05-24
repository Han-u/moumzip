package com.ssafy.web.domain.deposit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OtpRequest {
	@NotBlank
	private String otp;
}
