package com.ssafy.web.domain.bid.entity;


import com.ssafy.web.domain.auction.entity.Auction;
import com.ssafy.web.domain.deposit.entity.Deposit;
import com.ssafy.web.domain.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bid {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bidId;

	private Long bidPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deposit_id")
	@NotNull
	private Deposit deposit;

	@Builder
	public Bid(Long bidId, Long bidPrice, Deposit deposit) {
		this.bidId = bidId;
		this.bidPrice = bidPrice;
		this.deposit = deposit;
	}
}
