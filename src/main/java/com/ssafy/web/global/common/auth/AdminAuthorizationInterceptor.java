package com.ssafy.web.global.common.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.web.domain.auth.service.AuthService;
import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.global.common.auth.jwt.JwtTokenProvider;
import com.ssafy.web.global.error.ErrorCode;
import com.ssafy.web.global.error.exception.BusinessException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminAuthorizationInterceptor implements HandlerInterceptor {
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthService authService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(!(handler instanceof HandlerMethod handlerMethod)){
			return true;
		}

		// 토큰 검증
		String token = jwtTokenProvider.resolveToken(request);

		if(token == null || !jwtTokenProvider.validateToken(token)){
			throw new BusinessException(ErrorCode.UNAUTHORIZED);
		}

		RequiresAdmin requireMethod = handlerMethod.getMethodAnnotation(RequiresAdmin.class); // method에 @RequiresAdmin 여부
		boolean requireClass = handlerMethod.getMethod().getDeclaringClass().isAnnotationPresent(RequiresAdmin.class); // class에 @RequiresAdmin 여부

		// 멤버 가져오기
		Member member = jwtTokenProvider.extractMember(token);

		if(requireMethod == null && !requireClass) { // @RequiresAdmin이 붙어있지 않다면 패스
			request.setAttribute("member", member);
			return true;
		}

		member = authService.getMemberById(member.getMemberId());
		if(!member.isAdmin()){ // 권한이 없는 경우
			throw new BusinessException(ErrorCode.FORBIDDEN);
		}

		request.setAttribute("member", member);
		return true;
	}
}
