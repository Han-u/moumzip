package com.ssafy.web.domain.deposit.entity;

public enum DepositStatus {
	DEPOSITED("입금"),
	CANCELLED("취소"),
	REFUNDED_CANCEL("취소 환불"),
	REFUNDED_NOT_AWARDED("패찰 환불"),
	AWARDED("낙찰");

	private final String desc;

	DepositStatus(String desc) {
		this.desc = desc;
	}

	public String getDescription() {
		return desc;
	}
}

