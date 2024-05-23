package com.ssafy.web.global.common.log;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ssafy.web.global.util.UserAgentUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class LoggingFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String ipAddress = UserAgentUtil.getIp(httpRequest);
		String userAgent = UserAgentUtil.getUserAgent(httpRequest);
		String requestURL = httpRequest.getRequestURL().toString();
		String authorizationHeader = httpRequest.getHeader("Authorization");

		String token = null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			token = authorizationHeader.substring(7);
		}

		// 로깅
		logger.info("IP Address: {}, User-Agent: {}, Request URL: {}, JWT: {}", ipAddress, userAgent, requestURL, token);


		// 다음 필터 또는 서블릿 실행
		chain.doFilter(request, response);

	}
}
