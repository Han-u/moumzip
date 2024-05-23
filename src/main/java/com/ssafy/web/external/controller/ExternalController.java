package com.ssafy.web.external.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.external.service.ExternalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/external")
@RequiredArgsConstructor
public class ExternalController {
	// 외부 서비스 콜백용

	private final ExternalService externalService;

	@PostMapping("/callback")
	public ResponseEntity<?> depositCallback(@RequestBody Map<String, Object> attr){
		// 사용자가 보증금을 입금 받았음
		externalService.depositCallback((Long)attr.get("orderId"));
		return ResponseEntity.ok().build();
	}
}
