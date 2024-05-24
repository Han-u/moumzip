package com.ssafy.web.domain.auth.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.web.domain.auth.dto.OAuthLoginRequest;
import com.ssafy.web.domain.auth.dto.TokenDto;
import com.ssafy.web.domain.auth.service.OAuth2Service;
import com.ssafy.web.domain.member.entity.Provider;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequestMapping("/oauth2")
@RestController
@RequiredArgsConstructor
public class OAuth2Controller {
	private final OAuth2Service oauthService;

	@GetMapping("/{oauthProvider}")
	public ResponseEntity<?> getAuthCodeUrl(@PathVariable Provider oauthProvider, HttpServletResponse response) throws
		IOException {
		String redirectUrl = oauthService.getAuthCodeUrl(oauthProvider);
		response.sendRedirect(redirectUrl);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login/{oauthProvider}")
	public ResponseEntity<?> getToken(@PathVariable Provider oauthProvider, @RequestBody OAuthLoginRequest loginRequest){
		TokenDto token = oauthService.getToken(oauthProvider, loginRequest.getCode());
		return ResponseEntity.status(HttpStatus.OK).body(token);
	}

}
