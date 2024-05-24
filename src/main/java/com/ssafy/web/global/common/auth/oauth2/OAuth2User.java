package com.ssafy.web.global.common.auth.oauth2;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuth2User {
	private String id;
	private String email;
	private String name;
	private String imageUrl;
	private String phone;
}
