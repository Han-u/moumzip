package com.ssafy.web.external.toss.dto;

import lombok.Getter;

@Getter
public class TossRefundRequest {
	private String paymentKey;
	private String cancelReason;

	public TossRefundRequest(String paymentKey, String cancelReason) {
		this.paymentKey = paymentKey;
		this.cancelReason = cancelReason;
	}
}
