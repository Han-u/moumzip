package com.ssafy.web.global.common.auth.jwt;

import com.ssafy.web.domain.member.repository.MemberRepository;
import com.ssafy.web.global.error.ErrorCode;
import com.ssafy.web.global.error.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import com.ssafy.web.domain.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenProvider {
    @Value("${spring.jwt.token.secret-key}")
    private String secretKey;
    @Value("${spring.jwt.access.expiration}")
    private long accessExpiration;
    @Value("${spring.jwt.refresh.expiration}")
    private long refreshExpiration;

    private MemberRepository memberRepository;

    public String createAccessToken(String memberEmail) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberEmail", memberEmail);

        Date now = new Date();
        Date validity = new Date(now.getTime() + accessExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(memberEmail)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(String memberEmail) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberEmail", memberEmail);

        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(memberEmail)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Member extractMember(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		String memberEmail = claims.getSubject();

        return memberRepository.findByEmail(memberEmail).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
