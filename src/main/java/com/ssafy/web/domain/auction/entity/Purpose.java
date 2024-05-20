package com.ssafy.web.domain.auction.entity;

public enum Purpose {
	LAND("토지"),
	RESIDENTIAL("주거용"),
	COMMERCIAL("상업용");

	private final String desc;

	Purpose(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
