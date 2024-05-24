package com.ssafy.web.global.common.auth.oauth2;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ssafy.web.global.config.oauth.OAuth2Config;

@Component
public class OAuth2ClientManager {
	private final OAuth2Config oAuth2Properties;
	private final Map<String, OAuth2Client> oauth2Clients = new HashMap<>();
	private final RestTemplate restTemplate;


	public OAuth2ClientManager(OAuth2Config oAuth2Properties) {
		this.oAuth2Properties = oAuth2Properties;
		this.restTemplate = new RestTemplate();
		initializeClients();
	}

	private void initializeClients() {
		Map<String, OAuth2Config.Provider> providerConfigs = oAuth2Properties.getProvider();
		for(String provider: providerConfigs.keySet()){
			OAuth2Client client = new OAuth2Client(restTemplate, oAuth2Properties.getProvider().get(provider), oAuth2Properties.getRegistration().get(provider));
			oauth2Clients.put(provider, client);
		}
	}

	public OAuth2Client getClient(String providerName) {
		return oauth2Clients.get(providerName);
	}
}
