package com.ssafy.web.domain.member.entity;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	private Long id;
	private String password;
	private String salt;
	private String loginId;
	private String name;
	private String email;
	private String nickname;
	private String phone;
	private String profileImg;
	private String role;
	private boolean isDeleted;
	private int loginFailCnt;
	private boolean isLocked;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime lastLoginFailTime;


}