package com.ssafy.web.external.toss;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ssafy.web.external.toss.dto.TossRefundRequest;

@Component
public class TossClient {
	@Value("${external.toss.toss-api-url}")
	private String TOSS_API_URL;

	@Value("${external.toss.toss-api-url}")
	private String TOSS_API_KEY;
	private final RestTemplate restTemplate = new RestTemplate();

	public String issueVirtualAccount(TossVirtualAccountRequest body) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Basic " + TOSS_API_KEY);
			headers.setContentType(MediaType.APPLICATION_JSON);

			// HTTP 요청 엔티티 생성
			HttpEntity<TossVirtualAccountRequest> entity = new HttpEntity<>(body, headers);

			// 토스 API 호출
			Map<String, Object> response = restTemplate.postForObject(TOSS_API_URL, entity, Map.class);
			System.out.println(response);

			// 응답에서 가상계좌 번호 추출
			String virtualAccountNumber = extractVirtualAccountNumber(response);
			return virtualAccountNumber;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// API 응답에서 가상계좌 번호 추출
	private static String extractVirtualAccountNumber(Map responseBody) {
		return (String)((Map<?, ?>)responseBody.get("virtualAccount")).get("accountNumber");
	}

	public String refundPayment(String paymentKey, String cancelReason) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Basic " + TOSS_API_KEY);
		headers.setContentType(MediaType.APPLICATION_JSON);

		TossRefundRequest requestBody = new TossRefundRequest(paymentKey, cancelReason);

		HttpEntity<TossRefundRequest> entity = new HttpEntity<>(requestBody, headers);
		Map<String, Object> response = restTemplate.postForObject(TOSS_API_URL, entity, Map.class);

		String status = extractKey(response);

		return status;
	}

	private static String extractKey(Map responseBody){
		return (String)responseBody.get("status");
	}
}
