package com.ssafy.web.domain.bid.entity;


import com.ssafy.web.domain.auction.entity.Auction;
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

	private long bidPrice;

	@Enumerated(EnumType.STRING)
	private BidStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_id")
	@NotNull
	private Auction auction;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@NotNull
	private Member member;

	@Builder
	public Bid(Long bidId, long bidPrice, BidStatus status, Auction auction, Member member) {
		this.bidId = bidId;
		this.bidPrice = bidPrice;
		this.status = status;
		this.auction = auction;
		this.member = member;
	}
}
