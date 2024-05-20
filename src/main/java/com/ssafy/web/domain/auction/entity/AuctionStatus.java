package com.ssafy.web.domain.auction.entity;

public enum AuctionStatus {

	SCHEDULED("예약"),
	CANCELED("취소"),
	ENDED("종료"),
	PROCESS("진행중");

	private final String desc;

	AuctionStatus(String desc){
		this.desc = desc;
	}

	public String getDesc(){
		return desc;
	}
}
