package com.ssafy.web.domain.member.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.web.global.common.entity.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE member SET is_deleted = true where member_id = ?")
public class Member extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@NotNull
	private String email;
	@JsonIgnore
	private String password;
	@JsonIgnore
	private String salt;
	@Enumerated(EnumType.STRING)
	private Provider provider;
	@NotNull
	private String name;
	private String phone;
	private int loginFailCnt;
	private LocalDateTime lastLoginFailTime;
	private LocalDateTime dateOfBirth;
	private String bank;
	private String accountNumber;
	private boolean isAdmin;
	private boolean isLocked;
	private boolean isDeleted;

	@Builder
	public Member(Long memberId, String email, String password, String salt, Provider provider, String name,
		String phone,
		int loginFailCnt, LocalDateTime lastLoginFailTime,LocalDateTime dateOfBirth,String bank, String accountNumber,boolean isAdmin, boolean isLocked, boolean isDeleted) {
		this.memberId = memberId;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.provider = provider;
		this.name = name;
		this.phone = phone;
		this.loginFailCnt = 0;
		this.lastLoginFailTime = lastLoginFailTime;
		this.dateOfBirth = dateOfBirth;
		this.bank = bank;
		this.accountNumber = accountNumber;
		this.isAdmin = isAdmin;
		this.isLocked = isLocked;
		this.isDeleted = isDeleted;
	}

	public void updateMember(String password, String phone){
		this.password = password;
		this.phone = phone;
	}

	public void updateAccount(String accountNumber, String bank){
		this.accountNumber = accountNumber;
		this.bank = bank;
	}
}