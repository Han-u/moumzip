package com.ssafy.web.global.config.oauth;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "oauth2.client")
@Getter
@Setter
public class OAuth2Config {
	private Map<String, Registration> registration;
	private Map<String, Provider> provider;

	@Getter
	@Setter
	public static class Registration {
		private final String clientId;
		private final String clientSecret;
		private final String redirectUri;
		private final String scope;

		public Registration(String clientId, String clientSecret, String redirectUri, String scope) {
			this.clientId = clientId;
			this.clientSecret = clientSecret;
			this.redirectUri = redirectUri;
			this.scope = scope;
		}
	}

	@Getter
	@Setter
	public static class Provider {
		private String authorizationUri;
		private String tokenUri;
		private String userInfoUri;
		private Map<String, String> mappings;
	}

}
