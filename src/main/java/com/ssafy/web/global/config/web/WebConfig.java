package com.ssafy.web.global.config.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.web.domain.member.entity.Provider;
import com.ssafy.web.global.common.auth.AdminAuthorizationInterceptor;
import com.ssafy.web.global.common.auth.MemberArgumentResolver;
import com.ssafy.web.global.common.auth.jwt.JwtTokenProvider;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Bean
	public JwtTokenProvider jwtTokenProvider(){
		return new JwtTokenProvider();
	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 모든 경로에 대해
		registry.addMapping("/**")
			// Origin 허용
			.allowedOriginPatterns("http://localhost:5173")
			// GET, POST, PATCH, DELETE 메서드를 허용한다.
			.allowedMethods("GET", "POST", "PATCH", "DELETE");
	}

	@Override
	public void addFormatters(FormatterRegistry registry){
		registry.addConverter(String.class, Provider.class, source -> Provider.valueOf(source.toUpperCase()));
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new MemberArgumentResolver()); // @CurrentUser를 통해 member 엔티티를 받아오기 위함
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AdminAuthorizationInterceptor(jwtTokenProvider())) // jwt 검증하는 인터셉터를
			.addPathPatterns("/api/**"); // 해당 url에 적용
	}

}
