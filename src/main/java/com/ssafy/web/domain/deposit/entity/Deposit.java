package com.ssafy.web.domain.deposit.entity;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.web.domain.auction.entity.Auction;
import com.ssafy.web.domain.bid.entity.Bid;
import com.ssafy.web.domain.member.entity.Member;
import com.ssafy.web.global.common.entity.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deposit extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long depositId;

	@NotNull
	private Long amount;

	private String otp;
	private String virtualAccount;
	private String bank;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@NotNull
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_id")
	@NotNull
	private Auction auction;

	@Enumerated(EnumType.STRING)
	private DepositStatus depositStatus;

	@OneToMany(mappedBy = "deposit")
	private List<Bid> bid = new ArrayList<>();

	@Builder
	public Deposit(Long depositId, Long amount, String otp, String virtualAccount, String bank, Member member,
		Auction auction, DepositStatus depositStatus, List<Bid> bids) {
		this.depositId = depositId;
		this.amount = amount;
		this.otp = otp;
		this.virtualAccount = virtualAccount;
		this.bank = bank;
		this.member = member;
		this.auction = auction;
		this.depositStatus = depositStatus;
		this.bid = bids;
	}



	public void updateOtp(String otp){
		if(otp.isBlank()){
			throw new IllegalArgumentException("");
		}
		this.otp = otp;
	}

}
