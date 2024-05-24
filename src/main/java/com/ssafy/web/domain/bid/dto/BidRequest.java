package com.ssafy.web.domain.bid.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BidRequest {
	@NotNull
	private Long bidPrice;
	@NotNull
	private String otp;
}
