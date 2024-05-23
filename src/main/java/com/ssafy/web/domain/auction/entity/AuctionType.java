package com.ssafy.web.domain.auction.entity;

public enum AuctionType {
	ENGLISH("호가 경매"),
	SEALED("기일 경매"),
	TIMED("기한 경매");

	AuctionType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	private final String desc;
}
