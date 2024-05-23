package com.ssafy.web.domain.deposit.entity;

public enum DepositStatus {
	PENDING_DEPOSIT("입금 대기"),
	DEPOSITED("입금"),
	CANCELED_NON_PAYMENT("미입금 취소"), // 보증금 입금 마지막 날
	CANCELED("환불 요청"),
	NOT_AWARDED("패찰"),
	REFUNDED_CANCEL("취소 환불"),
	REFUNDED_NOT_AWARDED("패찰 환불"),
	AWARDED("낙찰"),
	COMPLETED("거래 완료");

	private final String desc;

	DepositStatus(String desc) {
		this.desc = desc;
	}

	public String getDescription() {
		return desc;
	}
}

