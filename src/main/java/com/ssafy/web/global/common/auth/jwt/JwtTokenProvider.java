package com.ssafy.web.global.common.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.web.domain.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    @Value("${spring.jwt.token.secret-key}")
    private String secretKey;
    @Value("${spring.jwt.access.expiration}")
    private long accessExpiration;
    @Value("${spring.jwt.refresh.expiration}")
    private long refreshExpiration;

    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";

    public String createAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberEmail", member.getEmail());

        Date now = new Date();
        Date validity = new Date(now.getTime() + accessExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(member.getMemberId().toString())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberEmail", member.getEmail());

        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(member.getMemberId().toString())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(BEARER.length());
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
		String memberEmail = (String)claims.get("memberEmail");
        Long memberId = Long.valueOf(claims.getSubject());

        return Member.builder()
            .memberId(memberId)
            .email(memberEmail)
            .build();
    }
}
