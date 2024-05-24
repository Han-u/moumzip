package com.ssafy.web.domain.member.entity;

public enum Provider {
	KAKAO("kakao"),
	NAVER("naver"),
	GOOGLE("google"),
	LOCAL("local");

	private final String name;

	Provider(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
