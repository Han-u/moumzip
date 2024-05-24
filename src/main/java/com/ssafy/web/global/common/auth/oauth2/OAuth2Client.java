package com.ssafy.web.global.common.auth.oauth2;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.Map;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ssafy.web.global.config.oauth.OAuth2Config;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OAuth2Client {
	private final OAuth2Config.Provider provider;
	private final OAuth2Config.Registration registration;
	private final RestTemplate restTemplate;

	public OAuth2Client(RestTemplate restTemplate, OAuth2Config.Provider provider,
		OAuth2Config.Registration registration) {
		this.restTemplate = restTemplate;
		this.provider = provider;
		this.registration = registration;
	}

	public String getRedirectUri() {

		return UriComponentsBuilder.fromUriString(provider.getAuthorizationUri())
			.queryParam("client_id", registration.getClientId())
			.queryParam("redirect_uri", registration.getRedirectUri())
			.queryParam("response_type", "code")
			.queryParam("scope", registration.getScope())
			.toUriString();
	}

	public OAuth2User getOAuthUserFromCode(String code){
		try {
			String accessToken = getAccessToken(code);
			Map<String, Object> userInfo = getUserInfo(accessToken);
			return extractUserInfo(userInfo);
		} catch (Exception e){
			log.info(e.getMessage());
			return null;
		}
	}

	public String getAccessToken(String authCode) {
		URI uri = UriComponentsBuilder.fromUriString(provider.getTokenUri())
			.queryParam("grant_type", "authorization_code")
			.queryParam("client_id", registration.getClientId())
			.queryParam("client_secret", registration.getClientSecret())
			.queryParam("redirect_uri", registration.getRedirectUri())
			.queryParam("code", authCode)
			.build()
			.toUri();
		System.out.println(uri);

		Map<String, Object> response = restTemplate.postForObject(uri, null, Map.class);

		return (String)response.get("access_token");
	}

	public Map<String, Object> getUserInfo(String accessToken) {

		URI uri = UriComponentsBuilder.fromUriString(provider.getUserInfoUri())
			.queryParam("access_token", accessToken).build().toUri();
		return restTemplate.getForObject(uri, Map.class);

	}

	public OAuth2User extractUserInfo(Map<String, Object> userInfo) {
		OAuth2User user = new OAuth2User();
		for (Field field : OAuth2User.class.getDeclaredFields()) {
			String fieldName = field.getName();
			if (provider.getMappings().containsKey(fieldName)) {
				Object value = getNestedValue(userInfo, provider.getMappings().get(fieldName));
				if (value != null) {
					field.setAccessible(true);
					try {
						field.set(user, value.toString());
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return user;
	}

	private Object getNestedValue(Map<String, Object> map, String key) {
		String[] keys = key.split("\\.");
		Object value = map;
		for (String k : keys) {
			if (value instanceof Map) {
				value = ((Map<?, ?>)value).get(k);
			} else {
				return null;
			}
		}
		return value;
	}
}
