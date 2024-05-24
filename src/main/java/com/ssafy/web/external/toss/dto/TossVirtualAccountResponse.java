package com.ssafy.web.external.toss.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class TossVirtualAccountResponse {
	private String orderId;
	private String status;
	private LocalDateTime requestedAt;
	private VirtualAccount virtualAccount;

	@Builder
	public TossVirtualAccountResponse(String orderId, String status, LocalDateTime requestedAt,
		VirtualAccount virtualAccount) {
		this.orderId = orderId;
		this.status = status;
		this.requestedAt = requestedAt;
		this.virtualAccount = virtualAccount;
	}
}
