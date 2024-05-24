package com.ssafy.web.external.toss;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TossVirtualAccountRequest {
	private Long amount;
	private String bank;
	private String customerName;
	private String orderId;
	private String orderName;

	@Builder
	public TossVirtualAccountRequest(Long amount, String bank, String customerName, String orderId, String orderName) {
		this.amount = amount;
		this.bank = bank;
		this.customerName = customerName;
		this.orderId = orderId;
		this.orderName = orderName;
	}
}
