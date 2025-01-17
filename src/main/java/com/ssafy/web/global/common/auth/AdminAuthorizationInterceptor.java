package com.ssafy.web.global.common.auth;

import org.springframework.core.MethodParameter;
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

	public boolean hasCurrentUser(HandlerMethod handlerMethod){
		for(MethodParameter parameter: handlerMethod.getMethodParameters()){
			if(parameter.hasParameterAnnotation(CurrentUser.class)){
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(!(handler instanceof HandlerMethod handlerMethod)){
			return true;
		}

		RequiresAdmin requireMethod = handlerMethod.getMethodAnnotation(RequiresAdmin.class); // method에 @RequiresAdmin 여부
		boolean requireClass = handlerMethod.getMethod().getDeclaringClass().isAnnotationPresent(RequiresAdmin.class); // class에 @RequiresAdmin 여부

		boolean checkUser = hasCurrentUser(handlerMethod);
		boolean requireAdmin = requireClass || requireMethod != null;

		// 토큰 검증
		String token = jwtTokenProvider.resolveToken(request);

		if(token == null || !jwtTokenProvider.validateToken(token)){
			throw new BusinessException(ErrorCode.UNAUTHORIZED);
		}

		// 멤버 가져오기
		Member member = jwtTokenProvider.extractMember(token);

		if(!requireAdmin && !checkUser) { // @RequiresAdmin이 붙어있지 않다면 패스
			request.setAttribute("member", member); // FIXME: currentUser 할일이 없어서 이 로직이 말이 안됨... 급하게 고친거라서 구조 변경 필요
			return true;
		}

		member = authService.getMemberById(member.getMemberId()); // id email -> 동시에 하는게 할수있다...
		if(requireAdmin && !member.isAdmin()){ // 권한이 없는 경우
			throw new BusinessException(ErrorCode.FORBIDDEN);
		}

		request.setAttribute("member", member);
		return true;
	}
}
