package com.ssafy.web.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMemberRequest {

	private String password;

	@NotBlank(message = "전화번호는 필수 항목입니다.")
	@Pattern(regexp = "^(010|011|016|017|018|019)-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다.")
	private String phone;
}
