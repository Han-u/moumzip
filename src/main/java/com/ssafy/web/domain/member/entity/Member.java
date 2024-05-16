package com.ssafy.web.domain.member.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.web.global.common.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE member SET is_deleted = true where member_id = ?")
public class Member extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;
	@Column(nullable = false)
	private String email;
	@JsonIgnore
	private String password;
	private String salt;
	@Enumerated(EnumType.STRING)
	private Provider provider;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String phone;
	private int loginFailCnt;
	private LocalDateTime lastLoginFailTime;
	private boolean isAdmin;
	private boolean isLocked;
	private boolean isDeleted;

	@Builder
	public Member(Long memberId, String email, String password, String salt, Provider provider, String name,
		String phone,
		int loginFailCnt, LocalDateTime lastLoginFailTime, boolean isAdmin, boolean isLocked, boolean isDeleted) {
		this.memberId = memberId;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.provider = provider;
		this.name = name;
		this.phone = phone;
		this.loginFailCnt = loginFailCnt;
		this.lastLoginFailTime = lastLoginFailTime;
		this.isAdmin = isAdmin;
		this.isLocked = isLocked;
		this.isDeleted = isDeleted;
	}
}